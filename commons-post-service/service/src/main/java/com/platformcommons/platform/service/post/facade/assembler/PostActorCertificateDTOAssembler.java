package com.platformcommons.platform.service.post.facade.assembler;

import com.platformcommons.platform.service.post.domain.PostActorCertificate;
import com.platformcommons.platform.service.post.dto.PostActorCertificateDTO;
import org.mapstruct.Mapper;

import java.util.Set;

public interface PostActorCertificateDTOAssembler {

    PostActorCertificateDTO toDTO(PostActorCertificate entity);

    Set<PostActorCertificateDTO> toDTOs(Set<PostActorCertificate> entities);

    PostActorCertificate fromDTO(PostActorCertificateDTO dto);

    Set<PostActorCertificate> fromDTOs(Set<PostActorCertificateDTO> dtos);
}
