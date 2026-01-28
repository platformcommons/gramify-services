package com.platformcommons.platform.service.iam.dto;

import com.platformcommons.platform.service.dto.base.BaseTransactionalDTO;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class TenantPointOfContactDTO extends BaseTransactionalDTO {

    private Long id;

    private String name;

    private String email;

    private String designation;

    private String contactNumber;

    private String contactNumberCountryCode;

    @Builder
    public TenantPointOfContactDTO(String uuid, Long appCreatedAt, Long appLastModifiedAt, Boolean isActive,String inactiveReason,
                                   Long id, String name, String email, String designation,
                                   String contactNumber, String contactNumberCountryCode) {
        super(uuid, appCreatedAt, appLastModifiedAt, isActive, inactiveReason);
        this.id = id;
        this.name = name;
        this.email = email;
        this.designation = designation;
        this.contactNumber = contactNumber;
        this.contactNumberCountryCode = contactNumberCountryCode;
    }
}
