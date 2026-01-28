package com.platformcommons.platform.service.report.application.impl;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.security.token.PlatformAppToken;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.report.application.DataSetCronMetaService;
import com.platformcommons.platform.service.report.application.DataSourceService;
import com.platformcommons.platform.service.report.application.DatasetService;
import com.platformcommons.platform.service.report.application.constant.FileType;
import com.platformcommons.platform.service.report.application.utility.DataSourceUtil;
import com.platformcommons.platform.service.report.application.utility.ExcelGenerator;
import com.platformcommons.platform.service.report.application.utility.ParamUtils;
import com.platformcommons.platform.service.report.domain.DataSetChangeLog;
import com.platformcommons.platform.service.report.domain.DataSource;
import com.platformcommons.platform.service.report.domain.Dataset;
import com.platformcommons.platform.service.report.domain.DatasetGroup;
import com.platformcommons.platform.service.report.domain.repo.DataSetChangeLogRepository;
import com.platformcommons.platform.service.report.domain.repo.DatasetGroupRepository;
import com.platformcommons.platform.service.report.domain.repo.DatasetRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class DatasetServiceImpl implements DatasetService {
    @Autowired
    private DatasetRepository repository;

    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private DatasetGroupRepository datasetGroupRepository;

    @Value("${commons.platform.service.default-datasource:true}")
    private boolean isDefaultDataSource;

    @Value("${commons.platform.service.default-page-size:10}")
    private int defaultPageSize;


    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final DataSetChangeLogRepository dataSetChangeLogRepository;
    private DataSetCronMetaService cronMetaService;

    @Override
    public Long save(Dataset dataset, Long datasource, Set<String> datasetGroupCodes) {
        DataSource dataSource = dataSourceService.getById(datasource);
        dataset.init(dataSource);
        if(datasetGroupCodes != null && !datasetGroupCodes.isEmpty()) {
            Set<DatasetGroup> datasetGroups = datasetGroupRepository.findAllByCode(datasetGroupCodes);
            if(datasetGroups.isEmpty()) {
                throw new NotFoundException("Invalid DatasetGroupCodes");
            }
            dataset.setDatasetGroups(datasetGroups);
        }
        setDefaultParams(dataset);
        return repository.save(dataset).getId();
    }

    @Override
    public Dataset update(Dataset dataset) {
        Dataset fetchedDataset = repository.findById(dataset.getId())
                .orElseThrow(() -> new NotFoundException
                        (String.format("Request Dataset with  %d  not found", dataset.getId())));
        boolean toEnable = !fetchedDataset.getIsCronEnabled() && dataset.getIsCronEnabled();
        boolean toDisable = fetchedDataset.getIsCronEnabled() && !dataset.getIsCronEnabled();
        DataSetChangeLog dataSetChangeLog=new DataSetChangeLog(fetchedDataset);
        fetchedDataset.update(dataset);
        setDefaultParams(fetchedDataset);
        Dataset updatedDataSet=repository.save(fetchedDataset);
        dataSetChangeLog.setDataset(updatedDataSet);
        dataSetChangeLogRepository.save(dataSetChangeLog);
        if(toEnable) {
            updatedDataSet.getDatasetCronMetas().forEach(cronMeta -> {
                cronMetaService.scheduleCronMeta(cronMeta);
            });
        }
        if(toDisable)
            cronMetaService.removeSchedulerForDataset(updatedDataSet);
        return updatedDataSet;
    }

    @Override
    public Dataset getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Request Dataset with  %d  not found", id)));
    }

    @Override
    public Dataset getByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new NotFoundException(String.format("Request Dataset with name  %s  not found", name)));
    }


    @Transactional(readOnly = true)
    @Override
    public Object execute(String name, String params,Map<String, String> mapParams) {

        Dataset dataset = this.getByName(name);

        Boolean isExposed = dataset.getIsExposed();
        if(isExposed == null || isExposed.equals(Boolean.FALSE)) {
            if (!repository.isAllowedTenant(PlatformSecurityUtil.getCurrentTenantId(), dataset.getId())) {
                throw new UnAuthorizedAccessException("Not Authorized to execute the dataset");
            }
        }
        Map<String, Object> paramValues = ParamUtils.parseMap(dataset.getDefaultParamMap());
        if(params!=null) {
            paramValues.putAll(ParamUtils.parseMap(params));
        }
        if(!CollectionUtils.isEmpty(mapParams)){
            paramValues.putAll(mapParams);
        }

        String queryText = dataset.getQueryText();
        queryText = ParamUtils.replaceReservedParams(queryText);
        String namedQuery = queryText.replace("<::",":").replace("::>","");
        String finalQuery =ParamUtils.replacePagingParameters(namedQuery,paramValues,Boolean.FALSE,defaultPageSize);


        return getResultSet(finalQuery,paramValues,dataset);
    }


    @Transactional(readOnly = true)
    @Override
    public Object executeV2(String name, String params,Integer page, Integer size) {

        Dataset dataset = this.getByName(name);
        if(ParamUtils.usingReservedParamsCheck(params)){
            throw new UnAuthorizedAccessException("Dataset not allowed to use  reserved params");
        }
        Map<String, Object> paramValues = ParamUtils.parseMap(dataset.getDefaultParamMap());
        if(params!=null) {
            paramValues.putAll(ParamUtils.parseMap(params));
        }
        paramValues.put(ParamUtils.LIMIT_OFFSET, String.valueOf(page*size));
        paramValues.put(ParamUtils.LIMIT_ROW_COUNT,String.valueOf(size));
        if(!paramValues.containsKey(ParamUtils.LIMIT_ROW_COUNT)){
            paramValues.put(ParamUtils.LIMIT_ROW_COUNT,String.valueOf(defaultPageSize));
        }
        String queryText = dataset.getQueryText();
        String namedQuery = queryText.replace("<::",":").replace("::>","");
        String finalQuery =ParamUtils.replacePagingParameters(namedQuery,paramValues,Boolean.TRUE,defaultPageSize);
        return getResultSet(finalQuery,paramValues,dataset);
    }

    public  Object getResultSet( String query,
                                 Map<String,Object> paramSource)  {
        return jdbcTemplate.query(query,  paramSource, new RowMapperResultSetExtractor<>(new ColumnMapRowMapper()));

    }

    @Override
    public Page<Dataset> getAllDataset(Integer page, Integer size, Sort orders) {
        return repository.findAll(PageRequest.of(page,size,orders));
    }
    @Override
    public byte[] executeQueryDownload(String name, String param, FileType fileType) {

        Object obj = execute(name, param,null);
        List<String[]> csvData = provideCsvData(obj);
        if(fileType.equals(FileType.EXCEL)){

            try {
                return  ExcelGenerator.generateExcel(csvData);
            }
            catch (IOException e){
                e.printStackTrace();
                throw new RuntimeException(e);
            }

        } else if (fileType.equals(FileType.CSV)) {

            if( csvData == null || csvData.isEmpty() ) {
                return null;
            }

            ByteArrayOutputStream outputStream;
            try {

                outputStream = new ByteArrayOutputStream();
                PrintWriter writer = new PrintWriter( outputStream );
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(csvData.get(0)));

                for (int i = 1; i < csvData.size(); i++)
                    csvPrinter.printRecord( csvData.get(i) );

                csvPrinter.flush();
                writer.flush();
                csvPrinter.close();
                writer.close();
            }
            catch (IOException e){
                e.printStackTrace();
                throw new RuntimeException(e);
            }

            return outputStream.toByteArray();
        }else {
            throw  new InvalidInputException("Invalid fileType");
        }
    }

    @Override
    public Object executeV3(String name, String params, Integer page, Integer size) {

        Dataset dataset = this.getByName(name);
        if( !(SecurityContextHolder.getContext().getAuthentication() instanceof PlatformAppToken) &&
                !repository.isAllowedTenant(PlatformSecurityUtil.getCurrentTenantId(),dataset.getId())){
            throw  new UnAuthorizedAccessException("Not Authorized to execute the dataset");
        }
        Map<String, Object> paramValues = ParamUtils.parseMap(dataset.getDefaultParamMap());
        if(params!=null) {
            paramValues.putAll(ParamUtils.parseMap(params));
        }

        paramValues.put(ParamUtils.LIMIT_OFFSET, String.valueOf(page*size));
        paramValues.put(ParamUtils.LIMIT_ROW_COUNT,String.valueOf(size));
        if(!paramValues.containsKey(ParamUtils.LIMIT_ROW_COUNT)){
            paramValues.put(ParamUtils.LIMIT_ROW_COUNT,String.valueOf(defaultPageSize));
        }

        String queryText = dataset.getQueryText();
        queryText = ParamUtils.replaceReservedParamsV2(queryText);
        String namedQuery = queryText.replace("<::",":").replace("::>","");
        String finalQuery =ParamUtils.replacePagingParameters(namedQuery,paramValues,Boolean.TRUE,defaultPageSize);
        return getResultSet(finalQuery,paramValues,dataset);
    }

    private Object getResultSet(String finalQuery,Map<String,Object> paramValues, Dataset dataset) {
        DataSource dataSource = dataset.getDataSource();
        if(Objects.equals(dataSource.getIsDefaultDataSource(),Boolean.FALSE)) {
            return DataSourceUtil.getResultSet(dataSource.getUrl(), dataSource.getUser(),
                    dataSource.getPassword(), dataSource.getDataSourceDriver(), finalQuery, paramValues);
        } else if(isDefaultDataSource){
            return getResultSet(finalQuery,paramValues);
        } else {
            throw new NotFoundException("Datasource Not Found for the dataset");
        }
    }

    @Override
    public List<Dataset> getByListOfNames(List<String> names) {
        return repository.getByListOfNames(names);
    }

    @Override
    public Object executeV3(String name, String params, String startDate, String endDate, Integer page, Integer size) {
        Dataset dataset = this.getByName(name);
        Map<String, Object> paramValues = new LinkedHashMap<>();
        if(params!=null) {
            paramValues = ParamUtils.parseMap(params);
        }
        paramValues.put(ParamUtils.LIMIT_OFFSET, String.valueOf(page*size));
        paramValues.put(ParamUtils.LIMIT_ROW_COUNT,String.valueOf(size));
        if(!paramValues.containsKey(ParamUtils.LIMIT_ROW_COUNT)){
            paramValues.put(ParamUtils.LIMIT_ROW_COUNT,String.valueOf(defaultPageSize));
        }
        if(startDate!=null && !startDate.isEmpty()){
            paramValues.put(ParamUtils.START_DATE, startDate);
        }
        if(endDate!=null && !endDate.isEmpty()){
            paramValues.put(ParamUtils.END_DATE, endDate);
        }
        String queryText = dataset.getQueryText();
        String namedQuery = queryText.replace("<::",":").replace("::>","");
        String finalQuery =ParamUtils.replacePagingParameters(namedQuery,paramValues,Boolean.TRUE,defaultPageSize);
        if(isDefaultDataSource) {
            return getResultSet(finalQuery,paramValues);
        }
        else {
            DataSource dataSource = dataset.getDataSource();
            return DataSourceUtil.getResultSet(dataSource.getUrl(), dataSource.getUser(),
                    dataSource.getPassword(), dataSource.getDataSourceDriver(), finalQuery, paramValues);
        }
    }


    private List<String[]> provideCsvData(Object obj) {

        ArrayList<LinkedCaseInsensitiveMap<String>> result = (ArrayList<LinkedCaseInsensitiveMap<String>>) obj;
        List<String[]> csvData = new ArrayList<>();

        if (result == null || result.isEmpty()) return null;

        Set<String> keys;
        SettingHeader: {
            keys = result.get(0).keySet();
            String[] header = new String[keys.size()];
            AtomicInteger index = new AtomicInteger(0);
            keys.forEach(key -> {
                header[index.getAndIncrement()] = key;
            });
            csvData.add(header);
        }

        SettingBody:
        result.forEach(row -> {
            String[] data = new String[keys.size()];
            AtomicInteger index1 = new AtomicInteger(0);
            keys.forEach(key -> {
                data[index1.getAndIncrement()] = String.valueOf( row.get(key) );
            });
            csvData.add(data);
        });


        return csvData;
    }

    private void setDefaultParams(Dataset dataset) {


        Matcher matcher=Pattern.compile("<::(.*?)::>").matcher(dataset.getQueryText());
        StringBuilder newDefaultDataSetParams=new StringBuilder();
        Set<String> paramNames=new HashSet<>();

        while(matcher.find()) {
            paramNames.add(matcher.group(1));
        }
        if(dataset.getDefaultParamMap()!=null) {
            Stream.of(dataset.getDefaultParamMap().split("--"))
                    .forEach(param -> {
                        String[] kv = param.split("=");
                        if (paramNames.contains(kv[0]) && kv.length == 2)
                            newDefaultDataSetParams.append(param).append("--");
                    });
        }
        if(newDefaultDataSetParams.toString().endsWith("--")) {
            newDefaultDataSetParams.delete(newDefaultDataSetParams.length() - 2, newDefaultDataSetParams.length());
        }
        dataset.setDefaultParamMap(newDefaultDataSetParams.toString());

    }

    @Autowired
    public void setCronMetaService(DataSetCronMetaService cronMetaService) {
        this.cronMetaService = cronMetaService;
    }
}