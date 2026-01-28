package com.platformcommons.platform.service.domain.application;

import com.platformcommons.platform.service.dto.commons.AttachmentDTO;

import java.util.List;

public interface AttachmentServiceNonMT {

    List<AttachmentDTO> getAttachmentsByEntityIdAndEntityType(Long entityId, String entityType);


}