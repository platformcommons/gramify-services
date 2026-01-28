package com.platformcommons.platform.service.iam.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.iam.domain.UserMetaData;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface UserMetaDataRepository extends BaseRepository<UserMetaData,Long> {

    @Query(" SELECT u FROM #{#entityName} u WHERE u.userId = :userId AND u.metaKey IN (:metaKeys) ")
    Set<UserMetaData> findByMetaKeyAndUserId(Long userId,Set<String> metaKeys);
}
