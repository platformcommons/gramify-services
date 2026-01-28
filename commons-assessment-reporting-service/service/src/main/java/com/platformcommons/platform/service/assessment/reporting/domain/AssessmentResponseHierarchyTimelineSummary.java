package com.platformcommons.platform.service.assessment.reporting.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AssessmentResponseHierarchyTimelineSummary extends BaseReportEntity {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long assessmentId;
    
    private Long assessmentInstanceId;
    
    private String actorType;
    
    private Long hierarchyLevel;

    private String hierarchyResolvedValue;

    private Long responseCount;

    private String timelineType;

    private String timelineValue;
    
    
    
    
}
