package com.platformcommons.platform.service.domain.messaging.mapper;

import com.platformcommons.platform.service.domain.dto.ClassificationTagEventDTO;
import com.platformcommons.platform.service.domain.dto.GenericProductTagEventDTO;
import com.platformcommons.platform.service.domain.dto.GenericProductVarietyTagEventDTO;
import com.platformcommons.platform.service.domain.dto.TagDTO;
import com.platformcommons.platform.service.dto.commons.MLTextDTO;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TagEventMapper {

    public TagDTO buildTagFromGenericProduct(GenericProductTagEventDTO eventDTO) {
        String categoryCode = Optional.ofNullable(eventDTO.getCategories())
                .flatMap(categories -> categories.stream()
                        .map(ClassificationTagEventDTO::getClassificationCode)
                        .findFirst())
                .orElse(null);

        String subCategoryCode = Optional.ofNullable(eventDTO.getSubCategories())
                .flatMap(subCategories -> subCategories.stream()
                        .map(ClassificationTagEventDTO::getClassificationCode)
                        .findFirst())
                .orElse(null);

        String engName = Optional.ofNullable(eventDTO.getGenericProductName())
                .flatMap(names -> names.stream()
                        .filter(n -> "ENG".equalsIgnoreCase(n.getLanguageCode()))
                        .map(MLTextDTO::getText)
                        .findFirst())
                .orElse(null);

        Set<MLTextDTO> nameML = Optional.ofNullable(eventDTO.getGenericProductName())
                .map(names -> names.stream()
                        .map(n -> new MLTextDTO(null, n.getText(), n.getLanguageCode()))
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());

        return TagDTO.builder()
                .code(eventDTO.getTagIdentifier())
                .name(engName)
                .type("TAG_TYPE.GENERIC_PRODUCT")
                .nameML(nameML)
                .categoryCode(categoryCode)
                .subCategoryCode(subCategoryCode)
                .context(eventDTO.getMarketCode())
                .sequence(1L)
                .build();
    }

    public TagDTO buildTagFromGenericProductVariety(GenericProductVarietyTagEventDTO eventDTO) {
        String categoryCode = Optional.ofNullable(eventDTO.getCategories())
                .flatMap(categories -> categories.stream()
                        .map(ClassificationTagEventDTO::getClassificationCode)
                        .findFirst())
                .orElse(null);

        String subCategoryCode = Optional.ofNullable(eventDTO.getSubCategories())
                .flatMap(subCategories -> subCategories.stream()
                        .map(ClassificationTagEventDTO::getClassificationCode)
                        .findFirst())
                .orElse(null);

        String engName = Optional.ofNullable(eventDTO.getGenericProductVarietyName())
                .flatMap(names -> names.stream()
                        .filter(n -> "ENG".equalsIgnoreCase(n.getLanguageCode()))
                        .map(MLTextDTO::getText)
                        .findFirst())
                .orElse(null);

        Set<MLTextDTO> nameML = Optional.ofNullable(eventDTO.getGenericProductVarietyName())
                .map(names -> names.stream()
                        .map(n -> new MLTextDTO(null, n.getText(), n.getLanguageCode()))
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());

        return TagDTO.builder()
                .code(eventDTO.getTagIdentifier())
                .name(engName)
                .type("TAG_TYPE.GENERIC_PRODUCT_VARIETY")
                .nameML(nameML)
                .categoryCode(categoryCode)
                .subCategoryCode(subCategoryCode)
                .context(eventDTO.getMarketCode())
                .sequence(1L)
                .build();
    }



}
