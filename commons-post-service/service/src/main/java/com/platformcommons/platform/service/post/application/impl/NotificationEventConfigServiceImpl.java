package com.platformcommons.platform.service.post.application.impl;

import com.platformcommons.platform.service.post.application.NotificationEventConfigService;
import com.platformcommons.platform.service.post.domain.NotificationEventConfig;
import com.platformcommons.platform.service.post.domain.Post;
import com.platformcommons.platform.service.post.domain.repo.NotificationEventConfigNonMTRepository;
import com.platformcommons.platform.service.post.domain.repo.NotificationEventConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationEventConfigServiceImpl implements NotificationEventConfigService {

    @Autowired
    private NotificationEventConfigRepository repository;

    @Autowired
    private NotificationEventConfigNonMTRepository nonMTRepository;

    @Override
    public Optional<NotificationEventConfig> getByPostParametersForLoggedInTenant(Post post) {
        return repository.findByPostParameters(post.getPostType(),post.getPostSubType(),post.getStatus());
    }

    @Override
    public Optional<NotificationEventConfig> getByPostParametersByDefault(Post post) {
        return nonMTRepository.findByPostParametersAndIsDefault(post.getPostType(),post.getPostSubType(),post.getStatus());
    }
}
