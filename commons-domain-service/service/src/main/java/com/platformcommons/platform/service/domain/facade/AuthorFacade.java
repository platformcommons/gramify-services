package com.platformcommons.platform.service.domain.facade;

import com.platformcommons.platform.service.domain.dto.AuthorDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

public interface AuthorFacade {

    Long save(AuthorDTO authorDTO );

    AuthorDTO update(AuthorDTO authorDTO );

    PageDTO<AuthorDTO> getAllPage(Integer page, Integer size);

    void delete(Long id,String reason);

    AuthorDTO getById(Long id);


    AuthorDTO getAuthorByTenant(Long tenantId);
}
