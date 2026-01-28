package com.platformcommons.platform.service.report.application;

import com.platformcommons.platform.service.report.domain.DataSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface DataSourceService {

    Long save(DataSource dataSource );

    DataSource update(DataSource dataSource );

    DataSource getById(Long id);

    Page<DataSource> getAllDataSource(Integer page, Integer size, Sort orders);

    void deleteDataSource(Long id, String reason);

}
