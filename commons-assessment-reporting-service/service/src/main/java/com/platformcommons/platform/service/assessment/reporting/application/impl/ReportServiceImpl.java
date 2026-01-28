package com.platformcommons.platform.service.assessment.reporting.application.impl;

import com.google.common.collect.Sets;
import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.security.token.PlatformAppToken;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentPivotReportZipDTO;
import com.platformcommons.platform.service.assessment.dto.MimicRefDataDTO;
import com.platformcommons.platform.service.assessment.reporting.application.QuestionDimService;
import com.platformcommons.platform.service.assessment.reporting.application.ReportService;
import com.platformcommons.platform.service.assessment.reporting.application.SectionQuestionDimService;
import com.platformcommons.platform.service.assessment.reporting.application.constant.ApplicationConstants;
import com.platformcommons.platform.service.assessment.reporting.application.constant.QueryComponentConstant;
import com.platformcommons.platform.service.assessment.reporting.application.constant.QuestionType;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.dto.ExtAssesseData;
import com.platformcommons.platform.service.assessment.reporting.dto.QuestionDimDTO;
import com.platformcommons.platform.service.assessment.reporting.dto.SectionQuestionDimDTO;
import com.platformcommons.platform.service.assessment.reporting.facade.AssesseDimFacade;
import com.platformcommons.platform.service.assessment.reporting.facade.AssessmentInstanceDimFacade;
import com.platformcommons.platform.service.assessment.reporting.facade.client.DatasetClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RequiredArgsConstructor
@Service
@Slf4j
public class ReportServiceImpl implements ReportService {


    private final AssessmentInstanceDimFacade instanceDimFacade;
    private final SectionQuestionDimService sectionQuestionDimService;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final AssesseDimFacade assesseDimFacade;
    private final QuestionDimService questionDimService;
    private final DatasetClient datasetClient;
    private final int EXCEL_SIZE=1000;
    private final Set<String> PORTFOLIO_KIND = Sets.newHashSet("ASSESSMENT_KIND.ADOPTION","ASSESSMENT_KIND.CROP_PLAN");

    @Override
    public AssessmentPivotReportZipDTO getAssessmentReportQuery(Set<Long> assessmentInstanceId, String delimiter, String language) {

        if (language == null) language = ",";
        Set<AssessmentInstanceDTO> instance = assessmentInstanceId.stream().map(instanceDimFacade::getAssessmentInstanceById).collect(Collectors.toSet());

        Set<AssessmentReportSyncContext> reportSyncContext = instance.stream().map(assessmentInstanceDTO -> instanceDimFacade.getSyncContext(assessmentInstanceDTO.getAssessment(), false)).collect(Collectors.toSet());
        Map<Long,AssessmentReportSyncContext> reportSyncContextMap = reportSyncContext.stream().collect(Collectors.toMap(context -> context.getAssessmentInstanceDim().getAssessmentId(), Function.identity()));

        Map<Long,SectionQuestionDimDTO> sectionQuestionDimsMap = new HashMap<>();
        Map<Long,QuestionDimDTO> questionDimsMap = new HashMap<>();

        reportSyncContext.stream().map(AssessmentReportSyncContext::getSectionQuestionDimsMap).forEach(sectionQuestionDimsMap::putAll);
        reportSyncContext.stream().map(AssessmentReportSyncContext::getQuestionDimsMap).forEach(questionDimsMap::putAll);

        Set<Long> assessmentIds = instance.stream().map(assessmentInstanceDTO -> assessmentInstanceDTO.getAssessment().getId()).collect(Collectors.toSet());
        List<SectionQuestionDimDTO> sectionQuestionDims = sectionQuestionDimService.getSequencedSectionQuestion(assessmentIds)
                .stream()
                .map(sectionQuestionDimsMap::get)
                .collect(Collectors.toList());
        final String assessmentName = reportSyncContext.stream().findAny().get().getAssessmentInstanceDim().getAssessmentName();

        StringBuilder fields = new StringBuilder();
        List<String> headers = new ArrayList<>();
        List<String> assessmentTitleHeaders = new ArrayList<>();
        final boolean singleAssessment = assessmentInstanceId.size()==1;
        if(singleAssessment){
            headers.add("AssessmentTile");
            headers.add("ResponseDate");
            headers.add("EntityId");
        }
        headers.add("UserId");
        headers.add("Name");
        if(!singleAssessment){
            for (int i = 0; i <= headers.size(); i++)
                assessmentTitleHeaders.add(null);
        }


        Map<String,String> names = datasetClient.executeQueryV3("COMMONS_ASSESSMENT_ORG-GET_USER_NAME",String.format("IN_PARAM_USERID=%s",assesseDimFacade.getUserIdsForInstance(instance.stream().map(AssessmentInstanceDTO::getId).collect(Collectors.toSet()))),0,0, PlatformSecurityUtil.getToken())
                .stream()
                .collect(Collectors.toMap(map -> map.get("userId").toString(), map -> map.get("name").toString()));


        Long questionCount = 1L;
        Long assessmentId = 0L;
        for (SectionQuestionDimDTO sectionQuestionDim : sectionQuestionDims) {
            StringBuilder prefix = new StringBuilder();
            if(!singleAssessment && !Objects.equals(sectionQuestionDim.getAssessmentId(), assessmentId)){
                assessmentId = sectionQuestionDim.getAssessmentId();
                assessmentTitleHeaders.remove(assessmentTitleHeaders.size()-1);
                assessmentTitleHeaders.add(reportSyncContextMap.get(assessmentId).getAssessmentInstanceDim().getAssessmentName());
            }
            prefix.append(questionCount);
            appendQuestionFactFields(prefix, headers,assessmentTitleHeaders,sectionQuestionDim,questionDimsMap.get(sectionQuestionDim.getQuestionId()), fields, delimiter, language, questionDimsMap,0);
            questionCount++;
        }
        fields.deleteCharAt(fields.length() - 1);
        fields.deleteCharAt(fields.length() - 1);

        String titleParam  = singleAssessment?String.format(QueryComponentConstant.ASSESSMENT_TITLE,assessmentName):"";
        String responseDateParam  = singleAssessment?QueryComponentConstant.RESPONSE_DATE:"";
        String entityIdParam = singleAssessment?QueryComponentConstant.ENTITY:"";
        String query = String.format(QueryComponentConstant.QUERY, titleParam, responseDateParam, entityIdParam, fields);

        HashMap<String, Object> parmas = new HashMap<>();
        parmas.put("assessmentInstanceId", new ArrayList<>(assessmentInstanceId));
        parmas.put("language", language);
        List<Map<String, Object>> as = namedParameterJdbcTemplate.queryForList("SELECT COUNT(*) AS `count` FROM ( " + query + " ) as t1", parmas);
        Long count = Long.parseLong(as.get(0).get("count").toString());
        if (count.equals(0L)) {
            throw new NotFoundException("No responses found for the given assessment instance id");
        }
        long excels = (long) Math.ceil(count / 1000.0);

        List<byte[]> xlsx = IntStream.range(0, (int) excels)
                .parallel()
                .mapToObj(i -> {
                    List<String[]> csvData = provideCsvData(namedParameterJdbcTemplate.queryForList(query + " LIMIT " + (i * EXCEL_SIZE) + " , " + EXCEL_SIZE, parmas), headers,assessmentTitleHeaders,singleAssessment, questionDimsMap,delimiter,names);
                    if (csvData == null || csvData.isEmpty()) {
                        return new byte[0];
                    }
                    return generateExcelBytes(csvData, i);
                }).collect(Collectors.toList());
        byte[] bytes = new byte[0];
        try (
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
        ) {
            int i = 0;
            for (byte[] excel : xlsx) {
                ZipEntry zipEntry = new ZipEntry(String.format("%s_report_%s.xlsx",assessmentName,i++));
                zipOutputStream.putNextEntry(zipEntry);
                zipOutputStream.write(excel);
                zipOutputStream.closeEntry();
            }
            zipOutputStream.close();
            bytes = byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return new AssessmentPivotReportZipDTO(bytes,assessmentName);
    }
    private byte[] generateExcelBytes(List<String[]> data, int sheetNumber) {
        List<List<String[]>> excelsData = new ArrayList<>();
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sheet - " + sheetNumber);
            int rowNum = 0;
            for (String[] rowData : data) {
                Row row = sheet.createRow(rowNum++);
                int colNum = 0;
                for (String cellData : rowData) {
                    Cell cell = row.createCell(colNum++);
                    cell.setCellValue(cellData);
                }
            }
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private List<String[]> provideCsvData(List<Map<String, Object>> result, List<String> headers, List<String> assessmentTitleHeaders, boolean singleAssessment, Map<Long,QuestionDimDTO> questionDimMap, String delimiter, Map<String, String> names) {
        List<String[]> csvData = new ArrayList<>();

        if (result == null || result.isEmpty()) return null;

        if(!singleAssessment){
            String[] assessmentTitleHeader = new String[assessmentTitleHeaders.size()];
            for (int i = 0; i < assessmentTitleHeaders.size(); i++) assessmentTitleHeader[i] = assessmentTitleHeaders.get(i);
            csvData.add(assessmentTitleHeader);
        }

        String[] header = new String[headers.size()];
        for (int i = 0; i < headers.size(); i++) header[i] = headers.get(i);
        csvData.add(header);

        SettingBody:
        result.forEach(row -> {
            String[] data = new String[header.length];
            AtomicInteger i = new AtomicInteger(0);
            List<Map.Entry<String, Object>> entries = new ArrayList<>(row.entrySet());
            ListIterator<Map.Entry<String, Object>> it = entries.listIterator();

            while (it.hasNext()) {
                Map.Entry<String, Object> entry = it.next();
                String[] split = entry.getKey().split("-");
                String column = split.length==2?split[1]:entry.getKey();

                String value = entry.getValue() != null ? entry.getValue().toString() : "";

                try {
                    QuestionDimDTO dim = questionDimMap.get(Long.parseLong(column));
                    switch (dim.getQuestionType()) {
                        case QuestionType.OBJECTIVE_SUBJECTIVE:
                        case QuestionType.MULTISELECT_SUBJECTIVE:
                            data[i.get()] = value;
                            if (it.hasNext()) {
                                Map.Entry<String, Object> subjective = it.next();
                                String subColumn = subjective.getKey().split("_")[0];
                                String subValue = subjective.getValue() != null ? subjective.getValue().toString() : "";

                                if (subColumn.equals(column)) {
                                    i.getAndIncrement();
                                    data[i.get()] = subValue;
                                } else {
                                    it.hasPrevious();
                                    it.previous();
                                }
                            }
                            break;
                        default:
                            data[i.get()] = value;
                            break;
                    }
                }
                catch (NumberFormatException e) {
                    data[i.get()] = value;
                    if(column.equalsIgnoreCase("User")) data[i.incrementAndGet()] = names.get(value);

                }
                i.incrementAndGet();
            }

            csvData.add(data);
        });


        return csvData;
    }

    private String numToAlphabet(int number) {
        char[] array = new char[26];
        for (int i = 0; i < 26; i++)
            array[i] = (char) ('A' + i);
        int num = 26;
        int index;
        String res = "";
        while (number > 0) {
            index = (number - 1 + num) % num;
            number = (number - 1) / num;
            res = array[index] + res;
        }
        return res;
    }



    @Override
    public AssessmentPivotReportZipDTO   getAssessmentReportQueryV2(Long assessmentInstanceId, String delimiter, String language, Set<Long> aiaIds, Date startDate, Date endDate) {

        if(startDate==null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(1001, Calendar.JANUARY, 1, 0, 0, 0);
            startDate = calendar.getTime();
        }
        if(endDate==null){
            Calendar calendar = Calendar.getInstance();
            calendar.set(4001, Calendar.JANUARY, 1, 0, 0, 0);
            endDate = calendar.getTime();
        }
        if(aiaIds==null || aiaIds.isEmpty()) aiaIds = Collections.singleton(-1L);
        if (language == null) language = ",";

        AssessmentInstanceDTO instance = instanceDimFacade.getAssessmentInstanceById(assessmentInstanceId);
        AssessmentReportSyncContext reportSyncContext = instanceDimFacade.getSyncContext(instance.getAssessment(), false);

        String assessmentName = reportSyncContext.getAssessmentInstanceDim().getAssessmentName();
        Map<Long,SectionQuestionDimDTO> sectionQuestionDimsMap = reportSyncContext.getSectionQuestionDimsMap();
        Map<Long,QuestionDimDTO> questionDimsMap = reportSyncContext.getQuestionDimsMap();

        List<SectionQuestionDimDTO> sectionQuestionDims = sectionQuestionDimService.getSequencedSectionQuestion(Collections.singleton(instance.getAssessment().getId()))
                .stream()
                .map(sectionQuestionDimsMap::get)
                .collect(Collectors.toList());

        StringBuilder fields = new StringBuilder();
        List<String> headers = new ArrayList<>();


        headers.add("ABA Id");
        headers.add("ABA Name");
        headers.add("Farmer Id");
        headers.add("Farmer Name");
        headers.add("Response Date");
        boolean isCropPlan = isCropPlanAssessment(instance);
        ExtAssesseData extAssesseData = new ExtAssesseData(isCropPlan, false);
        extAssesseData.add(getExtAssesseDataForAssessment(instance.getId()));
        if(isCropPlan) headers.add("Crop Plan name");
        headers.add("FPO Name");
        headers.add("FPO Id");

        fields.append(" max(assessor_id)         AS `ABA Id`, " ).append('\n')
              .append(" max(assessor_name)       AS `ABA Name`, " ).append('\n')
              .append(" max(assessee_actor_id)   AS `Farmer Id`, " ).append('\n')
              .append(" max(assessee_actor_name) AS `Farmer Name`, " ).append('\n')
              .append(" max(from_unixtime(response_created_at / 1000)) as ResponseDate,").append('\n')
              .append(" assessment_instance_assesse_id         AS `AssesseeId`, ").append('\n');

        Long questionCount = 1L;
        for (SectionQuestionDimDTO sectionQuestionDim : sectionQuestionDims) {
            StringBuilder prefix = new StringBuilder();
            prefix.append(questionCount);
            appendQuestionFactFieldsV2(prefix, headers,questionDimsMap.get(sectionQuestionDim.getQuestionId()), fields, delimiter, language, questionDimsMap);
            questionCount++;
        }
        fields.deleteCharAt(fields.length() - 1);
        fields.deleteCharAt(fields.length() - 1);

        String query = String.format(QueryComponentConstant.ALL_RESPONSE__QUERY, fields);

        HashMap<String, Object> parmas = new HashMap<>();
        parmas.put("assessmentInstanceId", assessmentInstanceId);
        parmas.put("aiaIds", aiaIds);
        parmas.put("startDate", startDate);
        parmas.put("endDate", endDate);
        parmas.put("language", language);
        List<Map<String, Object>> as = namedParameterJdbcTemplate.queryForList("SELECT COUNT(*) AS `count` FROM ( " + query + " ) as t1", parmas);
        Long count = Long.parseLong(as.get(0).get("count").toString());
        if (count.equals(0L)) {
            throw new NotFoundException("No responses found for the given assessment instance id");
        }
        long excels = (long) Math.ceil(count / 1000.0);

        List<byte[]> xlsx = IntStream.range(0, (int) excels)
                .parallel()
                .mapToObj(i -> {
                    List<String[]> csvData = provideCsvDataV2(namedParameterJdbcTemplate.queryForList(query + " LIMIT " + (i * EXCEL_SIZE) + " , " + EXCEL_SIZE, parmas), headers, questionDimsMap,delimiter, extAssesseData);
                    if (csvData == null || csvData.isEmpty()) {
                        return new byte[0];
                    }
                    return generateExcelBytes(csvData, i);
                }).collect(Collectors.toList());
        byte[] bytes = new byte[0];
        try (
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
        ) {
            int i = 0;
            for (byte[] excel : xlsx) {
                ZipEntry zipEntry = new ZipEntry(String.format("%s_report_%s.xlsx",assessmentName,i++));
                zipOutputStream.putNextEntry(zipEntry);
                zipOutputStream.write(excel);
                zipOutputStream.closeEntry();
            }
            zipOutputStream.close();
            bytes = byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return new AssessmentPivotReportZipDTO(bytes,assessmentName);
    }

    @Override
    public List<Map<String,String>> getAssessmentReportQueryV3(Long assessmentInstanceId, String language, String marketCode, boolean cacheDisabled) {

        long startTime = System.currentTimeMillis();
        log.error("Starting getAssessmentReportQueryV3. InstanceId: {}, Language: {}, MarketCode: {}", assessmentInstanceId, language, marketCode);
        if (language == null) language = ",";

        AssessmentInstanceDTO instance = instanceDimFacade.getAssessmentInstanceById(assessmentInstanceId);
        AssessmentReportSyncContext reportSyncContext = instanceDimFacade.getSyncContext(instance.getAssessment(), cacheDisabled);

        String assessmentName = reportSyncContext.getAssessmentInstanceDim().getAssessmentName();
        Long assessmentId = reportSyncContext.getAssessmentInstanceDim().getAssessmentId();

        log.error("Context loaded. AssessmentName: [{}], AssessmentId: [{}]", assessmentName, assessmentId);

        Map<Long,SectionQuestionDimDTO> sectionQuestionDimsMap = reportSyncContext.getSectionQuestionDimsMap();
        Map<Long,QuestionDimDTO> questionDimsMap = reportSyncContext.getQuestionDimsMap();

        List<SectionQuestionDimDTO> sectionQuestionDims = sectionQuestionDimService.getSequencedSectionQuestion(Collections.singleton(instance.getAssessment().getId()))
                .stream()
                .map(sectionQuestionDimsMap::get)
                .collect(Collectors.toList());

        StringBuilder fields = new StringBuilder();
        List<String> headers = new ArrayList<>();
        String kind = Optional.ofNullable(instance.getAssessment().getAssessmentKind()).map(MimicRefDataDTO::getCode).orElse(null);
        ExtAssesseData extAssesseData = new ExtAssesseData(false,PORTFOLIO_KIND.contains(kind));
        extAssesseData.add(getExtAssesseDataForAssessmentV2(instance.getId(),marketCode));

        log.error("Assessment Kind: [{}]. Include Portfolio: [{}]. ExtAssesseData size: [{}]",
                kind, extAssesseData.toAddPortfolioId(), extAssesseData.getAssesseIds().size());

        headers.add("response_date");
        headers.add("fpo_Name");
        headers.add("fpo_Id");
        if(extAssesseData.toAddPortfolioId()) headers.add("portfolio_line_item_id");
        headers.add("assesse_Id");
        headers.add("aba_name");
        headers.add("aba_id");
        headers.add("farmer_name");
        headers.add("farmer_io");
        headers.add("assessment_name");
        headers.add("assessment_id");

        fields.append(" max(from_unixtime(response_created_at / 1000)) AS ResponseDate,").append('\n')
              .append(" assessment_instance_assesse_id                 AS `AssesseeId`, ").append('\n')
              .append(" max(assessor_name)                             AS `ABA Name`, " ).append('\n')
              .append(" max(assessor_id)                               AS `ABA Id`, " ).append('\n')
              .append(" max(assessee_actor_name)                       AS `Farmer Name`, " ).append('\n')
              .append(" max(assessee_actor_id)                         AS `Farmer Id`, " ).append('\n')
              .append(String.format(" '%s'                                           AS `Assessment Name`, ",assessmentName)).append('\n')
              .append(String.format(" '%s'                                           AS `Assessment Id`, ",assessmentId)).append('\n');

        Long questionCount = 1L;
        String delimiter = ",";
        for (SectionQuestionDimDTO sectionQuestionDim : sectionQuestionDims) {
            StringBuilder prefix = new StringBuilder();
            prefix.append(questionCount);
            log.error("Starting Root Question Sequence #{}: ID [{}], Prefix [{}]", questionCount, sectionQuestionDim.getQuestionId(), prefix);
            appendQuestionFactFieldsV2(prefix, headers,questionDimsMap.get(sectionQuestionDim.getQuestionId()), fields, delimiter, language, questionDimsMap);
            questionCount++;
        }
        fields.deleteCharAt(fields.length() - 1);
        fields.deleteCharAt(fields.length() - 1);

        String query = String.format(QueryComponentConstant.ALL_RESPONSE__QUERY_V2, fields);

        Set<Long> aiaIds = extAssesseData.getAssesseIds();
        HashMap<String, Object> parmas = new HashMap<>();
        parmas.put("assessmentInstanceId", assessmentInstanceId);
        parmas.put("aiaIds", aiaIds);
        parmas.put("language", language);

        if (aiaIds.isEmpty()) {
            throw new NotFoundException("No responses found for the given assessment instance id");
        }

        log.error("Generated Query: {}", query);
        log.error("Query Parameters: {}", parmas);

        log.error("Executing DB Query. Params: [aiaIds count: {}]", aiaIds.size());
        List<Map<String, Object>> rawData = namedParameterJdbcTemplate.queryForList(query, parmas);
        log.error("DB Query successful. Fetched {} rows.", rawData.size());
        List<Map<String, String>> resultData = provideJsonData(rawData, headers, questionDimsMap, delimiter, extAssesseData);
        log.info("Completed getAssessmentReportQueryV3 in {} ms. Returning {} records.", (System.currentTimeMillis() - startTime), resultData.size());
        return resultData;
    }

    private List<Map<String,String>> provideJsonData(List<Map<String, Object>> result, List<String> headers, Map<Long,QuestionDimDTO> questionDimMap, String delimiter, ExtAssesseData extAssesseData) {

        List<Map<String,String>> array = new ArrayList<>();
        if (result == null || result.isEmpty()) return null;
        SettingBody:
        result.forEach(row -> {

            Map<String,String> data = new LinkedHashMap<>();
            AtomicInteger i = new AtomicInteger(0);
            List<Map.Entry<String, Object>> entries = new ArrayList<>(row.entrySet());
            ListIterator<Map.Entry<String, Object>> it = entries.listIterator();

            while (it.hasNext()) {
                Map.Entry<String, Object> entry = it.next();
                String column = entry.getKey();
                String value = entry.getValue() != null ? entry.getValue().toString() : "";

                try {
                    QuestionDimDTO dim = questionDimMap.get(Long.parseLong(column));
                    String jsonField = headers.get(i.get());
                    switch (dim.getQuestionType()) {
                        case QuestionType.OBJECTIVE_SUBJECTIVE:
                        case QuestionType.MULTISELECT_SUBJECTIVE:
                            data.put(jsonField,value);
                            if (it.hasNext()) {
                                Map.Entry<String, Object> subjective = it.next();
                                String subColumn = subjective.getKey().split("_")[0];
                                String subValue = subjective.getValue() != null ? subjective.getValue().toString() : "";

                                if (subColumn.equals(column)) {
                                    i.getAndIncrement();
                                    data.put(headers.get(i.get()),subValue);
                                } else {
                                    it.hasPrevious();
                                    it.previous();
                                }
                            }
                            break;
                        default:
                            data.put(jsonField,value);
                            break;
                    }
                }
                catch (NumberFormatException e) {
                    String jsonField;
                    if(i.get()==1){
                        Long assesseId = Long.parseLong(value);
                        data.put(headers.get(i.getAndIncrement()) ,extAssesseData.getFpoName(assesseId));;
                        data.put(headers.get(i.get()) ,extAssesseData.getFpoId(assesseId));
                        if(extAssesseData.toAddPortfolioId())
                            data.put(headers.get(i.incrementAndGet()) ,extAssesseData.getPortfolioLineItemId(assesseId));
                        data.put(headers.get(i.incrementAndGet()), assesseId.toString());
                    }
                    else {
                        data.put(headers.get(i.get()) ,value);
                    }
                }
                i.incrementAndGet();
            }
            array.add(data);
        });


        return array;
    }

    private List<Map<String, Object>> getExtAssesseDataForAssessment(Long id) {
        String params = String.format("INSTANCE=%s",id);
        return datasetClient.executeQueryV3("COMMONS_ASSESSMENT_ORG.MARKIFY.GET_CROP_PLAN_NAME_INFO",params,0,0,PlatformSecurityUtil.getToken());
    }
    private List<Map<String, Object>> getExtAssesseDataForAssessmentV2(Long id, String code) {
        String params = String.format("INSTANCE=%s--MARKET_CODE=%s",id,code);
        if(SecurityContextHolder.getContext().getAuthentication() instanceof PlatformAppToken){
            PlatformAppToken token = (PlatformAppToken)SecurityContextHolder.getContext().getAuthentication();
            String appKey = String.format("Appkey %s",Base64.getEncoder().encodeToString(String.format("appKey:%s,appCode:%s",token.getAppKey(),token.getAppCode()).getBytes(StandardCharsets.UTF_8)));
            return datasetClient.executeQueryV3AppKey("COMMONS_ASSESSMENT_ORG.MARKIFY.GET_CROP_PLAN_NAME_INFO_V2",params,0,0,appKey);
        }
        return datasetClient.executeQueryV3("COMMONS_ASSESSMENT_ORG.MARKIFY.GET_CROP_PLAN_NAME_INFO_V2",params,0,0,PlatformSecurityUtil.getToken());
    }


    private boolean isCropPlanAssessment(AssessmentInstanceDTO instance) {
        return instance.getAssessment()!=null && instance.getAssessment().getAssessmentSubType()!=null &&
                instance.getAssessment().getAssessmentSubType().getCode()!=null &&
                Objects.equals(instance.getAssessment().getAssessmentSubType().getCode(), ApplicationConstants.ASSESSMENT_SUB_TYPE_CROP_PLAN);
    }

    private List<String[]> provideCsvDataV2(List<Map<String, Object>> result, List<String> headers, Map<Long,QuestionDimDTO> questionDimMap, String delimiter, ExtAssesseData extAssesseData) {
        List<String[]> csvData = new ArrayList<>();

        if (result == null || result.isEmpty()) return null;
        String[] header = new String[headers.size()];
        for (int i = 0; i < headers.size(); i++) header[i] = headers.get(i);
        csvData.add(header);

        SettingBody:
        result.forEach(row -> {
            String[] data = new String[header.length];
            AtomicInteger i = new AtomicInteger(0);
            List<Map.Entry<String, Object>> entries = new ArrayList<>(row.entrySet());
            ListIterator<Map.Entry<String, Object>> it = entries.listIterator();

            while (it.hasNext()) {
                Map.Entry<String, Object> entry = it.next();
                String column = entry.getKey();
                String value = entry.getValue() != null ? entry.getValue().toString() : "";

                try {
                    QuestionDimDTO dim = questionDimMap.get(Long.parseLong(column));
                    switch (dim.getQuestionType()) {
                        case QuestionType.OBJECTIVE_SUBJECTIVE:
                        case QuestionType.MULTISELECT_SUBJECTIVE:
                            data[i.get()] = value;
                            if (it.hasNext()) {
                                Map.Entry<String, Object> subjective = it.next();
                                String subColumn = subjective.getKey().split("_")[0];
                                String subValue = subjective.getValue() != null ? subjective.getValue().toString() : "";

                                if (subColumn.equals(column)) {
                                    i.getAndIncrement();
                                    data[i.get()] = subValue;
                                } else {
                                    it.hasPrevious();
                                    it.previous();
                                }
                            }
                            break;
                        default:
                            data[i.get()] = value;
                            break;
                    }
                }
                catch (NumberFormatException e) {
                    if(i.get()==5){
                        if(extAssesseData.isCropPlanAssessment()) data[i.getAndIncrement()] = extAssesseData.getCropPlanName(Long.parseLong(value));
                        data[i.getAndIncrement()] = extAssesseData.getFpoName(Long.parseLong(value));
                        data[i.get()] = extAssesseData.getFpoId(Long.parseLong(value));;
                    }
                    else{
                        data[i.get()] = value;
                    }
                }
                i.incrementAndGet();
            }
            csvData.add(data);
        });


        return csvData;
    }

    private void appendQuestionFactFieldsV2(StringBuilder prefix, List<String> headers, QuestionDimDTO questionDim, StringBuilder fields, String delimiter, String language, Map<Long, QuestionDimDTO> questionDimMap) {


        final String QUESTION_ID = questionDim.getQuestionId().toString();
        final String QUESTION_TEXT = questionDim.getQuestionText();
        log.error("Processing Question - ID: [{}], Type: [{}], Prefix: [{}]", QUESTION_ID, questionDim.getQuestionType(), prefix);

        switch (questionDim.getQuestionType()) {
            case QuestionType.OBJECTIVE_SUBJECTIVE:
                fields.append(String.format(QueryComponentConstant.QUESTION_FIELD, QUESTION_ID, QueryComponentConstant.OBJECTIVE_RESPONSE_COLUMN, QUESTION_ID))
                        .append(",").append("\n")
                        .append(String.format(QueryComponentConstant.QUESTION_FIELD, QUESTION_ID, QueryComponentConstant.SUBJECTIVE_RESPONSE_COLUMN, QUESTION_ID+"_"))
                        .append(",").append("\n");
                headers.add(String.format("%s Option:- %s", prefix, QUESTION_TEXT));
                headers.add(String.format("%s Remarks:- %s", prefix, QUESTION_TEXT));
                break;
            case QuestionType.MULTISELECT_SUBJECTIVE:
                if (delimiter == null)
                    fields.append(String.format(QueryComponentConstant.QUESTION_FIELD, QUESTION_ID, QueryComponentConstant.MULTISELECT_RESPONSE_COLUMN, QUESTION_ID));
                else
                    fields.append(String.format(QueryComponentConstant.REPLACE_DELIMITER_FIELD, QUESTION_ID, delimiter, QUESTION_ID));
                fields.append(",").append("\n")
                        .append(String.format(QueryComponentConstant.QUESTION_FIELD, QUESTION_ID, QueryComponentConstant.SUBJECTIVE_RESPONSE_COLUMN, QUESTION_ID+"_"))
                        .append(",").append("\n");
                headers.add(String.format("%s Options:- %s", prefix, QUESTION_TEXT));
                headers.add(String.format("%s Remarks:- %s", prefix, QUESTION_TEXT));
                break;
            case QuestionType.MULTISELECT:
                if (delimiter == null)
                    fields.append(String.format(QueryComponentConstant.QUESTION_FIELD, QUESTION_ID, QueryComponentConstant.MULTISELECT_RESPONSE_COLUMN, QUESTION_ID));
                else
                    fields.append(String.format(QueryComponentConstant.REPLACE_DELIMITER_FIELD, QUESTION_ID, delimiter, QUESTION_ID));
                fields.append(",").append("\n");
                headers.add(String.format("%s  %s", prefix, QUESTION_TEXT));
                break;
            case QuestionType.OBJECTIVE:
                fields.append(String.format(QueryComponentConstant.QUESTION_FIELD, QUESTION_ID, QueryComponentConstant.QUESTION_RESPONSE_COLUMN, QUESTION_ID));
                fields.append(",").append("\n");
                headers.add(String.format("%s  %s", prefix, QUESTION_TEXT));
                List<Long> childQuestions = questionDimService.getChildQuestions(questionDim.getQuestionId());
                int a = 1;
                for (Long childQuestion : childQuestions) {
                    String alphabet = numToAlphabet(a);
                    int deleteChars = alphabet.length() + 1;
                    prefix.append('.').append(alphabet);
                    if (!questionDimMap.containsKey(childQuestion))
                        throw new InvalidInputException("Data out of sync try syncing data and try again");
                    log.error("Processing Child Sequence #{}: ID [{}], Label [{}], Appending to Prefix", a, childQuestion, alphabet);
                    appendQuestionFactFieldsV2(prefix, headers,  questionDimMap.get(childQuestion), fields, delimiter, language, questionDimMap);
                    a++;
                    prefix.delete(prefix.length() - deleteChars, prefix.length());
                }

                break;
            default:
                log.error("Handling default case for Question ID: [{}]", QUESTION_ID);
                fields.append(String.format(QueryComponentConstant.QUESTION_FIELD, QUESTION_ID, QueryComponentConstant.QUESTION_RESPONSE_COLUMN, QUESTION_ID));
                fields.append(",").append("\n");
                headers.add(String.format("%s  %s", prefix, QUESTION_TEXT));
                break;
        }

    }



    @Override
    public AssessmentPivotReportZipDTO getAssessmentReportQueryV4(Long assessmentInstanceId, String delimiter, String language) {

        if (language == null) language = ",";
        AssessmentInstanceDTO instance = instanceDimFacade.getAssessmentInstanceById(assessmentInstanceId);

        AssessmentReportSyncContext reportSyncContext = instanceDimFacade.getSyncContext(instance.getAssessment(), false);

        Map<Long,SectionQuestionDimDTO> sectionQuestionDimsMap = reportSyncContext.getSectionQuestionDimsMap();
        Map<Long,QuestionDimDTO> questionDimsMap = reportSyncContext.getQuestionDimsMap();

        Set<Long> assessmentIds = Collections.singleton(instance.getAssessment().getId());
        List<SectionQuestionDimDTO> sectionQuestionDims = sectionQuestionDimService.getSequencedSectionQuestion(assessmentIds)
                .stream()
                .map(sectionQuestionDimsMap::get)
                .collect(Collectors.toList());
        final String assessmentName = reportSyncContext.getAssessmentInstanceDim().getAssessmentName();

        StringBuilder fields = new StringBuilder();
        List<String> headers = new ArrayList<>();

        headers.add("ResponseDate");
        headers.add("EntityId");
        headers.add("User");
        headers.add("Name");

        fields.append(QueryComponentConstant.RESPONSE_DATE).append('\n')
                .append(QueryComponentConstant.ENTITY).append('\n')
                .append(QueryComponentConstant.ASSESSE_ID).append('\n');


        String params = String.format("IN_PARAM_USERID=%s",assesseDimFacade.getUserIdsForInstance(Collections.singleton(instance.getId())));
        Map<String,String> names = datasetClient.executeQueryV3("COMMONS_ASSESSMENT_ORG-GET_USER_NAME",params,0,0, PlatformSecurityUtil.getToken())
                .stream()
                .collect(Collectors.toMap(map -> map.get("userId").toString(), map -> map.get("name").toString()));


        Long questionCount = 1L;
        for (SectionQuestionDimDTO sectionQuestionDim : sectionQuestionDims) {
            StringBuilder prefix = new StringBuilder();
            prefix.append(questionCount);
            appendQuestionFactFieldsV3(prefix, headers,sectionQuestionDim,questionDimsMap.get(sectionQuestionDim.getQuestionId()), fields, delimiter, questionDimsMap, 0);
            questionCount++;
        }
        fields.deleteCharAt(fields.length() - 1);
        fields.deleteCharAt(fields.length() - 1);



        String query = String.format(QueryComponentConstant.ALL_RESPONSE__QUERY_V3, fields);

        HashMap<String, Object> parmas = new HashMap<>();
        parmas.put("assessmentInstanceId", assessmentInstanceId);
        parmas.put("language", language);
        List<Map<String, Object>> as = namedParameterJdbcTemplate.queryForList("SELECT COUNT(*) AS `count` FROM ( " + query + " ) as t1", parmas);
        Long count = Long.parseLong(as.get(0).get("count").toString());
        if (count.equals(0L)) {
            throw new NotFoundException("No responses found for the given assessment instance id");
        }
        long excels = (long) Math.ceil(count / 1000.0);

        List<byte[]> xlsx = IntStream.range(0, (int) excels)
                .parallel()
                .mapToObj(i -> {
                    List<String[]> csvData = provideCsvDataV3(namedParameterJdbcTemplate.queryForList(query + " LIMIT " + (i * EXCEL_SIZE) + " , " + EXCEL_SIZE, parmas), headers, questionDimsMap,names);
                    if (csvData == null || csvData.isEmpty()) {
                        return new byte[0];
                    }
                    return generateExcelBytes(csvData, i);
                }).collect(Collectors.toList());
        byte[] bytes = new byte[0];
        try (
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
        ) {
            int i = 0;
            for (byte[] excel : xlsx) {
                ZipEntry zipEntry = new ZipEntry(String.format("%s_report_%s.xlsx",assessmentName,i++));
                zipOutputStream.putNextEntry(zipEntry);
                zipOutputStream.write(excel);
                zipOutputStream.closeEntry();
            }
            zipOutputStream.close();
            bytes = byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return new AssessmentPivotReportZipDTO(bytes,assessmentName);
    }

    @Override
    public AssessmentPivotReportZipDTO getAssessmentReportQueryV5(Set<Long> assessmentInstanceIds, String delimiter, String language) {
        if (language == null) language = ",";

        Set<AssessmentReportSyncContext> reportSyncContexts = instanceDimFacade.getAssessmentReportSyncContextByAssessmentInstanceIds(assessmentInstanceIds);

        Map<Long,AssessmentReportSyncContext> reportSyncContextMap = reportSyncContexts.stream()
                .collect(Collectors.toMap(context -> context.getAssessmentInstanceDim().getAssessmentId(), Function.identity()));

        Map<Long,SectionQuestionDimDTO> sectionQuestionDimsMap = new HashMap<>();
        Map<Long,QuestionDimDTO> questionDimsMap = new HashMap<>();

        reportSyncContexts.stream().map(AssessmentReportSyncContext::getSectionQuestionDimsMap).forEach(sectionQuestionDimsMap::putAll);
        reportSyncContexts.stream().map(AssessmentReportSyncContext::getQuestionDimsMap).forEach(questionDimsMap::putAll);

        Set<Long> assessmentIds = reportSyncContexts.stream()
                .map(assessmentReportSyncContext -> assessmentReportSyncContext.getAssessmentInstanceDim().getAssessmentId())
                .collect(Collectors.toSet());

        List<SectionQuestionDimDTO> sectionQuestionDims = sectionQuestionDimService.getSequencedSectionQuestion(assessmentIds)
                .stream()
                .map(sectionQuestionDimsMap::get)
                .collect(Collectors.toList());
        final String assessmentName = reportSyncContexts.stream().findAny().get().getAssessmentInstanceDim().getAssessmentName();

        StringBuilder fields = new StringBuilder();
        List<String> headers = new ArrayList<>();
        List<String> assessmentTitleHeaders = new ArrayList<>();
        final boolean singleAssessment = assessmentInstanceIds.size()==1;
        if(singleAssessment){
            headers.add("AssessmentTile");
            headers.add("ResponseDate");
            headers.add("EntityId");
        }
        headers.add("UserId");
        headers.add("Name");
        if(!singleAssessment){
            for (int i = 0; i <= headers.size(); i++)
                assessmentTitleHeaders.add(null);
        }





        Long questionCount = 1L;
        Long assessmentId = 0L;
        for (SectionQuestionDimDTO sectionQuestionDim : sectionQuestionDims) {
            StringBuilder prefix = new StringBuilder();
            if(!singleAssessment && !Objects.equals(sectionQuestionDim.getAssessmentId(), assessmentId)){
                assessmentId = sectionQuestionDim.getAssessmentId();
                assessmentTitleHeaders.remove(assessmentTitleHeaders.size()-1);
                assessmentTitleHeaders.add(reportSyncContextMap.get(assessmentId).getAssessmentInstanceDim().getAssessmentName());
            }
            prefix.append(questionCount);
            appendQuestionFactFields(prefix, headers,assessmentTitleHeaders,sectionQuestionDim,questionDimsMap.get(sectionQuestionDim.getQuestionId()), fields, delimiter, language, questionDimsMap,0);
            questionCount++;
        }
        fields.deleteCharAt(fields.length() - 1);
        fields.deleteCharAt(fields.length() - 1);

        String titleParam  = singleAssessment?String.format(QueryComponentConstant.ASSESSMENT_TITLE,assessmentName):"";
        String responseDateParam  = singleAssessment?QueryComponentConstant.RESPONSE_DATE:"";
        String entityIdParam = singleAssessment?QueryComponentConstant.ENTITY:"";
        String query = String.format(QueryComponentConstant.QUERY, titleParam, responseDateParam, entityIdParam, fields);

        HashMap<String, Object> parmas = new HashMap<>();
        parmas.put("assessmentInstanceId", new ArrayList<>(assessmentInstanceIds));
        parmas.put("language", language);
        List<Map<String, Object>> as = namedParameterJdbcTemplate.queryForList("SELECT COUNT(*) AS `count` FROM ( " + query + " ) as t1", parmas);
        Long count = Long.parseLong(as.get(0).get("count").toString());
        if (count.equals(0L)) {
            throw new NotFoundException("No responses found for the given assessment instance id");
        }
        long excels = (long) Math.ceil(count / 1000.0);

        List<byte[]> xlsx = IntStream.range(0, (int) excels)
                .parallel()
                .mapToObj(i -> {
                    List<String[]> csvData = provideCsvData(namedParameterJdbcTemplate.queryForList(query + " LIMIT " + (i * EXCEL_SIZE) + " , " + EXCEL_SIZE, parmas), headers,assessmentTitleHeaders,singleAssessment, questionDimsMap,delimiter,new HashMap<>());
                    if (csvData == null || csvData.isEmpty()) {
                        return new byte[0];
                    }
                    return generateExcelBytes(csvData, i);
                }).collect(Collectors.toList());
        byte[] bytes = new byte[0];
        try (
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
        ) {
            int i = 0;
            for (byte[] excel : xlsx) {
                ZipEntry zipEntry = new ZipEntry(String.format("%s_report_%s.xlsx",assessmentName,i++));
                zipOutputStream.putNextEntry(zipEntry);
                zipOutputStream.write(excel);
                zipOutputStream.closeEntry();
            }
            zipOutputStream.close();
            bytes = byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return new AssessmentPivotReportZipDTO(bytes,assessmentName);
    }

    private void appendQuestionFactFields(StringBuilder prefix, List<String> headers, List<String> assessmentTitleHeaders, SectionQuestionDimDTO sectionQuestionDim, QuestionDimDTO questionDim, StringBuilder fields, String delimiter, String language, Map<Long, QuestionDimDTO> questionDimMap,Integer depth) {


        final String SECTION_QUESTION_ID = sectionQuestionDim.getSectionQuestionId().toString();

        final String QUESTION_ID = questionDim.getQuestionId().toString();
        final String COLUMN_NAME = String.format("%s-%s",SECTION_QUESTION_ID,QUESTION_ID);
        final String QUESTION_TEXT = questionDim.getQuestionText();


        String fieldTemplate,fieldTemplate2;
        String columnName;
        String id;

        switch (questionDim.getQuestionType()) {
            case QuestionType.OBJECTIVE_SUBJECTIVE:

                if(depth==0){
                    fieldTemplate = QueryComponentConstant.SECTION_QUESTIONS_FIELD;
                    columnName = COLUMN_NAME;
                    id = SECTION_QUESTION_ID;
                }
                else{
                    fieldTemplate = QueryComponentConstant.QUESTION_FIELD;
                    columnName = COLUMN_NAME;
                    id = QUESTION_ID;
                }
                fields.append(String.format(fieldTemplate, id, QueryComponentConstant.OBJECTIVE_RESPONSE_COLUMN, columnName))
                        .append(",").append("\n")
                        .append(String.format(fieldTemplate, id, QueryComponentConstant.SUBJECTIVE_RESPONSE_COLUMN, columnName+"_"))
                        .append(",").append("\n");
                headers.add(String.format("%s Option:- %s", prefix, QUESTION_TEXT));
                headers.add(String.format("%s Remarks:- %s", prefix, QUESTION_TEXT));
                assessmentTitleHeaders.add(null);
                assessmentTitleHeaders.add(null);
                break;
            case QuestionType.MULTISELECT_SUBJECTIVE:

                if(depth==0){
                    fieldTemplate = QueryComponentConstant.SECTION_QUESTIONS_FIELD;
                    fieldTemplate2 = QueryComponentConstant.SECTION_QUESTIONS_REPLACE_DELIMITER_FIELD;
                    columnName = COLUMN_NAME;
                    id = SECTION_QUESTION_ID;
                }
                else{
                    fieldTemplate = QueryComponentConstant.QUESTION_FIELD;
                    fieldTemplate2 = QueryComponentConstant.REPLACE_DELIMITER_FIELD;
                    columnName = COLUMN_NAME;
                    id = QUESTION_ID;
                }

                if (delimiter == null)
                    fields.append(String.format(fieldTemplate, id, QueryComponentConstant.MULTISELECT_RESPONSE_COLUMN, columnName));
                else
                    fields.append(String.format(fieldTemplate2, id, delimiter, COLUMN_NAME));

                fields.append(",").append("\n")
                        .append(String.format(fieldTemplate, id, QueryComponentConstant.SUBJECTIVE_RESPONSE_COLUMN, columnName+"_"))
                        .append(",").append("\n");
                headers.add(String.format("%s Options:- %s", prefix, QUESTION_TEXT));
                headers.add(String.format("%s Remarks:- %s", prefix, QUESTION_TEXT));
                assessmentTitleHeaders.add(null);
                assessmentTitleHeaders.add(null);
                break;
            case QuestionType.MULTISELECT:
                if(depth==0){
                    fieldTemplate = QueryComponentConstant.SECTION_QUESTIONS_FIELD;
                    fieldTemplate2 = QueryComponentConstant.SECTION_QUESTIONS_REPLACE_DELIMITER_FIELD;
                    columnName = COLUMN_NAME;
                    id = SECTION_QUESTION_ID;
                }
                else{
                    fieldTemplate = QueryComponentConstant.QUESTION_FIELD;
                    fieldTemplate2 = QueryComponentConstant.REPLACE_DELIMITER_FIELD;
                    columnName = COLUMN_NAME;
                    id = QUESTION_ID;
                }
                if (delimiter == null)
                    fields.append(String.format(fieldTemplate, id, QueryComponentConstant.MULTISELECT_RESPONSE_COLUMN, columnName));
                else
                    fields.append(String.format(fieldTemplate2, id, delimiter, columnName));
                fields.append(",").append("\n");
                headers.add(String.format("%s  %s", prefix, QUESTION_TEXT));
                assessmentTitleHeaders.add(null);
                break;
            case QuestionType.OBJECTIVE:
                if(depth==0){
                    fieldTemplate = QueryComponentConstant.SECTION_QUESTIONS_FIELD;
                    columnName = COLUMN_NAME;
                    id = SECTION_QUESTION_ID;
                }
                else{
                    fieldTemplate = QueryComponentConstant.QUESTION_FIELD;
                    columnName = COLUMN_NAME;
                    id = QUESTION_ID;
                }
                fields.append(String.format(fieldTemplate, id, QueryComponentConstant.QUESTION_RESPONSE_COLUMN, columnName));
                fields.append(",").append("\n");
                headers.add(String.format("%s  %s", prefix, QUESTION_TEXT));
                assessmentTitleHeaders.add(null);
                List<Long> childQuestions = questionDimService.getChildQuestions(questionDim.getQuestionId());
                int a = 1;
                for (Long childQuestion : childQuestions) {
                    String alphabet = numToAlphabet(a);
                    int deleteChars = alphabet.length() + 1;
                    prefix.append('.').append(alphabet);
                    if (!questionDimMap.containsKey(childQuestion))
                        throw new InvalidInputException("Data out of sync try syncing data and try again");
                    appendQuestionFactFields(prefix, headers, assessmentTitleHeaders, sectionQuestionDim, questionDimMap.get(childQuestion), fields, delimiter, language, questionDimMap,depth+1);
                    a++;
                    prefix.delete(prefix.length() - deleteChars, prefix.length());
                }

                break;
            default:
                if(depth==0){
                    fieldTemplate = QueryComponentConstant.SECTION_QUESTIONS_FIELD;
                    columnName = COLUMN_NAME;
                    id = SECTION_QUESTION_ID;
                }
                else{
                    fieldTemplate = QueryComponentConstant.QUESTION_FIELD;
                    columnName = COLUMN_NAME;
                    id = QUESTION_ID;
                }
                fields.append(String.format(fieldTemplate, id, QueryComponentConstant.QUESTION_RESPONSE_COLUMN, columnName));
                fields.append(",").append("\n");
                headers.add(String.format("%s  %s", prefix, QUESTION_TEXT));
                assessmentTitleHeaders.add(null);
                break;
        }

    }
    private void appendQuestionFactFieldsV3(StringBuilder prefix, List<String> headers, SectionQuestionDimDTO sectionQuestionDim, QuestionDimDTO questionDim, StringBuilder fields, String delimiter, Map<Long, QuestionDimDTO> questionDimMap, int depth) {

        final String SECTION_QUESTION_ID = sectionQuestionDim.getSectionQuestionId().toString();
        final String QUESTION_ID = questionDim.getQuestionId().toString();
        final String QUESTION_TEXT = questionDim.getQuestionText();
        final String COLUMN_NAME = String.format("%s-%s",SECTION_QUESTION_ID,QUESTION_ID);

        String fieldTemplate,fieldTemplate2;
        String columnName;
        String id;

        switch (questionDim.getQuestionType()) {
            case QuestionType.OBJECTIVE_SUBJECTIVE:

                if(depth==0){
                    fieldTemplate = QueryComponentConstant.SECTION_QUESTIONS_FIELD;
                    columnName = COLUMN_NAME;
                    id = SECTION_QUESTION_ID;
                }
                else{
                    fieldTemplate = QueryComponentConstant.QUESTION_FIELD;
                    columnName = COLUMN_NAME;
                    id = QUESTION_ID;
                }
                fields.append(String.format(fieldTemplate, id, QueryComponentConstant.OBJECTIVE_RESPONSE_COLUMN, columnName))
                        .append(",").append("\n")
                        .append(String.format(fieldTemplate, id, QueryComponentConstant.SUBJECTIVE_RESPONSE_COLUMN, columnName+"_"))
                        .append(",").append("\n");
                headers.add(String.format("%s Option:- %s", prefix, QUESTION_TEXT));
                headers.add(String.format("%s Remarks:- %s", prefix, QUESTION_TEXT));
                break;
            case QuestionType.MULTISELECT_SUBJECTIVE:
                if(depth==0){
                    fieldTemplate = QueryComponentConstant.SECTION_QUESTIONS_FIELD;
                    fieldTemplate2 = QueryComponentConstant.SECTION_QUESTIONS_REPLACE_DELIMITER_FIELD;
                    columnName = COLUMN_NAME;
                    id = SECTION_QUESTION_ID;
                }
                else{
                    fieldTemplate = QueryComponentConstant.QUESTION_FIELD;
                    fieldTemplate2 = QueryComponentConstant.REPLACE_DELIMITER_FIELD;
                    columnName = COLUMN_NAME;
                    id = QUESTION_ID;
                }
                if (delimiter == null)
                    fields.append(String.format(fieldTemplate, id, QueryComponentConstant.MULTISELECT_RESPONSE_COLUMN, columnName));
                else
                    fields.append(String.format(fieldTemplate2, id, delimiter, columnName));
                fields.append(",").append("\n")
                        .append(String.format(fieldTemplate, id, QueryComponentConstant.SUBJECTIVE_RESPONSE_COLUMN, columnName+"_"))
                        .append(",").append("\n");
                headers.add(String.format("%s Options:- %s", prefix, QUESTION_TEXT));
                headers.add(String.format("%s Remarks:- %s", prefix, QUESTION_TEXT));
                break;
            case QuestionType.MULTISELECT:
                if(depth==0){
                    fieldTemplate = QueryComponentConstant.SECTION_QUESTIONS_FIELD;
                    fieldTemplate2 = QueryComponentConstant.SECTION_QUESTIONS_REPLACE_DELIMITER_FIELD;
                    columnName = COLUMN_NAME;
                    id = SECTION_QUESTION_ID;
                }
                else{
                    fieldTemplate = QueryComponentConstant.QUESTION_FIELD;
                    fieldTemplate2 = QueryComponentConstant.REPLACE_DELIMITER_FIELD;
                    columnName = COLUMN_NAME;
                    id = QUESTION_ID;
                }
                if (delimiter == null)
                    fields.append(String.format(fieldTemplate, id, QueryComponentConstant.MULTISELECT_RESPONSE_COLUMN, columnName));
                else
                    fields.append(String.format(fieldTemplate2, id, delimiter, columnName));
                fields.append(",").append("\n");
                headers.add(String.format("%s  %s", prefix, QUESTION_TEXT));
                break;
            case QuestionType.OBJECTIVE:
                if(depth==0){
                    fieldTemplate = QueryComponentConstant.SECTION_QUESTIONS_FIELD;
                    columnName = COLUMN_NAME;
                    id = SECTION_QUESTION_ID;
                }
                else{
                    fieldTemplate = QueryComponentConstant.QUESTION_FIELD;
                    columnName = COLUMN_NAME;
                    id = QUESTION_ID;
                }
                fields.append(String.format(fieldTemplate, id, QueryComponentConstant.QUESTION_RESPONSE_COLUMN, columnName));
                fields.append(",").append("\n");
                headers.add(String.format("%s  %s", prefix, QUESTION_TEXT));
                List<Long> childQuestions = questionDimService.getChildQuestions(questionDim.getQuestionId());
                int a = 1;
                for (Long childQuestion : childQuestions) {
                    String alphabet = numToAlphabet(a);
                    int deleteChars = alphabet.length() + 1;
                    prefix.append('.').append(alphabet);
                    if (!questionDimMap.containsKey(childQuestion))
                        throw new InvalidInputException("Data out of sync try syncing data and try again");
                    appendQuestionFactFieldsV3(prefix, headers, sectionQuestionDim, questionDimMap.get(childQuestion), fields, delimiter, questionDimMap, 1+depth);
                    a++;
                    prefix.delete(prefix.length() - deleteChars, prefix.length());
                }

                break;
            default:
                if(depth==0){
                    fieldTemplate = QueryComponentConstant.SECTION_QUESTIONS_FIELD;
                    columnName = COLUMN_NAME;
                    id = SECTION_QUESTION_ID;
                }
                else{
                    fieldTemplate = QueryComponentConstant.QUESTION_FIELD;
                    columnName = COLUMN_NAME;
                    id = QUESTION_ID;
                }
                fields.append(String.format(fieldTemplate, id, QueryComponentConstant.QUESTION_RESPONSE_COLUMN, columnName));
                fields.append(",").append("\n");
                headers.add(String.format("%s  %s", prefix, QUESTION_TEXT));
                break;
        }

    }

    private List<String[]> provideCsvDataV3(List<Map<String, Object>> result, List<String> headers, Map<Long,QuestionDimDTO> questionDimMap, Map<String, String> names) {
        List<String[]> csvData = new ArrayList<>();

        if (result == null || result.isEmpty()) return null;


        String[] header = new String[headers.size()];
        for (int i = 0; i < headers.size(); i++) header[i] = headers.get(i);
        csvData.add(header);

        SettingBody:
        result.forEach(row -> {
            String[] data = new String[header.length];
            AtomicInteger i = new AtomicInteger(0);
            List<Map.Entry<String, Object>> entries = new ArrayList<>(row.entrySet());
            ListIterator<Map.Entry<String, Object>> it = entries.listIterator();

            while (it.hasNext()) {
                Map.Entry<String, Object> entry = it.next();
                String[] split = entry.getKey().split("-");
                String column = split.length==2?split[1]:entry.getKey();
                String value = entry.getValue() != null ? entry.getValue().toString() : "";

                try {
                    QuestionDimDTO dim = questionDimMap.get(Long.parseLong(column));
                    switch (dim.getQuestionType()) {
                        case QuestionType.OBJECTIVE_SUBJECTIVE:
                        case QuestionType.MULTISELECT_SUBJECTIVE:
                            data[i.get()] = value;
                            if (it.hasNext()) {
                                Map.Entry<String, Object> subjective = it.next();
                                String subColumn = subjective.getKey().split("_")[0];
                                String subValue = subjective.getValue() != null ? subjective.getValue().toString() : "";

                                if (subColumn.equals(column)) {
                                    i.getAndIncrement();
                                    data[i.get()] = subValue;
                                } else {
                                    it.hasPrevious();
                                    it.previous();
                                }
                            }
                            break;
                        default:
                            data[i.get()] = value;
                            break;
                    }
                }
                catch (NumberFormatException e) {
                    data[i.get()] = value;
                    if(column.equalsIgnoreCase("User")) data[i.incrementAndGet()] = names.get(value);

                }
                i.incrementAndGet();
            }

            csvData.add(data);
        });


        return csvData;
    }
}
