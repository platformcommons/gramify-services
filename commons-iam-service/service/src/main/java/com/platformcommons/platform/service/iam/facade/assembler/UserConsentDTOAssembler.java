package com.platformcommons.platform.service.iam.facade.assembler;

import com.platformcommons.platform.service.iam.domain.UserConsent;
import com.platformcommons.platform.service.iam.dto.UserConsentDTO;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserConsentDTOAssembler {

    UserConsent fromDTO(UserConsentDTO dto);

    UserConsentDTO toDTO(UserConsent entity);

    Set<UserConsentDTO> toDTOs(Set<UserConsent> entities);
 }
