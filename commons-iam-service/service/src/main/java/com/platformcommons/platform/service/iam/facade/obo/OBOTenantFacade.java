package com.platformcommons.platform.service.iam.facade.obo;

import com.mindtree.bridge.platform.dto.PersonDTO;
import com.platformcommons.platform.service.iam.dto.LeadDTO;
import com.platformcommons.platform.service.iam.dto.TenantDTO;
import com.platformcommons.platform.service.iam.dto.brbase.TLDTenantDTO;
import feign.FeignException;
import org.springframework.transaction.annotation.Transactional;

public interface OBOTenantFacade {

    TenantDTO addTenantInLinkedSystem(TenantDTO tenantDTO, String adminPas);

    TenantDTO addTenantInLinkedSystemVMS(LeadDTO leadDTO, String adminPass, Long worldUserId);

    TenantDTO getTenantFromLinkedSystem(String tenantLogin, String email, String mobile);

    @Transactional(noRollbackFor = {FeignException.class})
    TLDTenantDTO getTenantFromLinkedSystem(String tenantLogin);
}
