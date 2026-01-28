package com.platformcommons.platform.service.app.application.impl;

import com.platformcommons.platform.service.app.domain.AppConfigScopeMaster;
import org.springframework.data.domain.Page;
import com.platformcommons.platform.service.app.application.AppConfigScopeMasterService;
import com.platformcommons.platform.service.app.domain.repo.AppConfigScopeMasterRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class AppConfigScopeMasterServiceImpl implements AppConfigScopeMasterService {

    @Autowired
    public AppConfigScopeMasterRepository appconfigscopemasterRepository;

    @Override
    public Long save(AppConfigScopeMaster appconfigscopemaster) {
        return appconfigscopemasterRepository.save(appconfigscopemaster).getId();
    }

    @Override
    public AppConfigScopeMaster update(AppConfigScopeMaster appconfigscopemaster) {
        AppConfigScopeMaster fetchedAppConfigScopeMaster = appconfigscopemasterRepository.findById(appconfigscopemaster.getId())
		.orElseThrow(()-> new NotFoundException("Request id  not found"));
        return appconfigscopemasterRepository.save(appconfigscopemaster);
    }

    @Override
    public void deleteById(Long id, String reason) {
        AppConfigScopeMaster fetchedAppConfigScopeMaster = appconfigscopemasterRepository.findById(id)
		.orElseThrow(()-> new NotFoundException("Request id  not found"));
       // fetchedAppConfigScopeMaster.deactivate(reason);
        appconfigscopemasterRepository.save(fetchedAppConfigScopeMaster);
    }

    @Override
    public AppConfigScopeMaster getById(Long id) {
        return appconfigscopemasterRepository.findById(id)
		.orElseThrow(()-> new NotFoundException("Request id  not found"));
    }

    @Override
    public Page<AppConfigScopeMaster> getByPage(Integer page, Integer size) {
        return appconfigscopemasterRepository.findAll(PageRequest.of(page,size));
    }
}