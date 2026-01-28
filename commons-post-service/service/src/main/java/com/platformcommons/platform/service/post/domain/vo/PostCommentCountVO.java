package com.platformcommons.platform.service.post.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostCommentCountVO {

    private Long postId;

    private Long commentCount;

    @Builder
    public PostCommentCountVO(Long postId, Long commentCount){
        this.postId = postId;
        this.commentCount = commentCount;
    }
}
