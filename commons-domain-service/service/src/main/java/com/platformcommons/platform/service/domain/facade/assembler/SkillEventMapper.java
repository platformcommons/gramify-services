package com.platformcommons.platform.service.domain.facade.assembler;

import com.platformcommons.platform.service.domain.domain.Skill;
import com.platformcommons.platform.service.domain.dto.DomainEventDTO;
import com.platformcommons.platform.service.domain.dto.SkillEventDTO;
import com.platformcommons.platform.service.entity.common.MLText;
import org.springframework.stereotype.Component;

@Component
public class SkillEventMapper {
    public SkillEventDTO from(Skill skill) {
        MLText name=skill.getName().stream().findFirst().orElse(null);
        return SkillEventDTO.builder()
                .id(skill.getId())
                .name(skill.getSkillName()==null?(name==null?null:name.getText()):skill.getSkillName())
                .context(skill.getContext())
                .code(skill.getSkillCode())
                .isRoot(skill.getIsRoot())
                .tenantId(skill.getTenantId())
                .build();
    }
}
