package com.platformcommons.platform.service.report.application.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.report.application.DataSourceService;
import com.platformcommons.platform.service.report.domain.DataSource;
import com.platformcommons.platform.service.report.domain.repo.DataSourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DataSourceServiceImpl implements DataSourceService {
    @Autowired
    private DataSourceRepository repository;

    @Override
    public Long save(DataSource dataSource ){
        return repository.save(dataSource).getId();
    }

    @Override
    public DataSource update(DataSource dataSource) {
        DataSource fetchedDataSource = repository.findById(dataSource.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Request DataSource with  " +
                        "%d  not found", dataSource.getId())));
        fetchedDataSource.update(dataSource);
        return repository.save(fetchedDataSource);
    }

    @Override
    public DataSource getById(Long id) {
       return repository.findById(id)
    		.orElseThrow(()-> new NotFoundException(String.format("Request DataSource with  %d  not found",id)));
    }

    @Override
    public Page<DataSource> getAllDataSource(Integer page, Integer size, Sort orders) {
        return repository.findAll(PageRequest.of(page,size,orders));
    }

    @Override
    public void deleteDataSource(Long id, String reason) {
        DataSource fetchedDataSource = getById(id);
        fetchedDataSource.deactivate(reason);
        repository.save(fetchedDataSource);
    }
}
