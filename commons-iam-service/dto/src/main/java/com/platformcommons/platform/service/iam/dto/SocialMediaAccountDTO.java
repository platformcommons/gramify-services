package com.platformcommons.platform.service.iam.dto;

import com.platformcommons.platform.service.dto.base.BaseTransactionalDTO;
import com.platformcommons.platform.service.dto.commons.RefDataDTO;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class SocialMediaAccountDTO extends BaseTransactionalDTO {

    private Long id;

    private RefDataDTO socialMediaType;

    private String socialMediaValue;
}
