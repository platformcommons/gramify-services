package com.platformcommons.platform.service.report.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.report.dto.DataSourceDTO;

public interface DataSourceFacade {

    Long save(DataSourceDTO dataSourceDTO );

    DataSourceDTO update(DataSourceDTO dataSourceDTO );

    DataSourceDTO getById(Long id);

    PageDTO<DataSourceDTO> getAllDataSource(Integer page, Integer size, String sortBy, String direction);

    void deleteDataSource(Long id, String reason);

}
