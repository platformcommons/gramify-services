package com.platformcommons.platform.service.profile.facade;

import com.platformcommons.platform.service.profile.domain.PersonBankDetail;
import com.platformcommons.platform.service.profile.dto.PersonBankDetailDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import java.util.*;

public interface PersonBankDetailFacade {

    Long save(PersonBankDetailDTO personBankDetailDTO );

    PersonBankDetailDTO update(PersonBankDetailDTO personBankDetailDTO );

    PageDTO<PersonBankDetailDTO> getAllPage(Integer page, Integer size);

    void delete(Long id,String reason);

    PersonBankDetailDTO getById(Long id);


}
