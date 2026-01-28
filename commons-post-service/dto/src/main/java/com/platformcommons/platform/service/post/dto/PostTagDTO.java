package com.platformcommons.platform.service.post.dto;

import com.platformcommons.platform.service.domain.dto.TagDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platformcommons.platform.service.dto.base.BaseTransactionalDTO;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
@Validated
public class PostTagDTO extends BaseTransactionalDTO implements Serializable {
    private Long id;
    @NotNull
    private String type;
    @NotNull
    private String tagCode;
    private TagDTO tag;

    @Builder
    public PostTagDTO(String uuid, Long appCreatedAt, Long appLastModifiedAt,Long id, String type, String tagCode,TagDTO tag) {
        super(uuid, appCreatedAt, appLastModifiedAt);
        this.id = id;
        this.type = type;
        this.tagCode = tagCode;
        this.tag = tag;
    }

}