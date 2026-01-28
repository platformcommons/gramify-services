package com.platformcommons.platform.service.iam.dto;

import com.platformcommons.platform.service.dto.base.BaseTransactionalDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class TenantMetaConfigDTO extends BaseTransactionalDTO implements Serializable {

    private Long id;
    @NotNull(message = "tenantLogin must not be null")
    private String tenantLogin;
    private String userLogin;
    private String password;
    private String actorContext;
    private Long tenantId;

    @Valid
    private Set<TenantMetaAdditionalPropertyDTO> tenantMetaAdditionalPropertySet;

    @Builder
    public TenantMetaConfigDTO(String uuid, Long appCreatedAt, Long appLastModifiedAt, Long id,Long tenantId,
                               String tenantLogin, String userLogin, String password, String actorContext,
                               Set<TenantMetaAdditionalPropertyDTO> tenantMetaAdditionalPropertySet , Set<IAMUserDTO> adminUsers, Map<String, String> config) {
        super(uuid, appCreatedAt, appLastModifiedAt);
        this.id = id;
        this.tenantLogin = tenantLogin;
        this.password = password;
        this.userLogin = userLogin;
        this.actorContext = actorContext;
        this.tenantId = tenantId;
        this.tenantMetaAdditionalPropertySet = tenantMetaAdditionalPropertySet;
    }
}