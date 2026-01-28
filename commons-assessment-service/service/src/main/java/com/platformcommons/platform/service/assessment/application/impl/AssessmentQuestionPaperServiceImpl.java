package com.platformcommons.platform.service.assessment.application.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.assessment.application.AssessmentInstanceService;
import com.platformcommons.platform.service.assessment.application.AssessmentQuestionPaperService;
import com.platformcommons.platform.service.assessment.application.validator.AssessmentQuestionPaperValidator;
import com.platformcommons.platform.service.assessment.domain.AssessmentInstance;
import com.platformcommons.platform.service.assessment.domain.AssessmentQuestionPaper;
import com.platformcommons.platform.service.assessment.domain.repo.AssessmentQuestionPaperNonMTRepository;
import com.platformcommons.platform.service.assessment.domain.repo.AssessmentQuestionPaperRepository;
import com.platformcommons.platform.service.assessment.domain.repo.SectionQuestionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AssessmentQuestionPaperServiceImpl implements AssessmentQuestionPaperService {

    private final AssessmentQuestionPaperRepository repository;
    private final AssessmentQuestionPaperNonMTRepository questionPaperNonMTRepository;
    private final AssessmentInstanceService assessmentInstanceService;
    private final AssessmentQuestionPaperValidator assessmentQuestionPaperValidator;
    private final SectionQuestionsRepository sectionQuestionsRepository;


    @Override
    public AssessmentQuestionPaper createAssessmentQuestionPaper(AssessmentQuestionPaper questionPaper) {
        assessmentQuestionPaperValidator.validateOnCreate(questionPaper.init());
        return repository.save(questionPaper);
    }

    @Override
    public Set<AssessmentQuestionPaper> getAllAssessmentQuestionPapersOfInstance(Long instanceId) {
        assessmentInstanceService.getAssessmentInstanceByIdv2(instanceId);
        return questionPaperNonMTRepository.getAllAssessmentQuestionPapersOfInstance(instanceId);
    }

    @Override
    public AssessmentQuestionPaper updateAssessmentQuestionPaperV3(AssessmentQuestionPaper toUpdatedAssessmentQuestionPaper) {
        AssessmentQuestionPaper assessmentQuestionPaper = repository.findByIdAndUserId(toUpdatedAssessmentQuestionPaper.getId(), PlatformSecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new NotFoundException("ERR_SVC_AQP_NOT_FOUND"));
        assessmentQuestionPaper.patch(toUpdatedAssessmentQuestionPaper);
        assessmentQuestionPaperValidator.validateOnUpdate(assessmentQuestionPaper);
        return repository.save(assessmentQuestionPaper);
    }

    @Override
    public boolean checkQuestionPaperForAssessment(Long id) {
        return repository.existsByAssessment_Id(id);
    }

    @Override
    public Set<AssessmentQuestionPaper> getAllAssessmentQuestionPapersByAssessmentId(Long id) {
        Set<AssessmentQuestionPaper> assessmentQuestionPapers = repository.findByAssessmentId(id);
        if (assessmentQuestionPapers.isEmpty())
            throw new NotFoundException("ERR_SVC_AQP_NOT_FOUND");
        return assessmentQuestionPapers;
    }

    @Override
    public Long getAssessmentInstanceIdByQuestionId(Long id) {
        return sectionQuestionsRepository.getSectionQuestionByQuestionId(id)
                .flatMap(sectionQuestions -> sectionQuestions.getQuestionPaperSection()
                        .getAssessmentQuestionPaper()
                        .getAssessment()
                        .getAssessmentInstanceList()
                        .stream()
                        .findAny()
                        .map(AssessmentInstance::getId))
                .orElse(null);
    }


}
