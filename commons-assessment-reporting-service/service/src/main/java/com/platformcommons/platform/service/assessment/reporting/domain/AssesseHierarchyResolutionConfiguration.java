package com.platformcommons.platform.service.assessment.reporting.domain;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Entity
@Table(name = "assesse_hierarchy_resolution_configuration")
@Getter
@Setter
@NoArgsConstructor
public class AssesseHierarchyResolutionConfiguration extends BaseTransactionalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String entityType;

    @NotNull(message = "maxHierarchyLevel must not be null")
    private Long maxHierarchyLevel;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "configuration")
    private List<AssessseHierarchyResolver> hierarchyResolvers;


    @Builder
    public AssesseHierarchyResolutionConfiguration(String uuid, Long tenantId, Boolean isActive, String inactiveReason, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, String entityType, Long maxHierarchyLevel, List<AssessseHierarchyResolver> hierarchyResolvers) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.entityType = entityType;
        this.maxHierarchyLevel = maxHierarchyLevel;
        this.hierarchyResolvers = hierarchyResolvers;
    }


    public void patch(AssesseHierarchyResolutionConfiguration updatedConfig) {
        Optional.ofNullable(updatedConfig.getEntityType()).ifPresent(this::setEntityType);
        patchHierarchyResolver(updatedConfig);
    }

    private void patchHierarchyResolver(AssesseHierarchyResolutionConfiguration updatedConfig) {
        if(CollectionUtils.isEmpty(updatedConfig.getHierarchyResolvers()))  return;
        Map<Long, AssessseHierarchyResolver> resolverMap = this.getHierarchyResolvers().stream().collect(Collectors.toMap(AssessseHierarchyResolver::getId, Function.identity()));

        for (AssessseHierarchyResolver hierarchyResolver : updatedConfig.getHierarchyResolvers()) {
            AssessseHierarchyResolver dbAssessseHierarchyResolver = resolverMap.get(hierarchyResolver.getId());
            if(dbAssessseHierarchyResolver==null) {
                addAssessseHierarchyResolver(hierarchyResolver);
            }
            else{
                dbAssessseHierarchyResolver.patch(hierarchyResolver);
            }
        }
    }

    private void addAssessseHierarchyResolver(AssessseHierarchyResolver hierarchyResolver) {
        hierarchyResolver.init(this);
        this.hierarchyResolvers.add(hierarchyResolver);
    }

    public void init() {
        this.id=null;
        if(CollectionUtils.isNotEmpty(this.hierarchyResolvers)) {
            this.hierarchyResolvers.forEach(resolver -> resolver.init(this));
        }
    }
}
