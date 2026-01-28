package com.platformcommons.platform.service.assessment.facade.assembler.impl;

import com.platformcommons.platform.service.assessment.dto.MimicLanguageDTO;
import com.platformcommons.platform.service.assessment.dto.MimicMLTextDTO;
import com.platformcommons.platform.service.assessment.dto.MimicRefDataDTO;
import com.platformcommons.platform.service.assessment.facade.assembler.MimicLinkedRefCodeDTOAssembler;
import com.platformcommons.platform.service.assessment.facade.assembler.MimicMLTextDTOAssembler;
import com.platformcommons.platform.service.assessment.facade.assembler.MimicRefDataDTOAssembler;
import com.platformcommons.platform.service.dto.commons.MLTextDTO;
import com.platformcommons.platform.service.dto.commons.RefDataDTO;
import com.platformcommons.platform.service.entity.common.MLText;
import com.platformcommons.platform.service.entity.common.RefData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MimicRefDataDTOAssemblerImpl implements MimicRefDataDTOAssembler {

    private final MimicMLTextDTOAssembler mlTextDTOAssembler;
    private final MimicLinkedRefCodeDTOAssembler mimicLinkedRefCodeDTOAssembler;
    @Override
    public MimicRefDataDTO toDTO(RefData refData) {
        if(refData==null) return null;
        return MimicRefDataDTO.builder()
                .labels(toMimicMLTextDTO(refData.getLabels(),refData.getName()))
                .description(refData.getDescription())
                .code(refData.getCode())
                .refClass(mimicLinkedRefCodeDTOAssembler.toDTO(refData.getCode()))
                .build();
    }

    @Override
    public RefData fromDTO(MimicRefDataDTO refDataDTO) {
        if(refDataDTO==null) return null;
        return RefData.builder()
                .code(refDataDTO.getCode())
                .description(refDataDTO.getDescription())
                .labels(toMLText(refDataDTO.getLabels()))
                .classCode(mimicLinkedRefCodeDTOAssembler.fromDTO(refDataDTO.getRefClass()))
                .build();
    }

    @Override
    public RefDataDTO toRefDataDTO(MimicRefDataDTO refDataDTO) {
        if(refDataDTO==null) return null;
        return RefDataDTO.builder()
                .code(refDataDTO.getCode())
                .description(refDataDTO.getDescription())
                .labels(toMLTextDTO(refDataDTO.getLabels()))
                .classCode(mimicLinkedRefCodeDTOAssembler.fromDTO(refDataDTO.getRefClass()))
                .build();
    }

    @Override
    public MimicRefDataDTO toMimicRefDataDTO(RefDataDTO dto) {
        if(dto==null) return null;
        return MimicRefDataDTO.builder()
                .code(dto.getCode())
                .description(dto.getDescription())
                .labels(toMimicMLTextDTOFromMLTextDTO(dto.getLabels(),dto.getName()))
                .refClass(mimicLinkedRefCodeDTOAssembler.toDTO(dto.getClassCode()))
                .build();
    }

    private Set<MimicMLTextDTO> toMimicMLTextDTOFromMLTextDTO(Set<MLTextDTO> labels, String name) {
        if(labels==null || labels.isEmpty()) {
            return Collections.singleton(MimicMLTextDTO.builder().language(MimicLanguageDTO.builder().code("ENG").build()).text(name).build());
        }
        return labels.stream().map(mlTextDTOAssembler::toMimicMLTextDTO).collect(Collectors.toSet());
    }

    private Set<MLTextDTO> toMLTextDTO(Set<MimicMLTextDTO> labels) {
        if(labels==null || labels.isEmpty()) return new HashSet<>();
        return labels.stream().map(mlTextDTOAssembler::toMLTextDTO).collect(Collectors.toSet());
    }

    private Set<MLText> toMLText(Set<MimicMLTextDTO> labels) {
        if(labels==null || labels.isEmpty()) return new HashSet<>();
        return labels.stream().map(mlTextDTOAssembler::fromDTO).collect(Collectors.toSet());
    }
    private Set<MimicMLTextDTO> toMimicMLTextDTO(Set<MLText> labels, String text) {
        if(labels==null || labels.isEmpty()) {
            return Collections.singleton(MimicMLTextDTO.builder().language(MimicLanguageDTO.builder().code("ENG").build()).text(text).build());
        }
        return labels.stream().map(mlTextDTOAssembler::toDTO).collect(Collectors.toSet());
    }

}
