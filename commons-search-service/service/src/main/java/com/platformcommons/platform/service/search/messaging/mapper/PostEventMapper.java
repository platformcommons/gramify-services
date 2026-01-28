package com.platformcommons.platform.service.search.messaging.mapper;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.post.dto.PostDTO;
import com.platformcommons.platform.service.search.domain.Post;
import org.springframework.stereotype.Component;

@Component
public class PostEventMapper {
    public Post fromEventDTO(PostDTO postDTO) {
        assert postDTO != null;

        Post.PostBuilder postBuilder = Post.builder()
                .id(postDTO.getId())
                .title(postDTO.getTitle())
                .payload(postDTO.getPayload())
                .postTime(postDTO.getPostTime())
                .postType(postDTO.getPostType())
                .postSubType(postDTO.getPostSubType())
                .appContext(postDTO.getAppContext())
                .content(postDTO.getContent())
                .status(postDTO.getStatus())
                .isPublic(postDTO.getIsPublic())
                .tenantLogin(PlatformSecurityUtil.getCurrentTenantLogin())
                .tenantId(PlatformSecurityUtil.getCurrentTenantId())
                .isActive(postDTO.getIsActive() == null ? Boolean.TRUE : postDTO.getIsActive());

        if (postDTO.getPostedBy() != null) {
            postBuilder.postedByActorType(postDTO.getPostedBy().getActorType())
                    .postedByActorId(postDTO.getPostedBy().getActorId())
                    .postedByIconPic(postDTO.getPostedBy().getIconpic())
                    .postedByName(postDTO.getPostedBy().getName());
        }

        return postBuilder.build();
    }
}
