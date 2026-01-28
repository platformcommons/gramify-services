package com.platformcommons.platform.service.iam.application.impl;

import com.platformcommons.platform.service.iam.application.AuthorityService;
import com.platformcommons.platform.service.iam.domain.Authority;
import com.platformcommons.platform.service.iam.domain.repo.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;
    @Override
    public List<Authority> addAuthorities(Set<Authority> set) {
        return authorityRepository.saveAll(set);
    }

    @Override
    public Page<Authority> getALlAuthorities(String process, Integer page, Integer size) {
        return authorityRepository.getALlAuthorities(process, PageRequest.of(page,size));
    }
}
