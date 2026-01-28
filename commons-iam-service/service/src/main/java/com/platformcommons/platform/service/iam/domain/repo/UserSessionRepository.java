package com.platformcommons.platform.service.iam.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.iam.domain.UserSession;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserSessionRepository extends BaseRepository<UserSession, Long> {
    @Query("select u from UserSession u where u.session = ?1")
    Optional<UserSession> findBySession(String session);

}