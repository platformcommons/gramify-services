package com.platformcommons.platform.service.iam.domain.repo;

import com.platformcommons.platform.service.iam.domain.User;
import com.platformcommons.platform.service.iam.domain.UserRoleMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Optional;

public interface UserRoleMapRepository extends JpaRepository<UserRoleMap, Long> {

    @Query("SELECT ur FROM UserRoleMap ur " +
            "WHERE ur.user.id = :userId")
    List<UserRoleMap> getUserRoleMapsByUser_Id(Long userId);

    Long user(User user);

    @Query("SELECT ur FROM UserRoleMap ur " +
            "WHERE ur.user.id = :userId " +
            "AND ur.role.code = :code")
    Optional<UserRoleMap> findByUserAndRole(Long userId, String code);

    @Query("SELECT new com.platformcommons.platform.service.iam.domain.UserRoleMap(ur.id) FROM UserRoleMap ur " +
            "WHERE ur.user.id = :userId " +
            "AND ur.role.code = :code")
    Optional<UserRoleMap> findUserRoleMapReference(Long userId, String code);
}