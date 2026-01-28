package com.platformcommons.platform.service.post.facade.assembler;

import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.post.domain.Post;
import com.platformcommons.platform.service.post.dto.*;


import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;


public interface PostDTOAssembler {

    PostDTO toDTO(Post entity);

    Post fromDTO(PostDTO dto);

    AttachmentDTO toDTO(Attachment attachment);

    List<PostDTO> toDTOs(List<Post> entities);
}