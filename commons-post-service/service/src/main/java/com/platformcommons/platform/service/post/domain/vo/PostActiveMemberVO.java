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
public class PostActiveMemberVO {

    private Long postId;

    private String name;

    private String iconpic;

    @Builder
    public PostActiveMemberVO(Long postId, String name, String iconpic){
        this.postId = postId;
        this.name = name;
        this.iconpic = iconpic;
    }
}
