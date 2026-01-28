package com.platformcommons.platform.service.report.facade.impl;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.report.application.DataSourceService;
import com.platformcommons.platform.service.report.domain.DataSource;
import com.platformcommons.platform.service.report.dto.DataSourceDTO;
import com.platformcommons.platform.service.report.facade.DataSourceFacade;
import com.platformcommons.platform.service.report.facade.assembler.DataSourceDTOAssembler;
import com.platformcommons.platform.service.utility.JPAUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class DataSourceFacadeImpl implements DataSourceFacade {


    @Autowired
    private DataSourceService service;

    @Autowired
    private DataSourceDTOAssembler assembler;


    @Override
    public Long save(DataSourceDTO dataSourceDTO ){
        return service.save(assembler.fromDTO(dataSourceDTO));
    }

    @Override
    public DataSourceDTO update(DataSourceDTO dataSourceDTO ){
        return assembler.toDTO(service.update(assembler.fromDTO(dataSourceDTO)));
    }
    
    @Override
    public DataSourceDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }

    @Override
    public PageDTO<DataSourceDTO> getAllDataSource(Integer page, Integer size, String sortBy, String direction) {
        Page<DataSource> dataSourcePage = service.getAllDataSource(page, size, JPAUtility.convertToSort(sortBy, direction));
        return new PageDTO<>(assembler.toDTOs(dataSourcePage.toSet()), dataSourcePage.hasNext());
    }

    @Override
    public void deleteDataSource(Long id, String reason) {
        service.deleteDataSource(id,reason);
    }

}
