package com.platformcommons.platform.service.iam.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
public class TenantVO {
    private Long id;
    private String login;
    private String name;
    private String domainURL;

    @Builder
    public TenantVO(Long id, String login, String name,String domainURL) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.domainURL = domainURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TenantVO tenantVO = (TenantVO) o;
        return Objects.equals(id, tenantVO.id) && Objects.equals(login, tenantVO.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login);
    }
}
