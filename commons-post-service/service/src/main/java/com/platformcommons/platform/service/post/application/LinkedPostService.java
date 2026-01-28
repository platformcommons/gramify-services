package com.platformcommons.platform.service.post.application;

import com.platformcommons.platform.service.post.domain.LinkedPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface LinkedPostService {
    LinkedPost createLinkPost(LinkedPost linkedPost);

    void deleteLinkedPostById(Long linkedPostId, String reason);

    Page<LinkedPost> getAllLinkedPost(Integer page, Integer size, Sort orders, String linkingType, Long postId);

    LinkedPost getLinkedPostById(Long linkedPostId);

    LinkedPost patchLinkedPost(LinkedPost linkedPost);

    LinkedPost updateLinkedPost(LinkedPost linkedPost);
}