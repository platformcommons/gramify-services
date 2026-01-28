package com.platformcommons.platform.service.post.domain.vo;

import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.dto.base.BaseDTO;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PostAttachmentResourceVO extends BaseDTO {

    private String title;

    private String ownerName;

    private String topic;

    private Date publishedDate;

    private Attachment attachment;

    private AttachmentDTO attachmentDTO;

    @Builder
    public PostAttachmentResourceVO(String title, String ownerName, String topic, Date publishedDate,
                                    Attachment attachment) {
        this.title = title;
        this.ownerName = ownerName;
        this.topic = topic;
        this.publishedDate = publishedDate;
        this.attachment = attachment;
    }
}
