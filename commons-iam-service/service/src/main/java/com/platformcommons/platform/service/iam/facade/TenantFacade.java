package com.platformcommons.platform.service.iam.facade;

import com.platformcommons.platform.security.token.PlatformToken;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.iam.dto.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface TenantFacade {

    Long addTenant(TenantDTO tenantDTO, String adminPass, String appContext,String marketContext, boolean addInLinkedSystem, String referralCode);

    TenantDTO getTenantByLogin(String login);

    TenantDTO getTenantById(Long tenantId);

    void updateTenant(TenantDTO tenantDTO, boolean isPatch);

    Optional<TenantDTO> getTenantByIdOptional(Long tenantId);

    TenantDTO getCurrentTenant();

    void addTenantLogo(MultipartFile file);

    void addTenantCoverImage(MultipartFile file);

    List<TenantVO> existsTenantWithEmail(String email);

    List<TenantVO> existsTenantWithMobile(String mobile);

    Boolean existsDomainForTenant(String domain);

    void addDomainForTenant(String domain);

    void addTenantSolution(SolutionSubscriptionDTO solutionSubscriptionDTO);

    @Transactional(readOnly = true)
    Map<String,Object> getTenantAppAssociationStatus(String appContext);

    void addTenantAndOperationsPostCreation(TenantDTO tenantDTO, String appContext, String marketContext, String leadKey,
                                            String datasetGroup);

    void createTenantVMS(@Valid SignUpRequestDTO signUpRequestDTO, String adminPassword);


    void addTenantSolutionV2(SolutionSubscriptionDTO solutionSubscriptionDTO);

    TenantLoginResourcesResponse getMarketConfigsAndLeadPresentByParams(String appContext, String email, String mobile,
                                                                   String marketUUID, Map<String, String> httpHeaders);

    AttachmentDTO addAttachmentToTenant(MultipartFile file, String attachmentKind, String entityType, String attachmentKindMeta,
                                        String attachmentKindIdentifier, Boolean isPublic, String name);

    TenantDTO getTenantBySlug(String slug);

    void deleteAttachmentFromTenant(Long attachmentId, String reason);

    Boolean checkForExistingSlug(Long tenantId, String slug);

    Long convertToTenant(TenantDTO tenantDTO, String context, String contextValue, PlatformToken platformTokenActual, Boolean isPrimary);

    Set<TenantPartnerVO> getTenantPartners();

    boolean existsTenantWithLogin(String tenantLogin);

    TenantDTO getTenantByTenantIdAndLoadCache(Long tenantId);

    TenantDTO getByTenantIdFromCacheOrDB(Long tenantId);

    void deleteTenantCacheByTenantId(Long tenantId);

    void deleteAllTenantCache();
}
