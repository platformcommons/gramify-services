package com.platformcommons.platform.service.assessment.application.impl;

import com.platformcommons.platform.service.assessment.domain.Skill;


import com.platformcommons.platform.service.assessment.domain.repo.SkillHierarchyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;



import com.platformcommons.platform.service.assessment.application.SkillService;
import com.platformcommons.platform.service.assessment.domain.repo.SkillRepository;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.util.*;


@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {



    private final SkillRepository repository;
    private final SkillHierarchyRepository hierarchyRepository;

    @Override
    public Long createSkill(Skill skill) {
        return repository.save(skill).getId();
    }

    @Override
    public Page<Skill> getAllSkills(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Skill getSkillById(Long id) {
        Optional<Skill> skill = repository.findById(id);
        if (!skill.isPresent())
            throw new NotFoundException("ERR_SKILL_NOT_FOUND");
        return skill.get();
    }

    @Override
    public void updateSkill(Skill skill) {
        boolean skillExists = repository.existsById(skill.getId());
        if (!skillExists)
            throw new NotFoundException("ERR_SKILL_NOT_FOUND");
        repository.save(skill);
    }

    @Override
    public void deleteSkill(Long id) {
        boolean skillExists = repository.existsById(id);
        if (!skillExists)
            throw new NotFoundException("ERR_SKILL_NOT_FOUND");
        repository.deleteById(id);
    }

    @Override
    public Page<Skill> getAllParentSkills(Integer page, Integer size) {
        return repository.findParentSkill(PageRequest.of(page, size));
    }

    @Override
    public Page<Skill> getAllChildSkills(Integer page, Integer size, String parentSkillCode) {
        return hierarchyRepository.getAllChildSkills(PageRequest.of(page, size), parentSkillCode);
    }


}
