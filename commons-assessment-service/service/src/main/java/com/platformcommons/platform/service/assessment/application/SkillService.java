package com.platformcommons.platform.service.assessment.application;

import com.platformcommons.platform.service.assessment.domain.Skill;

import org.springframework.data.domain.Page;

public interface SkillService {

    Long createSkill(Skill skill);

    Page<Skill> getAllSkills(Integer page, Integer size);

    Skill getSkillById(Long id);

    void updateSkill(Skill skill);

    void deleteSkill(Long id);

    Page<Skill> getAllParentSkills(Integer page, Integer size);

    Page<Skill> getAllChildSkills(Integer page, Integer size, String parentSkillCode);


}
