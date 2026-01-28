package com.platformcommons.platform.service.assessment.facade.assembler;

import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;

import java.util.List;


public interface AttachmentDTOAssembler {

    AttachmentDTO toDTO(Attachment attachment);

    Attachment fromDTO(AttachmentDTO dto);

    List<AttachmentDTO> toDTOs(List<Attachment> attachments);

    List<Attachment> fromDTOs(List<AttachmentDTO> dtos);

}