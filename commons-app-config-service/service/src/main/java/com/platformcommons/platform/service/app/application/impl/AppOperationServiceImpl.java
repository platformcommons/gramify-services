package com.platformcommons.platform.service.app.application.impl;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.app.application.AppRbacAsync;
import com.platformcommons.platform.service.app.application.utility.PlatformUtil;
import com.platformcommons.platform.service.app.domain.AppOperation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;



import com.platformcommons.platform.service.app.application.AppOperationService;
import com.platformcommons.platform.service.app.domain.repo.AppOperationRepository;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class AppOperationServiceImpl implements AppOperationService {


    @Autowired
    private AppOperationRepository repository;

    @Autowired
    private AppRbacAsync appRbacAsync;


    @Override
    public Long save(AppOperation appOperation ){
        PlatformUtil.validateTenantAdmin();
        return repository.save(appOperation).getId();
    }


    @Override
    public AppOperation update(AppOperation appOperation, Boolean isPatch) {
        AppOperation fetchedAppOperation = repository.findById(appOperation.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Request AppOperation with  %d  not found", appOperation.getId())));
        isPatch = isPatch != null ? isPatch : Boolean.TRUE;
        if (isPatch) {
            fetchedAppOperation.patch(appOperation);
        } else {
            fetchedAppOperation.update(appOperation);
        }
        return repository.save(fetchedAppOperation);
    }




    @Override
    public Page<AppOperation> getByPage(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page,size));
    }

    @Override
    public void deleteById(Long id, String reason) {
        AppOperation fetchedAppOperation = repository.findById(id)
           		.orElseThrow(()-> new NotFoundException(String.format("Request AppOperation with  %d  not found",id)));
        PlatformUtil.validateLoginTenant(fetchedAppOperation.getCreatedByTenant());
        fetchedAppOperation.deactivate(reason);
        repository.save(fetchedAppOperation);
    }


    @Override
    public AppOperation getById(Long id) {
       return repository.findById(id)
    		.orElseThrow(()-> new NotFoundException(String.format("Request AppOperation with  %d  not found",id)));
    }

    @Override
    public Page<AppOperation> getAppOperationByAppCodeAppRoleAndEntity(String appCode, Integer page, Integer size, String role, String entity) {
        return repository.findByAppCodeAppRoleAndEntity(appCode,role,entity,PageRequest.of(page,size));
    }

    @Override
    public void updateAppOperationInBulk(Set<AppOperation> appOperations) {
        Map<Long, AppOperation> nonZeroAppOperationMap = appOperations.stream()
                .filter(appOperation -> appOperation.getId() != null && appOperation.getId() > 0)
                .collect(Collectors.toMap(AppOperation::getId, appOperation -> appOperation));

        List<AppOperation> fetchedNonZeroAppOperionlist=repository.findAllById(nonZeroAppOperationMap.keySet());

        fetchedNonZeroAppOperionlist.forEach(fetchedNonZeroAppOperation ->
                fetchedNonZeroAppOperation.patch(nonZeroAppOperationMap.get(fetchedNonZeroAppOperation.getId())) );
        repository.saveAll(fetchedNonZeroAppOperionlist);
    }

    @Override
    public Set<AppOperation> getLowerRbacAppOperation(String appCode, String entity, Integer priority) {
        return repository.getLowerRbacAppOperation(appCode, entity, priority, PlatformSecurityUtil.getCurrentTenantId());
    }

    @Override
    public Set<AppOperation> getHigherRbacAppOperation(String appCode, String entity, Integer priority) {
        return repository.getHigherRbacAppOperation(appCode, entity, priority, PlatformSecurityUtil.getCurrentTenantId());
    }

    @Override
    public List<AppOperation> updateRbacOperation(Set<AppOperation> appOperations) {
        return repository.saveAll(appOperations);
    }

    @Override
    public AppOperation patch(AppOperation appOperation) {
        AppOperation fetchedAppOperation = repository.findById(appOperation.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Request AppOperation with  %d  not found", appOperation.getId())));
        fetchedAppOperation.patch(appOperation);
        return repository.save(fetchedAppOperation);
    }

}
