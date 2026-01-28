package com.platformcommons.platform.service.domain.facade.assembler.impl;

import com.platformcommons.platform.service.domain.domain.Skill;
import com.platformcommons.platform.service.domain.dto.SkillDTO;
import com.platformcommons.platform.service.domain.dto.TagDTO;
import com.platformcommons.platform.service.domain.facade.assembler.SkillDTOAssembler;
import com.platformcommons.platform.service.domain.facade.assembler.TagDTOAssembler;
import com.platformcommons.platform.service.dto.commons.MLTextDTO;
import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SkillDtoAssemblerImpl implements SkillDTOAssembler {

    @Autowired
    private MLTextDTOAssembler mlTextDTOAssembler;
    @Autowired
    private TagDTOAssembler tagDTOAssembler;
    @Override
    public Skill fromDTO(SkillDTO dto) {


        return Skill.builder()
                .id(dto.getId()!=null?dto.getId():0L)
                .skillCode(getSkillCode(dto))
                .domainCode(dto.getDomainCode())
                .skillName(dto.getSkillName())
                .skillDesc(dto.getSkillDesc())
                .skillType(dto.getSkillType())
                .isRoot(dto.getIsRoot())
                .context(dto.getContext())
                .name(dto.getName()==null?new HashSet<>():dto.getName().stream().map(mlTextDTOAssembler::fromDTO).collect(Collectors.toSet()))
                .description(dto.getDescription()==null?new HashSet<>():dto.getDescription().stream().map(mlTextDTOAssembler::fromDTO).collect(Collectors.toSet()))
                .tags(dto.getTags()==null?new HashSet<>():dto.getTags().stream().map(tagDTOAssembler::fromDTO).collect(Collectors.toSet()))
                .build();
    }

    private String getSkillCode(SkillDTO dto) {
        String skillCode = dto.getSkillCode();
        if(skillCode==null){
            skillCode = dto.getSkillName();
            if(skillCode==null) {
                skillCode = (dto.getName() == null || dto.getName().isEmpty()) ?
                        null : dto.getName().stream().filter(el -> (el.getLanguageCode().equalsIgnoreCase("ENG") && el.getText() != null)).findFirst().orElseThrow(() -> new IllegalStateException("Skill code or skill name is required"))
                        .getText();
            }
            if(skillCode==null)
                throw new IllegalStateException("Skill code or skill name is required");
            skillCode = dto.getDomainCode()+"."+createCode(skillCode);
        }

        return skillCode;
    }

    private String createCode(String skillCode) {
        skillCode=skillCode.replaceAll(" ", "_");
        StringBuilder sb = new StringBuilder();
        sb.append(Character.toUpperCase(skillCode.charAt(0)));
        for (int i = 1; i < skillCode.length(); i++) {
            sb.append(Character.isUpperCase(skillCode.charAt(i)) ? "_" + Character.toUpperCase(skillCode.charAt(i)) : Character.toUpperCase(skillCode.charAt(i)) );
        }
        return sb.toString();
    }

    @Override
    public SkillDTO toDTO(Skill entity) {
        return SkillDTO.builder()
                .id(entity.getId())
                .skillCode(entity.getSkillCode())
                .domainCode(entity.getDomainCode())
                .skillName(entity.getSkillName())
                .skillDesc(entity.getSkillDesc())
                .isRoot(entity.getIsRoot())
                .context(entity.getContext())
                .skillType(entity.getSkillType())
                .name(entity.getName()==null?new HashSet<>():entity.getName().stream().map(mlTextDTOAssembler::toDTO).collect(Collectors.toSet()))
                .description(entity.getDescription()==null?new HashSet<>():entity.getDescription().stream().map(mlTextDTOAssembler::toDTO).collect(Collectors.toSet()))
                .tags(entity.getTags()==null?new HashSet<>():entity.getTags().stream().map(tagDTOAssembler::toDTO).collect(Collectors.toSet()))
                .build();
    }
}
