package com.platformcommons.platform.service.iam.facade.impl.obo;

import com.mindtree.bridge.platform.dto.PersonDTO;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.iam.application.utility.OBOSecurityUtil;
import com.platformcommons.platform.service.iam.dto.LeadDTO;
import com.platformcommons.platform.service.iam.dto.TenantDTO;
import com.platformcommons.platform.service.iam.dto.brbase.ChangeMakerSignupDTO;
import com.platformcommons.platform.service.iam.dto.brbase.TLDTenantDTO;
import com.platformcommons.platform.service.iam.facade.assembler.obo.TLDTenantDTOAssembler;
import com.platformcommons.platform.service.iam.facade.client.TLDClient;
import com.platformcommons.platform.service.iam.facade.obo.OBOTenantFacade;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class OBOTenantFacadeImpl implements OBOTenantFacade {

    private final TLDClient tldClient;
    private final TLDTenantDTOAssembler tldTenantDTOAssembler;
    private final OBOSecurityUtil oboSecurityUtil;


    public OBOTenantFacadeImpl(TLDClient tldClient,
                               TLDTenantDTOAssembler tldTenantDTOAssembler,
                               OBOSecurityUtil oboSecurityUtil) {
        this.tldClient = tldClient;
        this.tldTenantDTOAssembler = tldTenantDTOAssembler;
        this.oboSecurityUtil = oboSecurityUtil;

    }

    @Override
    public TenantDTO addTenantInLinkedSystem(TenantDTO tenantDTO, String adminPass) {
        TLDTenantDTO tldTenantDTO = tldTenantDTOAssembler.fromTenantDTO(tenantDTO);
        tldTenantDTO = tldClient.addTenant(tldTenantDTO, adminPass, PlatformSecurityUtil.getToken());
        tenantDTO = tldTenantDTOAssembler.fromTLDTenantDTO(tldTenantDTO, tenantDTO.getMobile(), tenantDTO.getEmail());
        return tenantDTO;
    }

    @Override
    public TenantDTO addTenantInLinkedSystemVMS(LeadDTO leadDTO, String adminPass, Long worldUserId) {
        oboSecurityUtil.initPrivilegeUser();
        ChangeMakerSignupDTO changeMakerSignupDTO = new ChangeMakerSignupDTO(leadDTO, adminPass);
        TLDTenantDTO tldTenantDTO = tldClient.onBoardTenantVMS(changeMakerSignupDTO,worldUserId,PlatformSecurityUtil.getToken());
        return tldTenantDTOAssembler.fromTLDTenantDTO(tldTenantDTO, leadDTO.getMobile(), leadDTO.getEmail());
    }

    @Transactional(noRollbackFor = {FeignException.class})
    @Override
    public TenantDTO getTenantFromLinkedSystem(String tenantLogin, String email, String mobile) {
        try {
            TLDTenantDTO tldTenantDTO = tldClient.getTenant(PlatformSecurityUtil.getToken(), tenantLogin, null);
            return tldTenantDTOAssembler.fromTLDTenantDTO(tldTenantDTO, mobile, email);
        } catch (FeignException ex) {
            log.warn("--->Exception occurred while fetching existing tenant", ex);
            return null;
        }
    }

    @Transactional(noRollbackFor = {FeignException.class})
    @Override
    public TLDTenantDTO getTenantFromLinkedSystem(String tenantLogin) {
        try {
            return tldClient.getTenant(PlatformSecurityUtil.getToken(), tenantLogin, null);
        } catch (FeignException ex) {
            log.warn("--->Exception occurred while fetching existing tenant", ex);
            return null;
        }
    }
}
