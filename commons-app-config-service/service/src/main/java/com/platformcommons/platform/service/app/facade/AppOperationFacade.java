package com.platformcommons.platform.service.app.facade;

import com.platformcommons.platform.service.app.dto.AppOperationDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import java.util.*;

public interface AppOperationFacade {

    Long save(AppOperationDTO appOperationDTO );

    AppOperationDTO update(AppOperationDTO appOperationDTO , Boolean isPatch);

    PageDTO<AppOperationDTO> getAllPage(Integer page, Integer size);

    void delete(Long id,String reason);

    AppOperationDTO getById(Long id);

    PageDTO<AppOperationDTO> getAppOperationByAppCodeAppRoleAndEntity(String appCode, Integer page, Integer size, String role, String entity);

    void updateAppOperationInBulk(Set<AppOperationDTO> appOperationDTOS);

    AppOperationDTO patch(AppOperationDTO appOperationDTO, Boolean syncHigherRbacOperation, Boolean syncLowerRbacOperation);
}
