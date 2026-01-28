package com.platformcommons.platform.service.search.application.service;

import com.platformcommons.platform.service.search.domain.CommonsAreaPinCode;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public interface CommonsAreaPinCodeService {
    Set<CommonsAreaPinCode> readPincodeByAreaCode(String keyword);

    CommonsAreaPinCode update(CommonsAreaPinCode commonsAreaPinCode);

    CommonsAreaPinCode save(CommonsAreaPinCode commonsAreaPinCode);
}
