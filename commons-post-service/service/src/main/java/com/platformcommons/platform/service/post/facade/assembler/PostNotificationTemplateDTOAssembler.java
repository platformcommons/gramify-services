package com.platformcommons.platform.service.post.facade.assembler;

import com.platformcommons.platform.service.post.domain.PostNotificationTemplate;
import com.platformcommons.platform.service.post.dto.PostNotificationTemplateDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
})
public interface PostNotificationTemplateDTOAssembler {

    PostNotificationTemplateDTO toDTO(PostNotificationTemplate entity);

    PostNotificationTemplate fromDTO(PostNotificationTemplateDTO dto);
}
