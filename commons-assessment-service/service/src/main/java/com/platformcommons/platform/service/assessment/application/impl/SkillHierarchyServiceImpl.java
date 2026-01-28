package com.platformcommons.platform.service.assessment.application.impl;

import com.platformcommons.platform.service.assessment.domain.SkillHierarchy;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;



import com.platformcommons.platform.service.assessment.application.SkillHierarchyService;
import com.platformcommons.platform.service.assessment.domain.repo.SkillHierarchyRepository;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.util.*;


@Service
@RequiredArgsConstructor
public class SkillHierarchyServiceImpl implements SkillHierarchyService {

    private final SkillHierarchyRepository repository;

    @Override
    public Long createLTLDSkillHierarchy(SkillHierarchy lTLDSkillHierarchy) {
        return repository.save(lTLDSkillHierarchy).getId();
    }

    @Override
    public Page<SkillHierarchy> getAllLTLDSkillHierarchies(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    @Override
    public SkillHierarchy getLTLDSkillHierarchyById(Long id) {
        Optional<SkillHierarchy> lTLDSkillHierarchy = repository.findById(id);
        if (!lTLDSkillHierarchy.isPresent())
            throw new NotFoundException("ERR_LTLD_SKILL_HRY_NOT_FOUND");
        return lTLDSkillHierarchy.get();
    }

    @Override
    public void updateLTLDSkillHierarchy(SkillHierarchy lTLDSkillHierarchy) {
        boolean lTLDSkillHierarchyExists = repository.existsById(lTLDSkillHierarchy.getId());
        if (!lTLDSkillHierarchyExists)
            throw new NotFoundException("ERR_LTLD_SKILL_HRY_NOT_FOUND");
        repository.save(lTLDSkillHierarchy);
    }

    @Override
    public void deleteLTLDSkillHierarchy(Long id) {
        boolean lTLDSkillHierarchyExists = repository.existsById(id);
        if (!lTLDSkillHierarchyExists)
            throw new NotFoundException("ERR_LTLD_SKILL_HRY_NOT_FOUND");
        repository.deleteById(id);
    }

}
