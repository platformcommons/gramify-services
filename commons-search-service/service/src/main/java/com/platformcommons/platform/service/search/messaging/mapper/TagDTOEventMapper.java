package com.platformcommons.platform.service.search.messaging.mapper;

import com.platformcommons.platform.service.domain.dto.TagDTO;
import com.platformcommons.platform.service.dto.commons.MLTextDTO;
import com.platformcommons.platform.service.search.domain.TagSearch;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TagDTOEventMapper {


    public TagSearch fromDTO(TagDTO tagDTO){
        return TagSearch.builder()
                .id(String.valueOf(tagDTO.getId()))
                .createdTimestamp(new Date().getTime())
                .lastModifiedTimestamp(new Date().getTime())
                .tagCode(tagDTO.getCode())
                .name(tagDTO.getName())
                .categoryCode(tagDTO.getCategoryCode())
                .subCategoryCode(tagDTO.getSubCategoryCode())
                .context(tagDTO.getContext())
                .type(tagDTO.getType())
                .labels(tagDTO.getNameML().stream()
                                .collect(Collectors.toMap(MLTextDTO::getLanguageCode, MLTextDTO::getText)))
                .build();
    }
}
