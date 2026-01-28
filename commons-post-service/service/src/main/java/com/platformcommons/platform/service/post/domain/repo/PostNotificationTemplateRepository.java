package com.platformcommons.platform.service.post.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.post.domain.PostNotificationTemplate;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostNotificationTemplateRepository extends BaseRepository<PostNotificationTemplate,Long> {

    @Query(" SELECT p FROM #{#entityName} p ")
    Optional<PostNotificationTemplate> findForLoggedInTenant();
}
