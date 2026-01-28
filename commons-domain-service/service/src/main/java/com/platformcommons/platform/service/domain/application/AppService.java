package com.platformcommons.platform.service.domain.application;

import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.domain.domain.App;

import com.platformcommons.platform.service.domain.domain.vo.ApplicationExcelVO;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

public interface AppService {

    App save(App app );

    App update(App app );

    Page<App> getByPage(Integer page, Integer size);

    void deleteById(Long id,String reason);

    App getById(Long id);

    App getBySlug(String slug);

    Attachment createAttachment(Long appId, MultipartFile file) throws IOException;

    List<AttachmentDTO> getAttachments(Long appId);

    void existsById(Long appId);


}
