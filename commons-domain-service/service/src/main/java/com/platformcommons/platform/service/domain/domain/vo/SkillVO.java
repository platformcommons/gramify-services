package com.platformcommons.platform.service.domain.domain.vo;

import com.platformcommons.platform.service.dto.base.BaseTransactionalDTO;
import com.platformcommons.platform.service.dto.commons.MLTextDTO;
import java.io.Serializable;
import java.util.Set;
import javax.validation.Valid;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter @Setter
public class SkillVO extends BaseTransactionalDTO implements Serializable {
    private Long id;
    private String skillCode;
    private String domainCode;
    private String skillName;
    private String skillDesc;
    private String skillType;
    private Boolean isRoot;
    private String context;
    private @Valid Set<SkillVO> subSkills;
    private @Valid Set<MLTextDTO> name;
    private @Valid Set<MLTextDTO> description;

    @Builder
    public SkillVO(String uuid, Long appCreatedAt, Long appLastModifiedAt, Long id, String skillCode, String domainCode, String skillName, String skillDesc, String skillType, Boolean isRoot, String context, Set<SkillVO> subSkills, Set<MLTextDTO> name, Set<MLTextDTO> description) {
        super(uuid, appCreatedAt, appLastModifiedAt);
        this.id = id;
        this.skillCode = skillCode;
        this.domainCode = domainCode;
        this.skillName = skillName;
        this.skillDesc = skillDesc;
        this.skillType = skillType;
        this.isRoot = isRoot;
        this.context = context;
        this.subSkills = subSkills;
        this.name = name;
        this.description = description;
    }

}
