package com.platformcommons.platform.service.iam.facade.assembler.impl;

import com.platformcommons.platform.service.iam.domain.Authority;
import com.platformcommons.platform.service.iam.dto.AuthorityDTO;
import com.platformcommons.platform.service.iam.facade.assembler.AuthorityDTOAssembler;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuthorityDTOAssemblerImpl implements AuthorityDTOAssembler {

    @Override
    public AuthorityDTO toDTO(Authority authority) {
        return AuthorityDTO.builder()
                .code(authority.getCode())
                .authorityDescription(authority.getAuthorityDescription())
                .process(authority.getProcess())
                .build();
    }

    @Override
    public Authority fromDTO(AuthorityDTO dto) {
        return Authority.builder()
                .code(dto.getCode())
                .authorityDescription(dto.getAuthorityDescription())
                .process(dto.getProcess())
                .build();
    }

    @Override
    public Set<Authority> fromDTOs(Set<AuthorityDTO> authorities) {
        if(authorities==null || authorities.isEmpty()) return new LinkedHashSet<>();
        return authorities.stream().map(this::fromDTO).collect(Collectors.toSet());
    }

    @Override
    public Set<AuthorityDTO> toDTOs(Set<Authority> authorities) {
        if(authorities==null || authorities.isEmpty()) return new LinkedHashSet<>();
        return authorities.stream().map(this::toDTO).collect(Collectors.toSet());
    }
}
