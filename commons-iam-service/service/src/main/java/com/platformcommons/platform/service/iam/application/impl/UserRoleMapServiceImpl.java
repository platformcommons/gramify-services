package com.platformcommons.platform.service.iam.application.impl;

import com.platformcommons.platform.service.iam.application.UserRoleMapService;
import com.platformcommons.platform.service.iam.domain.UserRoleMap;
import com.platformcommons.platform.service.iam.domain.repo.UserRoleMapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRoleMapServiceImpl implements UserRoleMapService {

    private final UserRoleMapRepository userRoleMapRepository;

    @Override
    public List<UserRoleMap> getUserRoleMapByUserId(Long userId) {
        return userRoleMapRepository.getUserRoleMapsByUser_Id(userId);
    }

    @Override
    public UserRoleMap addUserRole(UserRoleMap userRoleMap) {
        Optional<UserRoleMap> userRoleMapReference = userRoleMapRepository.
                findUserRoleMapReference(userRoleMap.getUser().getId(),userRoleMap.getRole().getCode());
        return userRoleMapReference.orElseGet(() -> userRoleMapRepository.save(userRoleMap));
    }

}
