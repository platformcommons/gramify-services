package com.platformcommons.platform.service.domain.facade;

import com.platformcommons.platform.service.domain.dto.AppDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.sdk.bulkupload.interfaces.dto.BulkUploadRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

public interface AppFacade {

    Long save(AppDTO appDTO );

    AppDTO update(AppDTO appDTO );

    PageDTO<AppDTO> getAllPage(Integer page, Integer size);

    void delete(Long id,String reason);

    AppDTO getById(Long id);

    AppDTO getBySlug(String slug);
    
    AttachmentDTO createAttachment(Long appId, MultipartFile file) throws IOException;

    List<AttachmentDTO> getAttachments(Long appId);

    BulkUploadRequestDTO uploadApplications(MultipartFile file) throws Exception;

    AppDTO getByIdOrSlug(String id);
}
