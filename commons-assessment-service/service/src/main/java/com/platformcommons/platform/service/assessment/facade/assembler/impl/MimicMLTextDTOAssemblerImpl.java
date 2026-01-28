package com.platformcommons.platform.service.assessment.facade.assembler.impl;

import com.platformcommons.platform.service.assessment.dto.MimicMLTextDTO;
import com.platformcommons.platform.service.assessment.facade.assembler.MimicLanguageDTOAssembler;
import com.platformcommons.platform.service.assessment.facade.assembler.MimicMLTextDTOAssembler;
import com.platformcommons.platform.service.dto.commons.MLTextDTO;
import com.platformcommons.platform.service.entity.common.MLText;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MimicMLTextDTOAssemblerImpl implements MimicMLTextDTOAssembler {

    private final MimicLanguageDTOAssembler mimicLanguageDTOAssembler;
    @Override
    public MimicMLTextDTO toDTO(MLText mlText) {
        if(mlText==null) return null;
        return MimicMLTextDTO.builder()
                .id(mlText.getId())
                .text(mlText.getText())
                .language(mimicLanguageDTOAssembler.toDTO(mlText))
                .build();
    }
    @Override
    public MLText fromDTO(MimicMLTextDTO mimicMLTextDTO) {
        if(mimicMLTextDTO==null) return null;
        return MLText.builder()
                .id(getId(mimicMLTextDTO.getId()))
                .text(mimicMLTextDTO.getText())
                .languageCode(mimicLanguageDTOAssembler.fromDTO(mimicMLTextDTO.getLanguage()))
                .build();
    }

    private Long getId(Long id) {
        return id!=null && id.equals(0L)?null:id;
    }

    @Override
    public Set<MimicMLTextDTO> toDTOs(Set<MLText> mlTexts) {
        if(mlTexts==null) return null;
        return mlTexts.stream().map(this::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
    }
    @Override
    public MLTextDTO toMLTextDTO(MimicMLTextDTO mimicMLTextDTO) {
        if(mimicMLTextDTO==null) return null;
        return MLTextDTO.builder()
                .id(getId(mimicMLTextDTO.getId()))
                .text(mimicMLTextDTO.getText())
                .languageCode(mimicLanguageDTOAssembler.fromDTO(mimicMLTextDTO.getLanguage()))
                .build();
    }

    @Override
    public MimicMLTextDTO toMimicMLTextDTO(MLTextDTO mlTextDTO) {
        if(mlTextDTO==null) return null;
        return MimicMLTextDTO.builder()
                .id(mlTextDTO.getId())
                .text(mlTextDTO.getText())
                .language(mimicLanguageDTOAssembler.toMimicLanguageDTO(mlTextDTO))
                .build();
    }

    @Override
    public Set<MLText> fromDTOs(Set<MimicMLTextDTO> mimicMLTextDTOS) {
        if(mimicMLTextDTOS==null) return null;
        return mimicMLTextDTOS.stream().map(this::fromDTO).collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
