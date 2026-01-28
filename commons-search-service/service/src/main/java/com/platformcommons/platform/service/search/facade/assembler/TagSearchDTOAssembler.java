package com.platformcommons.platform.service.search.facade.assembler;

import com.platformcommons.platform.service.search.domain.TagSearch;
import com.platformcommons.platform.service.search.dto.TagSearchDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagSearchDTOAssembler {

    TagSearchDTO toDTO(TagSearch tagSearch);

    TagSearch fromDTO(TagSearchDTO tagSearchDTO);
}
