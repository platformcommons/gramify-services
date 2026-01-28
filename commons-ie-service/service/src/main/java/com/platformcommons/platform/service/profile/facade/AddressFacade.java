package com.platformcommons.platform.service.profile.facade;

import com.platformcommons.platform.service.profile.domain.Address;
import com.platformcommons.platform.service.profile.dto.AddressDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import java.util.*;

public interface AddressFacade {

    Long save(AddressDTO addressDTO );

    AddressDTO update(AddressDTO addressDTO );

    PageDTO<AddressDTO> getAllPage(Integer page, Integer size);

    void delete(Long id,String reason);

    AddressDTO getById(Long id);


}
