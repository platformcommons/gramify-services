package com.platformcommons.platform.service.search.application.service.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.search.application.service.CommonsAreaPinCodeService;
import com.platformcommons.platform.service.search.domain.CommonsAreaPinCode;
import com.platformcommons.platform.service.search.domain.repo.CommonsAreaPinCodeRepository;
import com.platformcommons.platform.service.search.dto.CommonsAreaPinCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CommonsAreaPinCodeServiceImpl implements CommonsAreaPinCodeService {

    @Autowired
    private CommonsAreaPinCodeRepository repository;

    @Override
    public Set<CommonsAreaPinCode> readPincodeByAreaCode(String keyword) {
        return repository.findByCustomQuery(keyword);
    }

    @Override
    public CommonsAreaPinCode update(CommonsAreaPinCode data) {
        CommonsAreaPinCode fetchedCommonsAreaPinCode = repository.findById(data.getId())
                .orElseThrow(()-> new NotFoundException(String.format("Request CommonsAreaPinCode  with  %d  not found", data.getId())));

        fetchedCommonsAreaPinCode.update(data);
        return repository.save(fetchedCommonsAreaPinCode);
    }

    @Override
    public CommonsAreaPinCode save(CommonsAreaPinCode commonsAreaPinCode) {
        return repository.save(commonsAreaPinCode);
    }
}
