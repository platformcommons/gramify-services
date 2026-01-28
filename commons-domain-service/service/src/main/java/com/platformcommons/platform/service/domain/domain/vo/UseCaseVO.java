package com.platformcommons.platform.service.domain.domain.vo;

import com.platformcommons.platform.service.dto.base.BaseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UseCaseVO extends BaseDTO {
    private Long id;

    private String name;

    private Long authorId;

    private String authorName;

    private String shortDescription;

    @Builder
    public UseCaseVO(Long id, String name, Long authorId, String authorName, String shortDescription) {
        this.id = id;
        this.name = name;
        this.authorId = authorId;
        this.authorName = authorName;
        this.shortDescription = shortDescription;
    }
}
