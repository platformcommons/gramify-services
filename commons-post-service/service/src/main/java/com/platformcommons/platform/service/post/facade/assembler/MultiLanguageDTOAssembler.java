package com.platformcommons.platform.service.post.facade.assembler;

import com.platformcommons.platform.service.post.domain.MultiLanguage;
import com.platformcommons.platform.service.post.dto.MultiLanguageDTO;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface MultiLanguageDTOAssembler {

    MultiLanguageDTO toDTO(MultiLanguage entity);

    Set<MultiLanguageDTO> toDTOs(Set<MultiLanguage> entities);

    MultiLanguage fromDTO(MultiLanguageDTO dto);

    Set<MultiLanguage> fromDTOs(Set<MultiLanguageDTO> dtos);
}
