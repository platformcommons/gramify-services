package com.platformcommons.platform.service.post.domain.vo;

import com.platformcommons.platform.service.dto.base.BaseDTO;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.post.domain.PostActor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostAttachmentVO extends BaseDTO {

    private Long id;

    private Date postTime;

    private PostActor postedBy;

    private List<AttachmentDTO> attachmentDTOS;


    public PostAttachmentVO(Long id, Date postTime, PostActor postedBy) {
        this.id = id;
        this.postTime = postTime;
        this.postedBy = postedBy;
    }

    @Builder
    public PostAttachmentVO(Long id, Date postTime,List<AttachmentDTO> attachmentDTOS) {
        this.id = id;
        this.postTime = postTime;
        this.attachmentDTOS = attachmentDTOS;
    }

    @Builder
    public PostAttachmentVO(Long id, Date postTime) {
        this.id = id;
        this.postTime = postTime;
    }
}
