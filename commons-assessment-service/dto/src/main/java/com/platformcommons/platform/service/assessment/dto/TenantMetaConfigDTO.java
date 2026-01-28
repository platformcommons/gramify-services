package com.platformcommons.platform.service.assessment.dto;

import com.platformcommons.platform.service.dto.base.BaseTransactionalDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class TenantMetaConfigDTO extends BaseTransactionalDTO implements Serializable {

    private Long id;
    private String tenantName;
    private String tenantLogin;
    private Set<UserDTO> adminUsers;
    private Map<String,String> config;

    @Builder
    public TenantMetaConfigDTO(String uuid, Long appCreatedAt, Long appLastModifiedAt, Long id, String tenantName, String tenantLogin, Set<UserDTO> adminUsers, Map<String, String> config) {
        super(uuid, appCreatedAt, appLastModifiedAt);
        this.id = id;
        this.tenantName = tenantName;
        this.tenantLogin = tenantLogin;
        this.adminUsers = adminUsers;
        this.config = config;
    }

}
