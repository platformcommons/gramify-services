package com.platformcommons.platform.service.iam.facade.assembler;

import com.platformcommons.platform.service.iam.domain.TenantSocialMedia;
import com.platformcommons.platform.service.iam.dto.TenantSocialMediaDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        SocialMediaAccountDTOAssembler.class
})
public interface TenantSocialMediaDTOAssembler {

    TenantSocialMedia fromDTO(TenantSocialMediaDTO tenantSocialMediaDTO);

    TenantSocialMediaDTO toDTO(TenantSocialMedia tenantSocialMedia);

    List<TenantSocialMedia> fromDTOs(List<TenantSocialMediaDTO> tenantSocialMediaDTOs);

    List<TenantSocialMediaDTO> toDTOs(List<TenantSocialMedia> tenantSocialMedias);

}
