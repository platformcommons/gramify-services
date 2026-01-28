package com.platformcommons.platform.service.iam.dto;

import com.platformcommons.platform.service.sdk.person.dto.PersonDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@NoArgsConstructor
public class IAMUserDTO extends AuthBaseDTO {

    @Setter
    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private String status;
    private String email;
    private String mobile;
    private TenantVO tenant;
    private Set<IAMUserRoleMapDTO> roles;
    private PersonDTO person;

    @Builder
    public IAMUserDTO(Long id,
                      String login, String firstName, String lastName, String status,
                      String email, String mobile,
                       TenantVO tenant, Set<IAMUserRoleMapDTO> roles,
                      PersonDTO person) {
        super();
        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.email = email;
        this.mobile = mobile;
        this.tenant = tenant;
        this.roles = roles;
        this.person = person;
    }
}
