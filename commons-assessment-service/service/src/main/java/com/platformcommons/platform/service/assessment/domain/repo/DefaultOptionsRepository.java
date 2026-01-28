package com.platformcommons.platform.service.assessment.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.assessment.domain.DefaultOptions;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface DefaultOptionsRepository extends NonMultiTenantBaseRepository<DefaultOptions, Long> {

    @Query(value = "select d from DefaultOptions d " +
            "LEFT JOIN FETCH d.options " +
            "LEFT JOIN FETCH d.childQuestionList  " +
            "WHERE d.id in (:ids)")
    Set<DefaultOptions> getByIds(Set<Long> ids);

}