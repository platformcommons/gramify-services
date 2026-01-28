package com.platformcommons.platform.service.iam.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentVO {

    private String attachmentKindMeta;

    private String attachmentKindIdentifier;

    private Long numberOfAttachments;
}
