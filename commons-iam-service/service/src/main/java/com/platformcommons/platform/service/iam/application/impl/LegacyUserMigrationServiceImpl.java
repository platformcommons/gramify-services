package com.platformcommons.platform.service.iam.application.impl;

import com.platformcommons.platform.security.token.PlatformToken;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.iam.application.LegacyUserMigrationService;
import com.platformcommons.platform.service.iam.application.TenantService;
import com.platformcommons.platform.service.iam.application.UserService;
import com.platformcommons.platform.service.iam.application.utility.TriggerNotification;
import com.platformcommons.platform.service.iam.domain.Tenant;
import com.platformcommons.platform.service.iam.domain.User;
import com.platformcommons.platform.service.iam.dto.brbase.TLDTenantDTO;
import com.platformcommons.platform.service.iam.dto.brbase.UserDTO;
import com.platformcommons.platform.service.iam.facade.UserFacade;
import com.platformcommons.platform.service.iam.facade.assembler.TenantDTOAssembler;
import com.platformcommons.platform.service.iam.facade.assembler.IAMUserDTOAssembler;
import com.platformcommons.platform.service.iam.facade.assembler.obo.TLDTenantDTOAssembler;
import com.platformcommons.platform.service.iam.facade.client.TLDClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
@RequiredArgsConstructor
public class LegacyUserMigrationServiceImpl implements LegacyUserMigrationService {


    private final TLDClient tLDClient;
    private final TenantService tenantService;
    private final TLDTenantDTOAssembler tldTenantDTOAssembler;
    private final TenantDTOAssembler tenantDTOAssembler;
    private final IAMUserDTOAssembler iamUserDTOAssembler;
    private UserService userService;
    private final UserFacade userFacade;
    private TriggerNotification triggerNotification;

    @Override
    @Transactional
    public void migrateCurrentSessionUser() {
        try {
            PlatformToken token = PlatformSecurityUtil.getContext();
            Long tenantId = token.getTenantContext().getTenantId();

            Boolean tenantExists = tenantService.existsByTenantId(tenantId);
            Boolean userExists = userService.existById(token.getUserContext().getUserId());

            if(!tenantExists){
                TLDTenantDTO tldTenantDTO = tLDClient.getTenant(token.getToken(),null,null);
                Tenant tenantDTO =  tenantDTOAssembler.fromDTO(tldTenantDTOAssembler.fromTLDTenantDTO(tldTenantDTO, null,null));
                tenantService.addTenant(tenantDTO,null);
            }
            if(!userExists){
                UserDTO userDetails = tLDClient.getUserDetails(token.getToken(),token.getUserContext().getUserId().intValue(),
                        null).getBody();
                User toCreateUser = iamUserDTOAssembler.fromDTO(userDetails,tenantId);
                userFacade.addUserWithoutPassword(toCreateUser);
            }
        }
        catch (Exception exception){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            // TODO
            //triggerNotification.reportError(exception);
        }

    }

    @Autowired
    @Lazy
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    @Lazy
    public void setNotificationUtil(TriggerNotification notificationUtil) {
        this.triggerNotification = notificationUtil;
    }
}
