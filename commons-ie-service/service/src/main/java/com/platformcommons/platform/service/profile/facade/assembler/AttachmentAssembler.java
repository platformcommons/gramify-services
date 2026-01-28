package com.platformcommons.platform.service.profile.facade.assembler;

import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;

public interface AttachmentAssembler {
    AttachmentDTO toDto(Attachment attachment);
}
