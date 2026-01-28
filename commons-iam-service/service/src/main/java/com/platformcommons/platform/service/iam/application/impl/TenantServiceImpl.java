package com.platformcommons.platform.service.iam.application.impl;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.iam.application.LeadService;
import com.platformcommons.platform.service.iam.application.TenantService;
import com.platformcommons.platform.service.iam.application.utility.PlatformUtil;
import com.platformcommons.platform.service.iam.domain.Lead;
import com.platformcommons.platform.service.iam.domain.Tenant;
import com.platformcommons.platform.service.iam.domain.repo.TenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;
    private final LeadService leadService;

    public static boolean isSession() {
        try {
            return PlatformSecurityUtil.isSession();
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Tenant addTenant(Tenant tenant,String leadKey) {
        log.info("Add tenant: {}", tenant);
        if(leadKey != null) {
            Lead lead = leadService.getByKey(leadKey);
            tenant.setLead(lead);
        }
        tenant.init();
        return tenantRepository.save(tenant);
    }

    @Override
    public Tenant getTenantByLogin(String tenantLogin) {
        Tenant tenant;
        if (isSession() && (PlatformSecurityUtil.isPlatformAdmin() ||
                PlatformSecurityUtil.getCurrentTenantLogin().equals(tenantLogin)))
            tenant = tenantRepository.findByTenantLogin(tenantLogin);
        else
            tenant = tenantRepository.findByTenantLoginForPublic(tenantLogin);
        if (tenant == null) {
            throw new NotFoundException(String.format("Tenant with login name %s not found", tenantLogin));
        }
        return tenant;
    }

    @Override
    public List<Tenant> getTenantByEmail(String email) {
        return tenantRepository.findByTenantEmail(email);
    }

    @Override
    public List<Tenant> getTenantByMobile(String mobile) {
        return tenantRepository.findByTenantMobile(mobile);
    }

    @Override
    public void addAttachmentToTenant(Long tenantId, Attachment attachment) {
        PlatformUtil.validateLoginTenantAndAdminByTenantId(tenantId);
        Tenant tenant = this.getTenantById(tenantId);
        if (tenant.getTenantProfile() == null) {
            throw new InvalidInputException(String.format("Tenant with id %d does not have a Tenant profile", tenantId));
        }
        List<Attachment> existingAttachments = tenant.getTenantProfile().getAttachments();
        existingAttachments.add(attachment);
        tenant.getTenantProfile().setAttachments(existingAttachments);
        tenantRepository.save(tenant);
    }

    @Override
    public Tenant getTenantBySlug(String slug) {
        return tenantRepository.findByTenantSlug(slug)
                .orElseThrow(()->new NotFoundException(String.format("Tenant with slug %s not found", slug)));
    }

    @Override
    public Tenant getTenantByLoginForPublic(String tenantName) {
        return tenantRepository.findByTenantIdForPublic(tenantName)
                .orElseThrow(() -> new NotFoundException("Tenant not found"));
    }

    @Override
    public Boolean existsByTenantId(Long tenantId) {
        return tenantRepository.existsById(tenantId);
    }

    /**
     * Retrieves a tenant by their ID with appropriate access control.
     *
     * @param tenantId The ID of the tenant to retrieve
     * @return The tenant entity
     * @throws IllegalArgumentException if tenantId is null
     * @throws NotFoundException        if tenant is not found
     */
    @Override
    public Tenant getTenantById(Long tenantId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }
        return tenantRepository.findByTenantId(tenantId)
                .orElseThrow(()-> new NotFoundException(String.format("Tenant with id %d not found", tenantId)));
    }

    @Override
    public Optional<Tenant> getTenantByIdOptional(Long tenantId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }
        return tenantRepository.findByTenantId(tenantId);
    }


    @Override
    public List<Tenant> getTenantByIds(Set<Long> ids) {
        return tenantRepository.findAllById(ids);
    }

    @Override
    public Boolean checkForExistingSlug(Long tenantId, String slug) {
        return tenantRepository.existsByTenantSlug(slug, tenantId);
    }

    @Override
    public Tenant addTenantCoverImage(Long tenantId, String completeURL) {
        Tenant tenant = this.getTenantById(tenantId);
        tenant.getTenantProfile().setCoverImage(completeURL);
        return tenantRepository.save(tenant);
    }

    @Override
    public Tenant addTenantLogo(Long tenantId, String completeURL) {
        Tenant tenant = getTenantById(tenantId);
        tenant.setIconpic(completeURL);
        return tenantRepository.save(tenant);
    }

    @Override
    public Tenant updateTenant(Tenant tenant, boolean isPatch) {
        Tenant tenantFetched = authorizeForTenantUpdate(tenant);
        if (isPatch)
            tenantFetched.patchUpdate(tenant);
        else
            tenantFetched.putUpdate(tenant);
        return tenantRepository.save(tenantFetched);
    }

    @Override
    public Boolean existsDomainForTenant(String domain) {
        return !tenantRepository.existsByTenantDomain(domain);
    }

    @Override
    public void addDomainForTenant(String domain) {
        Tenant tenant = getTenantById(PlatformSecurityUtil.getCurrentTenantId());
        if (tenant.getTenantDomain() == null) {
            tenant.addDomain(domain);
            tenantRepository.save(tenant);
        } else {
            throw new InvalidInputException("Tenant already has a domain assigned");
        }
    }

    private Tenant authorizeForTenantUpdate(Tenant tenant) {
        Long tenantId = tenant.getId();
        if (PlatformUtil.isTenantAdmin(tenantId) || PlatformSecurityUtil.isPlatformAdmin()) {
            return getTenantById(tenantId);
        }
        throw new UnAuthorizedAccessException("Logged in user is not allowed to update detail");
    }

    @Override
    public boolean existsByTenantLogin(String tenantLogin) {
        return tenantRepository.existsByTenantLogin(tenantLogin);
    }

    @Override
    public void updateTenantLogo(String completeURL) {
        //TODO.. update into database
    }
}
