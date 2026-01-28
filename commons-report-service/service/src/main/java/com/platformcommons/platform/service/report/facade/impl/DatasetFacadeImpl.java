package com.platformcommons.platform.service.report.facade.impl;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.report.application.DatasetService;
import com.platformcommons.platform.service.report.application.constant.FileType;
import com.platformcommons.platform.service.report.domain.Dataset;
import com.platformcommons.platform.service.report.dto.DatasetDTO;
import com.platformcommons.platform.service.report.facade.DatasetFacade;
import com.platformcommons.platform.service.report.facade.assembler.DatasetDTOAssembler;
import com.platformcommons.platform.service.utility.JPAUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Transactional
public class DatasetFacadeImpl implements DatasetFacade {


    @Autowired
    private DatasetService service;

    @Autowired
    private DatasetDTOAssembler assembler;



    @Override
    public Long save(DatasetDTO datasetDTO, Long datasource, Set<String> datasetGroupCodes) {
        return service.save(assembler.fromDTO(datasetDTO),datasource,datasetGroupCodes);
    }


    @Override
    public DatasetDTO update(DatasetDTO datasetDTO ){
        return assembler.toDTO(service.update(assembler.fromDTO(datasetDTO)));
    }

    @Override
    public DatasetDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }


    @Override
    public DatasetDTO getByName(String name) {
        return assembler.toDTO(service.getByName(name));
    }

    @Override
    public Object execute(String name, String params) {
        return service.execute(name,params,null);
    }
    @Override
    public Object execute(String name, Map<String, String> params) {
        return service.execute(name,null,params);
    }

    @Override
    public Object executeV2(String name, String params,Integer page, Integer size) {
        return service.executeV2(name,params,page,size);
    }

    @Override
    public Object executeV3(String name, String params, Integer page, Integer size) {
        return service.executeV3(name, params, page, size);
    }

    @Override
    public List<DatasetDTO> getByListOfNames(List<String> names) {
        return service.getByListOfNames(names).stream().map(assembler::toDTO).collect(Collectors.toList());
    }


    @Override
    public Object executeV2(String name, String params, String startDate, String endDate, Integer page, Integer size) {
        return service.executeV3(name,params,startDate,endDate,page,size);
    }

    @Override
    public PageDTO<DatasetDTO> getAllDataset(Integer page, Integer size, String sortBy, String direction) {
        Page<Dataset> datasetPage = service.getAllDataset(page,size, JPAUtility.convertToSort(sortBy,direction));
        return new PageDTO<>(assembler.toDTOs(datasetPage.toSet()),datasetPage.hasNext());
    }
    @Override
    public byte[] executeQueryDownload(String name, String param, FileType fileType) {
        return service.executeQueryDownload(name, param,fileType);
    }
}
