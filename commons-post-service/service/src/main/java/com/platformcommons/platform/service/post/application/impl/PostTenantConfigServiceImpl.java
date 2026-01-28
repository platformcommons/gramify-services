package com.platformcommons.platform.service.post.application.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.post.application.PostTenantConfigService;
import com.platformcommons.platform.service.post.domain.PostTenantConfig;
import com.platformcommons.platform.service.post.domain.repo.PostTenantConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostTenantConfigServiceImpl implements PostTenantConfigService {

    @Autowired
    private PostTenantConfigRepository repository;

    @Override
    public PostTenantConfig getForLoggedInTenant() {
        return repository.findByTenantLogin(PlatformSecurityUtil.getCurrentTenantLogin()).orElseThrow(
                ()->new NotFoundException("TenantPostConfig not found For tenantLogin "+PlatformSecurityUtil.getCurrentTenantLogin()));
    }

    @Override
    public PostTenantConfig getPostTenantConfigByTenantLoginV2(String tenantLogin) {
        return repository.findByTenantLogin(tenantLogin).orElseThrow(
                ()->new NotFoundException("TenantPostConfig not found For tenantLogin "+PlatformSecurityUtil.getCurrentTenantLogin()));
    }

    @Override
    public PostTenantConfig getPostTenantConfigByTenantLogin(String tenantLogin) {
        return repository.findByTenantLogin(tenantLogin).orElse(null);
    }

    @Override
    public PostTenantConfig save(PostTenantConfig postTenantConfig) {
        PostTenantConfig finalPostTenantConfig = null;
        Optional<PostTenantConfig> optionalTenantConfig = repository.findByTenantLogin(postTenantConfig.getTenantLogin());
        if(optionalTenantConfig.isPresent()) {
            finalPostTenantConfig = optionalTenantConfig.get();
            finalPostTenantConfig.patchUpdate(postTenantConfig);
        }
        else {
            finalPostTenantConfig = postTenantConfig;
            finalPostTenantConfig.init();
        }
        return repository.save(finalPostTenantConfig);
    }

    @Override
    public PostTenantConfig patchUpdate(PostTenantConfig postTenantConfig) {
        PostTenantConfig fetchedPostTenantConfig = getPostTenantConfigByTenantLogin(postTenantConfig.getTenantLogin());
        fetchedPostTenantConfig.patchUpdate(postTenantConfig);
        return fetchedPostTenantConfig;
    }

    @Override
    public void validateIsPublicFeedAllowed(String tenantLogin) {
        PostTenantConfig postTenantConfig = getPostTenantConfigByTenantLogin(tenantLogin);
        if (postTenantConfig != null && postTenantConfig.getIsPublicFeedAllowed() != null) {
            if (Boolean.FALSE.equals(postTenantConfig.getIsPublicFeedAllowed())) {
                throw new UnAuthorizedAccessException("Public Feed is not enabled for tenant " + tenantLogin);
            }
        }
    }


    @Override
    public PostTenantConfig validateIsResponseAndReactionPublic(String tenantLogin) {
        PostTenantConfig postTenantConfig = getPostTenantConfigByTenantLogin(tenantLogin);
        if (postTenantConfig != null && postTenantConfig.getIsResponseAndReactionPublic() != null) {
            if (Boolean.FALSE.equals(postTenantConfig.getIsResponseAndReactionPublic())) {
                throw new UnAuthorizedAccessException("Response and Reaction is not public for the tenant %s "+tenantLogin);
            }
        }
        return postTenantConfig;
    }

    @Override
    public void validateIsUserFeedPublic(String tenantLogin) {
        PostTenantConfig postTenantConfig = getPostTenantConfigByTenantLogin(tenantLogin);
        if (postTenantConfig != null && postTenantConfig.getIsUserFeedPublic() != null) {
            if (Boolean.FALSE.equals(postTenantConfig.getIsUserFeedPublic())) {
                throw new UnAuthorizedAccessException("user feed is not public for the tenant %s "+tenantLogin);
            }
        };
    }
}
