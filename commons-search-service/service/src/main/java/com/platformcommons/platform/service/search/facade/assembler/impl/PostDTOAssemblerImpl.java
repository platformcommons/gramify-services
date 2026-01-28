package com.platformcommons.platform.service.search.facade.assembler.impl;

import com.platformcommons.platform.service.search.domain.Post;
import com.platformcommons.platform.service.search.dto.PostDTO;
import com.platformcommons.platform.service.search.facade.assembler.PostDTOAssembler;
import org.springframework.stereotype.Component;

@Component
public class PostDTOAssemblerImpl implements PostDTOAssembler {

    @Override
    public PostDTO toDTO(Post post) {
        if (post == null) {
            return null;
        }

        PostDTO.PostDTOBuilder postDTO = PostDTO.builder();

        postDTO.id(post.getId());
        postDTO.postedByActorType(post.getPostedByActorType());
        postDTO.postedByActorId(post.getPostedByActorId());
        postDTO.postedByIconPic(post.getPostedByIconPic());
        postDTO.postedByName(post.getPostedByName());
        postDTO.title(post.getTitle());
        postDTO.payload(post.getPayload());
        postDTO.postTime(post.getPostTime());
        postDTO.postType(post.getPostType());
        postDTO.postSubType(post.getPostSubType());
        postDTO.appContext(post.getAppContext());
        postDTO.content(post.getContent());
        postDTO.status(post.getStatus());
        postDTO.isPublic(post.getIsPublic());
        postDTO.tenantLogin(post.getTenantLogin());
        postDTO.tenantId(post.getTenantId());

        return postDTO.build();
    }

    @Override
    public PostDTO toCustomDTO(Post post) {
        if (post == null) {
            return null;
        }

        PostDTO.PostDTOBuilder postDTO = PostDTO.builder();

        postDTO.id(post.getId());
        postDTO.postedByActorType(post.getPostedByActorType());
        postDTO.postedByActorId(post.getPostedByActorId());
        postDTO.postedByIconPic(post.getPostedByIconPic());
        postDTO.postedByName(post.getPostedByName());
        postDTO.title(post.getTitle());
        postDTO.payload(post.getPayload());
        postDTO.postTime(post.getPostTime());
        postDTO.postType(post.getPostType());
        postDTO.postSubType(post.getPostSubType());
        postDTO.content(post.getContent());

        return postDTO.build();
    }

    @Override
    public Post fromDTO(PostDTO postDTO) {
        if (postDTO == null) {
            return null;
        }

        return Post.builder()
                .id(postDTO.getId())
                .postedByActorType(postDTO.getPostedByActorType())
                .postedByActorId(postDTO.getPostedByActorId())
                .postedByIconPic(postDTO.getPostedByIconPic())
                .postedByName(postDTO.getPostedByName())
                .title(postDTO.getTitle())
                .payload(postDTO.getPayload())
                .postTime(postDTO.getPostTime())
                .postType(postDTO.getPostType())
                .postSubType(postDTO.getPostSubType())
                .appContext(postDTO.getAppContext())
                .content(postDTO.getContent())
                .status(postDTO.getStatus())
                .isPublic(postDTO.getIsPublic())
                .tenantLogin(postDTO.getTenantLogin())
                .tenantId(postDTO.getTenantId())
                .build();
    }
}