package com.platformcommons.platform.service.iam.facade.assembler;

import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;
import com.platformcommons.platform.service.iam.domain.SocialMediaAccount;
import com.platformcommons.platform.service.iam.dto.SocialMediaAccountDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {
        RefDataDTOValidator.class
})
public interface SocialMediaAccountDTOAssembler {

    SocialMediaAccount fromDTO(SocialMediaAccountDTO socialMediaAccountDTO);

    SocialMediaAccountDTO toDTO(SocialMediaAccount socialMediaAccount);
}
