package com.platformcommons.platform.service.iam.facade.assembler.obo;


import com.platformcommons.platform.service.iam.application.utility.TenantOnBoardUtil;
import com.platformcommons.platform.service.iam.dto.LeadDTO;
import com.platformcommons.platform.service.iam.dto.TenantDTO;
import com.platformcommons.platform.service.iam.dto.brbase.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.UUID;

@Component
public class TLDTenantDTOAssembler {

    public TenantDTO fromTLDTenantDTO(TLDTenantDTO tldTenantDTO, String mobile, String email) {
        TenantDTO.TenantDTOBuilder builder = TenantDTO.builder()
                .id(tldTenantDTO.getId())
                .tenantLogin(tldTenantDTO.getLoginName())
                .tenantName(tldTenantDTO.getName())
                .description(tldTenantDTO.getDescription())
                .website(tldTenantDTO.getNotes() != null
                        && tldTenantDTO.getNotes().startsWith("https://") ? tldTenantDTO.getNotes() : null)
                .iconpic(tldTenantDTO.getIconpic())
                .onBoardedDateTime(tldTenantDTO.getCreatedDateTime() != null ? tldTenantDTO.getCreatedDateTime()
                        .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null)
                .tenantUUID(tldTenantDTO.getUuid());

        if (mobile!=null && !mobile.isEmpty())
            builder.mobile(mobile);
        if (email!=null && !email.isEmpty())
            builder.email(email);
        return builder.build();
    }

    public TLDTenantDTO fromTenantDTO(TenantDTO tenantDTO) {
        TLDTenantDTO tldTenantDTO = new TLDTenantDTO();
        if (tenantDTO.getId() == null){
            tldTenantDTO.setId(0L);
        } else {
            tldTenantDTO.setId(tenantDTO.getId());
        }
        tldTenantDTO.setName(tenantDTO.getTenantName());
        tldTenantDTO.setLoginName(tenantDTO.getTenantLogin());
        tldTenantDTO.setDescription(tenantDTO.getDescription());
        tldTenantDTO.setNotes(tenantDTO.getWebsite());
        tldTenantDTO.setTenantStatus(globalRefDataDTO("GREF.TENANT_STATUS", "TENANT_STATUS.ACTIVE"));
        tldTenantDTO.setTenantTypes(Collections.singletonList(tenantType()));
        tldTenantDTO.setUuid(tenantDTO.getUuid());
        return tldTenantDTO;
    }

    public static TenantTypeDTO tenantType() {
        TenantTypeDTO tenantTypeDTO = new TenantTypeDTO();
        tenantTypeDTO.setTenantType(globalRefDataDTO("GREF.TENANT_TYPE",
                "TENANT_TYPE.SELLER"));
        return tenantTypeDTO;
    }

    public TenantDTO fromLeadDTO(LeadDTO leadDTO) {
        TenantDTO.TenantDTOBuilder builder = TenantDTO.builder()
                .id(null)
                .tenantLogin(TenantOnBoardUtil.tenantLogin(leadDTO.getOrganizationName()))
                .tenantName(leadDTO.getOrganizationName())
                .onBoardedDateTime(LocalDateTime.now())
                .tenantUUID(UUID.randomUUID().toString());
        builder.mobile(leadDTO.getMobile());
        builder.email(leadDTO.getEmail());
        builder.useMobileAsLogin(leadDTO.getUseMobileAsUserLogin());
        return builder.build();
    }
    public static GlobalRefDataDTO globalRefDataDTO(String classCode, String dataCode) {
        GlobalRefClassDTO globalRefClassDTO = new GlobalRefClassDTO();
        globalRefClassDTO.setClassCode(classCode);
        GlobalRefDataDTO globalRefDataDTO = new GlobalRefDataDTO();
        globalRefDataDTO.setGlobalRefClass(globalRefClassDTO);
        globalRefDataDTO.setDataCode(dataCode);
        LanguageDTO languageDTO = new LanguageDTO();
        languageDTO.setLanguage("English");
        languageDTO.setLanguageCode("ENG");
        globalRefDataDTO.setLanguage(languageDTO);
        return globalRefDataDTO;
    }
}
