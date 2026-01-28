package com.platformcommons.platform.service.post.facade.assembler;

import com.platformcommons.platform.service.post.domain.PostTenantConfig;
import com.platformcommons.platform.service.post.dto.PostTenantConfigDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostTenantConfigDTOAssembler {

    PostTenantConfigDTO toDTO(PostTenantConfig entity);

    PostTenantConfig fromDTO(PostTenantConfigDTO dto);
}
