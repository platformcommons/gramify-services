package com.platformcommons.platform.service.domain.application.impl;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.domain.application.SkillHierarchyService;
import com.platformcommons.platform.service.domain.domain.SkillHierarchy;
import com.platformcommons.platform.service.domain.domain.repo.DomainHierarchyRepository;
import com.platformcommons.platform.service.domain.domain.repo.SkillHierarchyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.List;
import java.util.Set;

@Service
public class SkillHierarchyServiceImpl implements SkillHierarchyService {
    private static final String ADD_SKILL_HIERARCHY = "addSkillHierarchy";
    @Autowired
    private SkillHierarchyRepository repository;

    @Autowired
    private EntityManager entityManager;
    @Override
    public void createHierarchy(Long skillId, Long parentSkillId) {
        StoredProcedureQuery store =
                entityManager.createStoredProcedureQuery(ADD_SKILL_HIERARCHY);
        store.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);
        store.registerStoredProcedureParameter(2, Long.class, ParameterMode.IN);
        store.registerStoredProcedureParameter(3,Long.class,ParameterMode.IN);
        store.registerStoredProcedureParameter(4,Long.class,ParameterMode.IN);


        store.setParameter(1, parentSkillId);
        store.setParameter(2, skillId);
        store.setParameter(3, PlatformSecurityUtil.getCurrentTenantId());
        store.setParameter(4,PlatformSecurityUtil.getCurrentUserId());
        store.execute();
    }

    @Override
    public List<SkillHierarchy> getChildSkill(Set<Long> id) {
        return repository.findBySkills(id);
    }

}
