package com.platformcommons.platform.service.iam.dto.brbase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TLDRoleDTO {
    @ApiModelProperty(required = true)
    private String code;

    private String roleName;

    private String description;

    private String roleType;
}
