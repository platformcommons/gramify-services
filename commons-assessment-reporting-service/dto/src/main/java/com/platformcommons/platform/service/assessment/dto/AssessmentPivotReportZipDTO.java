package com.platformcommons.platform.service.assessment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssessmentPivotReportZipDTO {

    private byte[] bytes;
    private String instanceName;

    public AssessmentPivotReportZipDTO(byte[] bytes, String instanceName) {
        this.bytes = bytes;
        this.instanceName = instanceName;
    }

}
