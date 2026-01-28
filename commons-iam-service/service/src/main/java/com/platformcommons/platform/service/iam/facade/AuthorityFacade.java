package com.platformcommons.platform.service.iam.facade;

import com.platformcommons.platform.service.iam.dto.AuthorityDTO;
import com.platformcommons.platform.service.iam.dto.PageAuthDTO;

import java.util.Set;

public interface AuthorityFacade {
    void addAuthorities(Set<AuthorityDTO> authorities);

    PageAuthDTO<AuthorityDTO> getALlAuthorities(String process, Integer page, Integer size);
}
