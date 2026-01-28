package com.platformcommons.platform.service.iam.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRoleAssignDTO {

    private Long userId;

    private String userLogin;

    @NotNull(message = "roleCode must not be null")
    private String roleCode;

    @Builder
    public UserRoleAssignDTO(Long userId, String userLogin,String roleCode) {
        this.userId = userId;
        this.roleCode = roleCode;
        this.userLogin = userLogin;
    }
}
