package com.platformcommons.platform.service.report.application;

import com.platformcommons.platform.service.report.application.constant.FileType;
import com.platformcommons.platform.service.report.domain.Dataset;
import com.platformcommons.platform.service.report.dto.DatasetDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DatasetService {

    Long save(Dataset dataset, Long datasource, Set<String> datasetGroupCodes);

    Dataset update(Dataset dataset );

    Dataset getById(Long id);

    Dataset getByName(String name);

    Object execute(String name, String params, Map<String,String> paramsMap) ;


    @Transactional(readOnly = true)
    Object executeV2(String name, String params,Integer page , Integer size);

    Page<Dataset> getAllDataset(Integer page, Integer size, Sort orders);

    byte[] executeQueryDownload(String name, String param, FileType fileType);

    Object executeV3(String name, String params, Integer page, Integer size);

    List<Dataset> getByListOfNames(List<String> names);

    Object executeV3(String name, String params, String startDate, String endDate, Integer page, Integer size);
}
