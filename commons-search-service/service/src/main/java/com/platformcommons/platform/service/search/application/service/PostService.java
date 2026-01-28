package com.platformcommons.platform.service.search.application.service;

import com.platformcommons.platform.service.search.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface PostService {

    Page<Post> getPostTimeline(String searchTerm,String tenantLogin, String  appContext, String domainContext, String languageContext,
                               String locationContext, String postType, Set<String> postSubTypes, String status, Pageable pageable);

    void save(Post post);

    Post getById(Long id);

    void delete(Long postId);
}
