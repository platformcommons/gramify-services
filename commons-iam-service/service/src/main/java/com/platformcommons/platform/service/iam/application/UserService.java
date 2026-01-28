package com.platformcommons.platform.service.iam.application;

import com.platformcommons.platform.service.iam.domain.User;
import com.platformcommons.platform.service.iam.dto.UserExistResponse;

import java.util.Optional;

public interface UserService {
    User addUser(User user, String password);

    User findByUserAndTenantLogin(String userLogin, String tenantLogin);
    User findByUserIdAndTenantLogin(Long userId, String tenantLogin);

    User getUserReferenceById(Long id);

    User findByUserContactAndTenantLogin(String tenantLogin, String contact);

    Boolean existById(Long userId);

    boolean existByLogin(String login);

    User getUserById(Long userId);

    User getUserByLogin(String userLogin);

    User getUserReferenceByLogin(String userLogin);

    Optional<User> getUserReferenceByLoginAndTenantLogin(String login, String tenantLogin);


    UserExistResponse existUser(String email, String mobile, String tenantLogin, String login);

    User getUserReferenceByLoginAndTenantId(String userLogin, Long tenantId);

    User patch(User user);
}
