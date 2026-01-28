package com.platformcommons.platform.service.domain.application.impl;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.domain.application.TagHierarchyService;
import com.platformcommons.platform.service.domain.domain.TagHierarchy;
import com.platformcommons.platform.service.domain.domain.repo.TagHierarchyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

@Service
@RequiredArgsConstructor
public class TagHierarchyServiceImpl implements TagHierarchyService {

    private static final String ADD_TAG_HIERARCHY = "addTagHierarchy";
    private final EntityManager entityManager;
    private final TagHierarchyRepository tagHierarchyRepository;

    @Override
    public void createHierarchy(Long childTagId, Long parentTagId) {
        StoredProcedureQuery store =  entityManager.createStoredProcedureQuery(ADD_TAG_HIERARCHY);
        store.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);
        store.registerStoredProcedureParameter(2, Long.class, ParameterMode.IN);
        store.registerStoredProcedureParameter(3, Long.class, ParameterMode.IN);
        store.registerStoredProcedureParameter(4, Long.class, ParameterMode.IN);

        store.setParameter(1, parentTagId);
        store.setParameter(2, childTagId);
        store.setParameter(3, PlatformSecurityUtil.getCurrentTenantId());
        store.setParameter(4, PlatformSecurityUtil.getCurrentUserId());
        store.execute();
    }

    @Override
    public void deleteHierarchy(Long parentTagId, Long childTagId) {
        TagHierarchy tagHierarchy = tagHierarchyRepository.findByParentTagIdAndTagId(parentTagId, childTagId);
        if (tagHierarchy != null) {
            tagHierarchy.deactivate("deleted By Admin");
            tagHierarchyRepository.save(tagHierarchy);
        }
    }

}
