package com.platformcommons.platform.service.iam.application.utility;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.iam.dto.CompanyDTO;
import com.platformcommons.platform.service.iam.dto.CompanyMasterDataV2DTO;
import com.platformcommons.platform.service.iam.facade.client.ClearBitClient;
import com.platformcommons.platform.service.iam.facade.client.CommonsSearchClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CompanyMasterDataUtil {

    @Autowired
    private CommonsSearchClient commonsSearchClient;

    @Autowired
    private ClearBitClient clearBitClient;


    public CompanyDTO getCompanyDTOByCompanyName(String companyName) {
        CompanyDTO companyDTO = null;
        Set<CompanyMasterDataV2DTO> companyMasterDataV2DTOSet = new LinkedHashSet<>(Objects.requireNonNull(commonsSearchClient.getCompanyBySearchText(companyName,
                10, 0, PlatformSecurityUtil.getToken()).getBody()).getElements());
        if (!companyMasterDataV2DTOSet.isEmpty()) {
            CompanyMasterDataV2DTO companyMasterDataV2DTO = companyMasterDataV2DTOSet.stream()
                    .findFirst()
                    .orElse(null);
            companyDTO = CompanyDTO.builder()
                    .code(companyMasterDataV2DTO.getCode())
                    .name(companyMasterDataV2DTO.getName())
                    .domain(companyMasterDataV2DTO.getWebsiteUrl())
                    .logo(companyMasterDataV2DTO.getLogoUrl())
                    .build();
            return companyDTO;
        }
        List<CompanyDTO> companyDTOSet = clearBitClient.getCompanyBySearchText(companyName, 10, 0).getBody();
        if (companyDTOSet != null && !companyDTOSet.isEmpty()) {
            companyDTO = companyDTOSet.stream()
                    .findFirst()
                    .orElse(null);
            return createCompanyInMasterData(companyDTO.getName(), companyDTO.getLogo(), companyDTO.getDomain());
        }
        return createCompanyInMasterData(companyName, null, null);
    }

    public CompanyDTO createCompanyInMasterData(String name, String logo, String domain) {
        CompanyDTO companyDTO = null;
        if (name != null) {
            CompanyMasterDataV2DTO companyMasterDataV2DTO = CompanyMasterDataV2DTO.builder()
                    .name(name)
                    .logoUrl(logo)
                    .websiteUrl(domain)
                    .build();
            companyMasterDataV2DTO = commonsSearchClient.saveCompany(companyMasterDataV2DTO,
                    PlatformSecurityUtil.getToken()).getBody();
            if (companyMasterDataV2DTO != null) {
                return CompanyDTO.builder()
                        .code(companyMasterDataV2DTO.getCode())
                        .name(companyMasterDataV2DTO.getName())
                        .domain(companyMasterDataV2DTO.getWebsiteUrl())
                        .logo(companyMasterDataV2DTO.getLogoUrl())
                        .build();
            }
        }
        return companyDTO;
    }
}
