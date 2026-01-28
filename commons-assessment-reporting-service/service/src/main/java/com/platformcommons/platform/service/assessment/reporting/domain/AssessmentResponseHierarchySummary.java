package com.platformcommons.platform.service.assessment.reporting.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AssessmentResponseHierarchySummary extends BaseReportEntity {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long assessmentId;
    
    private Long assessmentInstanceId;
    
    private String actorType;
    
    private Long hierarchyLevel;

    private String resolvedHierarchyValue;

    private Long responseCount;
    
    
    
    
}
