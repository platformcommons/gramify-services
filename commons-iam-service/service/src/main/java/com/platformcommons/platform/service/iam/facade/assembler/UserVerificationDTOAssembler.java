package com.platformcommons.platform.service.iam.facade.assembler;

import com.platformcommons.platform.service.iam.domain.UserVerification;
import com.platformcommons.platform.service.iam.dto.UserVerificationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserVerificationDTOAssembler {

    UserVerificationDTO toDTO(UserVerification userVerification);

    UserVerification fromDTO(UserVerificationDTO userVerificationDTO);
}
