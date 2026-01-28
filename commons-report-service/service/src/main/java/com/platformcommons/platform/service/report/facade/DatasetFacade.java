package com.platformcommons.platform.service.report.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.report.application.constant.FileType;
import com.platformcommons.platform.service.report.dto.DatasetDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DatasetFacade {

    Long save(DatasetDTO datasetDTO, Long datasource, Set<String> datasetGroupCodes);

    DatasetDTO update(DatasetDTO datasetDTO );


    DatasetDTO getById(Long id);

    DatasetDTO getByName(String name);


    Object execute(String name, String params);


    PageDTO<DatasetDTO> getAllDataset(Integer page, Integer size, String sortBy, String direction);

    byte[] executeQueryDownload(String name, String param, FileType fileType);

    Object executeV2(String name, String params,Integer page, Integer size);

    Object executeV3(String name, String params, Integer page, Integer size);

    List<DatasetDTO> getByListOfNames(List<String> names);

    Object executeV2(String name, String params,String startDate, String endDate,Integer page, Integer size);

    Object execute(String name, Map<String, String> params);
}
