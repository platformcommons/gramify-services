package com.platformcommons.platform.service.post.facade.assembler;

import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;

public interface AttachmentDTOAssembler {

    AttachmentDTO toDTO(Attachment attachment);

}
