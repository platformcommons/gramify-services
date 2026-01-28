package com.platformcommons.platform.service.assessment.reporting.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "assessse_hierarchy_resolver")
@Getter
@Setter
@NoArgsConstructor
public class AssessseHierarchyResolver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long hierarchyLevel;

    private String datasetCode;

    private String defaultParams;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "configuration_id", nullable = false)
    private AssesseHierarchyResolutionConfiguration configuration;

    public void init(AssesseHierarchyResolutionConfiguration assesseHierarchyResolutionConfiguration) {
        this.id = null;
        this.configuration = assesseHierarchyResolutionConfiguration;
    }

    public void patch(AssessseHierarchyResolver hierarchyResolver) {
        Optional.ofNullable(hierarchyResolver.getHierarchyLevel()).ifPresent(this::setHierarchyLevel);
        Optional.ofNullable(hierarchyResolver.getDatasetCode()).ifPresent(this::setDatasetCode);
        Optional.ofNullable(hierarchyResolver.getDefaultParams()).ifPresent(this::setDefaultParams);
    }
}
