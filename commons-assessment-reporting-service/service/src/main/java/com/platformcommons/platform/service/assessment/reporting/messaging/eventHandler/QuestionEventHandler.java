package com.platformcommons.platform.service.assessment.reporting.messaging.eventHandler;

import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.facade.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Transactional
public class QuestionEventHandler {

    private final QuestionDimFacade questionDimFacade;
    private final OptionsDimFacade optionsDimFacade;
    private final UserContributionDimFacade userContributionDimFacade;
    private final AssesseDimFacade assesseDimFacade;
    private final AssessmentInstanceDimFacade assessmentInstanceDimFacade;
    private final QuestionFactFacade questionFactFacade;
    private final OptionFactFacade optionFactFacade;
    private final SkillFactFacade skillFactFacade;

    public void createQuestionEvent(QuestionDTO questionDTO) {
        questionDimFacade.createQuestionDim(questionDTO);
        if((questionDTO.getDefaultOptionsList() != null && !questionDTO.getDefaultOptionsList().isEmpty()) ||
                (questionDTO.getMtfoptionList() != null && questionDTO.getMtfoptionList().isEmpty()))
            optionsDimFacade.createOptionDim(questionDTO);
        userContributionDimFacade.createQuestionContributions(questionDTO);
    }

    public void updateQuestionEvent(QuestionDTO questionDTO) {
        // Intentional Unlink To any Question Type
        questionDimFacade.unlink(questionDTO.getId());
        optionsDimFacade.unlink(questionDTO.getId());

        questionDimFacade.updateQuestionDim(questionDTO);
        optionsDimFacade.updateOptionDim(questionDTO);
        Set<AssessmentReportSyncContext> syncContexts = assessmentInstanceDimFacade.getSyncContext(questionDTO);
        syncContexts.forEach(syncContext->{
            questionFactFacade.questionUpdateEvent(questionDTO, syncContext);
            optionFactFacade.questionUpdateEvent(questionDTO, syncContext);
//            skillFactFacade.questionUpdateEvent(questionDTO, syncContext);
        });
        assessmentInstanceDimFacade.updateSyncContext(questionDTO);
    }
}
