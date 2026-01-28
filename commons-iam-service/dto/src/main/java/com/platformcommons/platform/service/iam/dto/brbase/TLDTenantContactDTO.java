package com.platformcommons.platform.service.iam.dto.brbase;

import lombok.Data;

@Data
public class TLDTenantContactDTO {
    private Integer id;
    private Boolean primaryContact;
    private ContactDTO contact;
}
