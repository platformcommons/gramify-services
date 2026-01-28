package com.platformcommons.platform.service.assessment.domain.repo;

import com.platformcommons.platform.service.assessment.domain.Options;
import com.platformcommons.platform.service.assessment.domain.vo.OptionVO;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface OptionsNonMTRepository extends NonMultiTenantBaseRepository<Options,Long> {

    @Query(" SELECT new com.platformcommons.platform.service.assessment.domain.vo.OptionVO(q.id,dop.options.id,mtf.id, dop.id) " +
            " FROM Question q " +
            " LEFT JOIN q.defaultOptionsList dop " +
            " LEFT JOIN q.mtfoptionList mtf " +
            " WHERE q.id in (:questionIds) AND " +
            "       dop.isActive=true AND " +
            "       (dop is not null OR mtf is not null) ")
    Set<OptionVO> getOptionsByQuestionIds(Set<Long> questionIds);
}
