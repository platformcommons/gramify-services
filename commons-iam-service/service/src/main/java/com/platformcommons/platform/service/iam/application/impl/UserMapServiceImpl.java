package com.platformcommons.platform.service.iam.application.impl;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.iam.application.UserMapService;
import com.platformcommons.platform.service.iam.domain.TenantPartner;
import com.platformcommons.platform.service.iam.domain.User;
import com.platformcommons.platform.service.iam.domain.UserMap;
import com.platformcommons.platform.service.iam.domain.repo.UserMapRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserMapServiceImpl implements UserMapService {

    private final UserMapRepository userMapRepository;


    @Override
    public Long addUserMap(Long tenantPartnerId, Long targetUserId, Long sourceUserId, String status) {
        UserMap userMap = buildUserMap(tenantPartnerId, targetUserId, sourceUserId, status);
        return userMapRepository.save(userMap).getId();
    }

    @Override
    public Set<UserMap> getTenantPartnersForLoggedInContext() {
        Long currentUserId = PlatformSecurityUtil.getCurrentUserId();
        Long currentTenantId = PlatformSecurityUtil.getCurrentTenantId();
        return userMapRepository.getTenantPartnersForLoggedInContext(currentTenantId,
                currentUserId);

    }

    @Override
    public boolean existsForSourceAndTarget(Long parentTenantId, Long sourceUserId, Long tenantId, Long targetUserId) {
        return userMapRepository.existsForSourceAndTarget(parentTenantId,
                sourceUserId,tenantId,targetUserId);
    }

    private UserMap buildUserMap(Long tenantPartnerId, Long targetUserId, Long sourceUserId, String status) {
        return UserMap.builder()
                .tenantPartner(TenantPartner.builder()
                        .id(tenantPartnerId)
                        .build())
                .targetUser(User.builder().id(targetUserId).build())
                .sourceUser(User.builder().id(sourceUserId).build())
                .status(status)
                .build();
    }
}
