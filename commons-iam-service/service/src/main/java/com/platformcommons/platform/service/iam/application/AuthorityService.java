package com.platformcommons.platform.service.iam.application;

import com.platformcommons.platform.service.iam.domain.Authority;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface AuthorityService {
    List<Authority> addAuthorities(Set<Authority> set);

    Page<Authority> getALlAuthorities(String process, Integer page, Integer size);
}
