package com.platformcommons.platform.service.post.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platformcommons.platform.service.domain.dto.TagDTO;
import com.platformcommons.platform.service.dto.base.BaseTransactionalDTO;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
@Validated
public class ProposalTagDTO extends BaseTransactionalDTO implements Serializable {
    private Long id;
    @NotNull
    private String type;
    @NotNull
    private String tagCode;

    private String tagPurpose;

    private TagDTO tag;


    @Builder
    public ProposalTagDTO(String uuid, Long appCreatedAt, Long appLastModifiedAt,
                          Long id, String type, String tagCode, String tagPurpose, TagDTO tag) {
        super(uuid, appCreatedAt, appLastModifiedAt);
        this.id = id;
        this.type = type;
        this.tagCode = tagCode;
        this.tagPurpose = tagPurpose;
        this.tag = tag;
    }
}