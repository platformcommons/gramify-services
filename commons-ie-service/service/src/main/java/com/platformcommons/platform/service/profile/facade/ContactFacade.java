package com.platformcommons.platform.service.profile.facade;

import com.platformcommons.platform.service.profile.domain.Contact;
import com.platformcommons.platform.service.profile.dto.ContactDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import java.util.*;

public interface ContactFacade {

    Long save(ContactDTO contactDTO );

    ContactDTO update(ContactDTO contactDTO );

    PageDTO<ContactDTO> getAllPage(Integer page, Integer size);

    void delete(Long id,String reason);

    ContactDTO getById(Long id);


}
