package com.platformcommons.platform.service.iam.facade.impl;

import com.platformcommons.platform.service.iam.application.AuthorityService;
import com.platformcommons.platform.service.iam.domain.Authority;
import com.platformcommons.platform.service.iam.dto.AuthorityDTO;
import com.platformcommons.platform.service.iam.dto.PageAuthDTO;
import com.platformcommons.platform.service.iam.facade.AuthorityFacade;
import com.platformcommons.platform.service.iam.facade.assembler.AuthorityDTOAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class AuthorityFacadeImpl implements AuthorityFacade {

    private final AuthorityService authorityService;
    private final AuthorityDTOAssembler authorityDTOAssembler;


    @Override
    public void addAuthorities(Set<AuthorityDTO> authorities) {
        Set<Authority> set = authorityDTOAssembler.fromDTOs(authorities);
        authorityService.addAuthorities(set);
    }

    @Override
    public PageAuthDTO<AuthorityDTO> getALlAuthorities(String process, Integer page, Integer size) {
        Page<Authority> pg = authorityService.getALlAuthorities(process,page,size);
        return new PageAuthDTO<>(authorityDTOAssembler.toDTOs(pg.toSet()),pg.hasNext(),pg.getTotalElements());
    }
}
