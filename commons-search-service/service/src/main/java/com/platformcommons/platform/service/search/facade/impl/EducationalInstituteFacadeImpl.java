package com.platformcommons.platform.service.search.facade.impl;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.application.service.EducationalInstituteService;
import com.platformcommons.platform.service.search.dto.EducationalInstituteDTO;
import com.platformcommons.platform.service.search.facade.EducationalInstituteFacade;
import com.platformcommons.platform.service.search.facade.assembler.EducationalInstituteDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@Transactional
public class EducationalInstituteFacadeImpl implements EducationalInstituteFacade {

    @Autowired
    EducationalInstituteService educationalInstituteService;

    @Autowired
    EducationalInstituteDTOAssembler educationalInstituteDTOAssembler;
    @Override
    public EducationalInstituteDTO saveEducationalInstitute(EducationalInstituteDTO body) {
        return educationalInstituteDTOAssembler.toDTO(educationalInstituteService.
                saveEducationalInstitute(educationalInstituteDTOAssembler.fromDTO(body)));
    }

    @Override
    public PageDTO<EducationalInstituteDTO> readByName(String keyword) {
        Set<EducationalInstituteDTO> set=educationalInstituteService.readByName(keyword).
                stream().map(ob->educationalInstituteDTOAssembler.toDTO(ob)).collect(Collectors.toSet());
        return new PageDTO<>(set,true,set.size());
    }

    @Override
    public String updateEducationalInstitute(EducationalInstituteDTO body) {
        return educationalInstituteService.updateEducationalInstitute(educationalInstituteDTOAssembler.fromDTO(body));
    }
}
