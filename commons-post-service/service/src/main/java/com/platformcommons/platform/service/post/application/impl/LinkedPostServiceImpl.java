package com.platformcommons.platform.service.post.application.impl;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.service.post.application.LinkedPostService;
import com.platformcommons.platform.service.post.application.PostService;
import com.platformcommons.platform.service.post.application.constant.PostConstant;
import com.platformcommons.platform.service.post.domain.LinkedPost;
import com.platformcommons.platform.service.post.domain.Post;
import com.platformcommons.platform.service.post.domain.repo.LinkedPostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LinkedPostServiceImpl implements LinkedPostService {

    @Autowired
    LinkedPostRepository linkedPostRepository;

    @Autowired
    PostService postService;

    @Override
    public LinkedPost createLinkPost(LinkedPost linkedPost) {
        if (linkedPostRepository.getLinkedPostBySourceAndTarget(linkedPost.getSourcePost().getId(), linkedPost.getTargetPost().getId(), linkedPost.getLinkingType()) != null) {
            throw new InvalidInputException(String.format("LinkedPost already exits with SourcePostId - %d and TargetPostId - %d with LinkingType - %s",
                    linkedPost.getSourcePost().getId(), linkedPost.getTargetPost().getId(), linkedPost.getLinkingType()));
        }
        Post sourcePost = postService.getById(linkedPost.getSourcePost().getId());
        Post targetPost = postService.getById(linkedPost.getTargetPost().getId());
        linkedPost.setId(null);
        linkedPost.setSourcePost(sourcePost);
        linkedPost.setTargetPost(targetPost);
        if (linkedPost.getLinkingType() == null) linkedPost.setLinkingType(PostConstant.LINKING_TYPE_RELATED);
        return linkedPostRepository.save(linkedPost);
    }

    @Override
    public void deleteLinkedPostById(Long linkedPostId, String reason) {
        LinkedPost linkedPost = getLinkedPostById(linkedPostId);
        linkedPost.deactivate(reason);
        linkedPostRepository.save(linkedPost);
    }

    @Override
    public Page<LinkedPost> getAllLinkedPost(Integer page, Integer size, Sort orders, String linkingType, Long postId) {
        return linkedPostRepository.getAllLinkedPost(linkingType, postId, PageRequest.of(page, size, orders));
    }

    @Override
    public LinkedPost getLinkedPostById(Long linkedPostId) {
        return linkedPostRepository.getLinkedPostById(linkedPostId).orElseThrow(() -> new InvalidInputException(String.format("LinkedPost doesn't exist by id = %d", linkedPostId)));
    }

    @Override
    public LinkedPost patchLinkedPost(LinkedPost linkedPost) {
        LinkedPost existingLinkedPost = getLinkedPostById(linkedPost.getId());
        if (linkedPostRepository.getLinkedPostBySourceAndTarget(linkedPost.getSourcePost().getId(), linkedPost.getTargetPost().getId(), linkedPost.getLinkingType()) != null) {
            throw new InvalidInputException(String.format("LinkedPost already exits with SourcePostId - %d and TargetPostId - %d with LinkingType - %s",
                    linkedPost.getSourcePost().getId(), linkedPost.getTargetPost().getId(), linkedPost.getLinkingType()));
        }
        Post sourcePost = postService.getById(linkedPost.getSourcePost().getId());
        Post targetPost = postService.getById(linkedPost.getTargetPost().getId());
        linkedPost.setSourcePost(sourcePost);
        linkedPost.setTargetPost(targetPost);
        existingLinkedPost.patch(linkedPost);
        return linkedPostRepository.save(existingLinkedPost);
    }

    @Override
    public LinkedPost updateLinkedPost(LinkedPost linkedPost) {
        LinkedPost existingLinkedPost = getLinkedPostById(linkedPost.getId());
        if (linkedPostRepository.getLinkedPostBySourceAndTarget(linkedPost.getSourcePost().getId(), linkedPost.getTargetPost().getId(), linkedPost.getLinkingType()) != null) {
            throw new InvalidInputException(String.format("LinkedPost already exits with SourcePostId - %d and TargetPostId - %d with LinkingType - %s",
                    linkedPost.getSourcePost().getId(), linkedPost.getTargetPost().getId(), linkedPost.getLinkingType()));
        }
        Post sourcePost = postService.getById(linkedPost.getSourcePost().getId());
        Post targetPost = postService.getById(linkedPost.getTargetPost().getId());
        linkedPost.setSourcePost(sourcePost);
        linkedPost.setTargetPost(targetPost);
        existingLinkedPost.update(linkedPost);
        return linkedPostRepository.save(existingLinkedPost);
    }
}