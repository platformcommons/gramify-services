package com.platformcommons.platform.service.iam.dto;

import com.platformcommons.platform.service.dto.base.BaseTransactionalDTO;
import com.platformcommons.platform.service.dto.commons.RefDataDTO;
import com.platformcommons.platform.service.sdk.person.dto.AddressDTO;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class TenantAddressDTO extends BaseTransactionalDTO {

    private Long id;

    private RefDataDTO addressType;

    private AddressDTO addressDTO;

    @Builder
    public TenantAddressDTO(String uuid, Long appCreatedAt, Long appLastModifiedAt, Boolean isActive,String inactiveReason,
                            Long id, RefDataDTO addressType, AddressDTO addressDTO) {
        super(uuid, appCreatedAt, appLastModifiedAt, isActive, inactiveReason);
        this.id = id;
        this.addressType = addressType;
        this.addressDTO = addressDTO;
    }

}
