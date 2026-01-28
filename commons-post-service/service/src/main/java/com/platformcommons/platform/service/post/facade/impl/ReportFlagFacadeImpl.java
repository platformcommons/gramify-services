package com.platformcommons.platform.service.post.facade.impl;

import com.platformcommons.platform.service.post.domain.ReportFlag;
import com.platformcommons.platform.service.post.dto.ReportFlagDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.platformcommons.platform.service.post.facade.ReportFlagFacade;
import org.springframework.transaction.annotation.Transactional;

import com.platformcommons.platform.service.post.application.ReportFlagService;
import com.platformcommons.platform.service.post.facade.assembler.ReportFlagDTOAssembler;
import java.util.stream.Collectors;


import java.util.*;

@Component
@Transactional
public class ReportFlagFacadeImpl implements ReportFlagFacade {


    @Autowired
    private ReportFlagService service;

    @Autowired
    private ReportFlagDTOAssembler assembler;


    @Override
    public Long save(ReportFlagDTO reportFlagDTO ){
        return service.save(assembler.fromDTO(reportFlagDTO));
    }


    @Override
    public ReportFlagDTO update(ReportFlagDTO reportFlagDTO ){
        return assembler.toDTO(service.update(assembler.fromDTO(reportFlagDTO)));
    }

    @Override
    public PageDTO<ReportFlagDTO> getAllPage(Integer page, Integer size, String reportType, String marketCode){
        Page<ReportFlag> result= service.getByPage(page,size,reportType,marketCode);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext(),result.getTotalElements());
    }

    @Override
    public void delete(Long id,String reason){
        service.deleteById(id,reason);
    }

    @Override
    public ReportFlagDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }

    @Override
    public Map<String,Set<ReportFlagDTO>> getReportFlagsByLoggedInUser(Set<String> entityTypes){
        Set<ReportFlag> reportFlags = service.getReportFlagsByLoggedInUser(entityTypes);
        Set<ReportFlagDTO> reportFlagDTOS = reportFlags!=null ? reportFlags.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new)):Collections.emptySet();
        assert reportFlags != null;

        return reportFlagDTOS.stream()
                .collect(Collectors.groupingBy(
                        ReportFlagDTO::getReportedOnEntityType,
                        Collectors.toCollection(LinkedHashSet::new)
                ));


    }

    @Override
    public ReportFlagDTO patchReportFlag(ReportFlagDTO body) {
        return assembler.toDTO(service.patchReportFlag(assembler.fromDTO(body)));
    }
}
