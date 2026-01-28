package com.platformcommons.platform.service.app.application;

import com.platformcommons.platform.service.app.domain.AppOperation;

import org.springframework.data.domain.Page;

import java.util.*;

public interface AppOperationService {

    Long save(AppOperation appOperation );

    AppOperation update(AppOperation appOperation, Boolean isPatch);

    Page<AppOperation> getByPage(Integer page, Integer size);

    void deleteById(Long id,String reason);

    AppOperation getById(Long id);

    Page<AppOperation> getAppOperationByAppCodeAppRoleAndEntity(String appCode, Integer page, Integer size, String role, String entity);

    void updateAppOperationInBulk(Set<AppOperation> appOperations);

    Set<AppOperation> getLowerRbacAppOperation(String appCode, String entity, Integer priority);

    Set<AppOperation> getHigherRbacAppOperation(String appCode, String entity, Integer priority);

    List<AppOperation> updateRbacOperation(Set<AppOperation> lowerRbacAppOperations);

    AppOperation patch(AppOperation appOperation);
}
