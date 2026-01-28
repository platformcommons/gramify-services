package com.platformcommons.platform.service.assessment.facade.assembler;

import com.platformcommons.platform.service.assessment.domain.AssessmentInstance;
import com.platformcommons.platform.service.assessment.domain.vo.AssessmentInstanceDetailVO;

public interface AssessmentInstanceDetailVoAssembler {

    AssessmentInstanceDetailVO toAssessmentDetailVo(AssessmentInstance assessmentInstance, String language, String name);

}
