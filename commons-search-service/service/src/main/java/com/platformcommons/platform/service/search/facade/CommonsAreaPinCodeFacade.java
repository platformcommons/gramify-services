package com.platformcommons.platform.service.search.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.dto.CommonsAreaPinCodeDTO;
import org.springframework.stereotype.Component;

@Component
public interface CommonsAreaPinCodeFacade {

    CommonsAreaPinCodeDTO update(CommonsAreaPinCodeDTO body);

    CommonsAreaPinCodeDTO save(CommonsAreaPinCodeDTO body);

    PageDTO<CommonsAreaPinCodeDTO> readPincode(String keyword);
}
