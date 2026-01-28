package com.platformcommons.platform.service.search.application.service;

import com.platformcommons.platform.service.search.domain.Domain;

public interface DomainService {

    Domain createDomain(Domain domain);

    Domain getDomainById(Long id);

    Domain updateDomain(Domain domain);
}
