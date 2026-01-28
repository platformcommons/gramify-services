package com.platformcommons.platform.service.assessment.facade;

import com.platformcommons.platform.service.assessment.dto.UploadedAssessmentInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

public interface BulkUploadFacade {


    UploadedAssessmentInfo uploadV2(MultipartFile file, Integer maxDepth, Boolean createInstance) throws Exception;

    ByteArrayInputStream downloadAssessmentAsExcel(Long assessmentId) throws Exception;
}
