package com.platformcommons.platform.service.domain.facade.assembler;

import com.platformcommons.platform.service.domain.domain.SearchKeyword;
import com.platformcommons.platform.service.domain.dto.SearchKeywordDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SearchKeywordDTOAssembler {

    SearchKeyword fromDTO(SearchKeywordDTO searchKeywordDTO);

    SearchKeywordDTO toDTO(SearchKeyword searchKeyword);
}
