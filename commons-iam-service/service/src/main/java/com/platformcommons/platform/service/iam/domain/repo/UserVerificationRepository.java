package com.platformcommons.platform.service.iam.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.iam.domain.UserVerification;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface UserVerificationRepository extends BaseRepository<UserVerification, Long> {

    @Query("SELECT uv FROM #{#entityName} uv " +
            " WHERE uv.userId = :userId " +
            " AND (:appContext IS NULL OR uv.appContext = :appContext) " +
            " AND (:marketContext IS NULL OR uv.marketContext = :marketContext) " +
            " AND uv.isActive = 1 ")
    Optional<UserVerification> findByUserIdAndAppContext(Long userId,String appContext,String marketContext);

    @Query("select count(id)>0 from UserVerification where userId=?1 and verificationStatus='STATUS.VERIFIED'")
    Optional<Boolean> isVerifiedUser(Long id);

    @Query("select uv from UserVerification uv where uv.userId in ( ?1 )")
    Set<UserVerification> findByUserIds(Set<Long> userId);

    @Query("select count(id)>0 from UserVerification where userId=?1 ")
    boolean isUserVerificationStatusPresent(Long id);

    @Query("SELECT COUNT(id)>0 FROM #{#entityName} uv " +
            " WHERE uv.userId = :userId " +
            " AND uv.appContext = :appContext " +
            " AND uv.isActive = 1 ")
    Boolean existsByUserIdAndAppContext(Long userId, String appContext);
}