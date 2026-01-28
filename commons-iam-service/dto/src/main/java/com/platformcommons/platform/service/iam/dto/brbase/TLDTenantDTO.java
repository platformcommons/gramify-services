package com.platformcommons.platform.service.iam.dto.brbase;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TLDTenantDTO {
    private Long id;
    private String uuid;
    private String loginName;
    private String name;
    private String description;
    public String aliasId;
    public String notes;
    public String iconpic;
    public Date createdDateTime;
    private GlobalRefDataDTO tenantStatus;
    private List<TenantTypeDTO> tenantTypes;


}
