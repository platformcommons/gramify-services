package com.platformcommons.platform.service.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class AssessmentInstanceAssesseFilterDTO {


    private Set<Long> instanceIds;
    private String context;
    private String subType;
    private String assesseId;
    private String assesseType;
    private String assessorId;
    private String assessorType;
    private Integer page;
    private Integer size;


}
