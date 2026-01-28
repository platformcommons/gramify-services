package com.platformcommons.platform.service.iam.dto;

import com.platformcommons.platform.service.dto.base.BaseTransactionalDTO;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class TenantSocialMediaDTO extends BaseTransactionalDTO {

        private Long id;

        private SocialMediaAccountDTO socialMediaAccount;

        @Builder
        public TenantSocialMediaDTO(String uuid, Long appCreatedAt, Long appLastModifiedAt, Boolean isActive,String inactiveReason,
                                    Long id, SocialMediaAccountDTO socialMediaAccount) {
            super(uuid, appCreatedAt, appLastModifiedAt, isActive, inactiveReason);
            this.id = id;
            this.socialMediaAccount = socialMediaAccount;
        }
}
