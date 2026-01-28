package com.platformcommons.platform.service.profile.application;

import com.platformcommons.platform.service.profile.domain.CostSpecification;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface CostSpecificationService {
    CostSpecification save(CostSpecification costSpecification);

    void deleteById(Long id, String reason);

    CostSpecification getById(Long id);

    Page<CostSpecification> getByPage(Integer page, Integer size);

    CostSpecification update(CostSpecification costSpecification);

    Optional<CostSpecification> findByUuid(String uuid);

    CostSpecification patch(CostSpecification costSpecification);
}
