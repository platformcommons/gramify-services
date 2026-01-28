package com.platformcommons.platform.service.iam.facade.assembler;

import com.platformcommons.platform.service.iam.domain.Authority;
import com.platformcommons.platform.service.iam.dto.AuthorityDTO;

import java.util.Set;

public interface AuthorityDTOAssembler {

    AuthorityDTO toDTO(Authority authority);
    Authority fromDTO(AuthorityDTO dto);

    Set<Authority> fromDTOs(Set<AuthorityDTO> authorities);

    Set<AuthorityDTO> toDTOs(Set<Authority> authorities);
}
