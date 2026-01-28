package com.platformcommons.platform.service.assessment.facade.assembler.impl;

import com.platformcommons.platform.service.assessment.domain.AiaDefaultResponse;
import com.platformcommons.platform.service.assessment.dto.AiaDefaultResponseDTO;
import com.platformcommons.platform.service.assessment.facade.assembler.*;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AiaDefaultResponseDTOAssemblerImpl implements AiaDefaultResponseDTOAssembler {

    private final DrSubjectiveResponseDTOAssembler drSubjectiveResponseDTOAssembler;
    private final DrObjectiveResponseDTOAssembler drObjectiveResponseDTOAssembler;
    private final MimicMLTextDTOAssembler mlTextDTOAssembler;
    private final OptionsDTOAssembler optionsDTOAssembler;
    private final SectionQuestionsDTOAssembler sectionQuestionsDTOAssembler;

    @Override
    @Mapping(source = "createdTimestamp", target = "createdAt")
    @Mapping(source = "createdByUser", target = "createdBy")
    @Mapping(source = "tenantId", target = "tenantId")
    @Mapping(source = "drSubjectiveResponseList", target = "drsubjectiveresponseList")
    public AiaDefaultResponseDTO toDTO(AiaDefaultResponse entity) {
        return AiaDefaultResponseDTO.builder()
                .id(entity.getId())
                .uuid(entity.getUuid())
                .tenantId(entity.getTenantId())
                .createdAt(entity.getCreatedTimestamp())
                .createdBy(entity.getCreatedByUser())
                .drsubjectiveresponseList(entity.getDrSubjectiveResponseList() == null ? null : entity.getDrSubjectiveResponseList()
                        .stream()
                        .filter(drSubjectiveResponse -> !Objects.equals(Boolean.FALSE, drSubjectiveResponse.getIsActive()))
                        .map(drSubjectiveResponseDTOAssembler::toDTO)
                        .collect(Collectors.toSet()))
                .drobjectiveresponseList(entity.getDrobjectiveresponseList() == null ? null : entity.getDrobjectiveresponseList()
                        .stream()
                        .filter(drObjectiveResponse -> !Objects.equals(Boolean.FALSE, drObjectiveResponse.getIsActive()))
                        .map(drObjectiveResponseDTOAssembler::toDTO)
                        .collect(Collectors.toSet()))
                .isActive(entity.getIsActive())
//                .aiaDesc(entity.getAiaDesc() == null ? null : entity.getAiaDesc().stream().map(mlTextDTOAssembler::toDTO).collect(Collectors.toSet()))
                .childQuestionId(entity.getChildQuestionId())
                .childQuestionParentQuestionId(entity.getChildQuestionParentQuestionId())
                .childDefaultOptionId(entity.getChildDefaultOptionId())
                .location(entity.getLocation())
                .optionId(optionsDTOAssembler.toDTO(entity.getOptionId()))
                .remarks(entity.getRemarks())
                .sectionQuestion(sectionQuestionsDTOAssembler.toDTO(entity.getSectionQuestion()))
                .tenant(entity.getTenant())
                .appCreatedAt(entity.getAppCreatedTimestamp())
                .appLastModifiedAt(entity.getAppLastModifiedTimestamp())
                .build();
    }

    @Override
    public AiaDefaultResponse fromDTO(AiaDefaultResponseDTO dto) {
        return AiaDefaultResponse.builder()
                .id(dto.getId())
                .uuid(dto.getUuid())
                .tenantId(dto.getTenantId())
                .drSubjectiveResponseList(dto.getDrsubjectiveresponseList() == null ? null : dto.getDrsubjectiveresponseList().stream().map(drSubjectiveResponseDTOAssembler::fromDTO).collect(Collectors.toSet()))
                .drobjectiveresponseList(dto.getDrobjectiveresponseList() == null ? null : dto.getDrobjectiveresponseList().stream().map(drObjectiveResponseDTOAssembler::fromDTO).collect(Collectors.toSet()))
                .isActive(dto.getIsActive())
//                .aiaDesc(dto.getAiaDesc() == null ? null : dto.getAiaDesc().stream().map(mlTextDTOAssembler::fromDTO).collect(Collectors.toSet()))
                .childQuestionId(dto.getChildQuestionId())
                .childQuestionParentQuestionId(dto.getChildQuestionParentQuestionId())
                .childDefaultOptionId(dto.getChildDefaultOptionId())
                .location(dto.getLocation())
                .optionId(optionsDTOAssembler.fromDTO(dto.getOptionId()))
                .remarks(dto.getRemarks())
                .sectionQuestion(sectionQuestionsDTOAssembler.fromDTO(dto.getSectionQuestion()))
                .tenant(dto.getTenant())
                .appCreatedTimestamp(dto.getAppCreatedAt())
                .appLastModifiedTimestamp(dto.getAppLastModifiedAt())
                .build();
    }
}
