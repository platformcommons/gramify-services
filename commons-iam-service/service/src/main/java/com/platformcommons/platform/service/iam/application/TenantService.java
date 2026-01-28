package com.platformcommons.platform.service.iam.application;

import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.iam.domain.Tenant;
import com.platformcommons.platform.service.iam.dto.TenantDTO;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TenantService {

    Tenant addTenant(Tenant tenant,String leadKey);

    Tenant getTenantByLogin(String tenantLogin);

    List<Tenant> getTenantByEmail(String email);

    Tenant getTenantById(Long id);

    Tenant updateTenant(Tenant tenant, boolean isPatch);

    Boolean existsDomainForTenant(String domain);

    void addDomainForTenant(String domain);

    List<Tenant> getTenantByMobile(String mobile);

    void addAttachmentToTenant(Long tenantId, Attachment attachment);

    Tenant getTenantBySlug(String slug);

    Tenant getTenantByLoginForPublic(String tenantName);

    Boolean existsByTenantId(Long tenantId);

    boolean existsByTenantLogin(String tenantLogin);

    void updateTenantLogo(String completeURL);

    Optional<Tenant> getTenantByIdOptional(Long tenantId);

    List<Tenant> getTenantByIds(Set<Long> tenantIds);

    Boolean checkForExistingSlug(Long tenantId, String slug);

    Tenant addTenantCoverImage(Long tenantId, String completeURL);

    Tenant addTenantLogo(Long tenantId, String completeURL);
}
