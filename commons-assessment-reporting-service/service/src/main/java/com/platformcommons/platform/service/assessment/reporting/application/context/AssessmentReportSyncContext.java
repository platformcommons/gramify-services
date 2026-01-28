package com.platformcommons.platform.service.assessment.reporting.application.context;

import com.platformcommons.platform.service.assessment.reporting.dto.AssessmentInstanceDimDTO;
import com.platformcommons.platform.service.assessment.reporting.dto.OptionsDimDTO;
import com.platformcommons.platform.service.assessment.reporting.dto.QuestionDimDTO;
import com.platformcommons.platform.service.assessment.reporting.dto.SectionQuestionDimDTO;
import com.platformcommons.platform.service.assessment.reporting.facade.QuestionDimFacade;
import lombok.Builder;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AssessmentReportSyncContext {

    private final AssessmentInstanceDimDTO assessmentInstanceDim;
    private final Map<Long, SectionQuestionDimDTO> sectionQuestionDims;
    private final Map<Long, QuestionDimDTO> questionDims;
    private final Map<Long, OptionsDimDTO> optionsDims;

    private static QuestionDimFacade questionDimFacade;



    @Builder
    public AssessmentReportSyncContext(AssessmentInstanceDimDTO assessmentInstanceDim, Set<SectionQuestionDimDTO> sectionQuestionDims, Set<QuestionDimDTO> questionDims, Set<OptionsDimDTO> optionsDims) {
        this.assessmentInstanceDim = assessmentInstanceDim;
        this.sectionQuestionDims = sectionQuestionDims.stream().filter(sectionQuestionDimDTO -> sectionQuestionDimDTO.getLanguage().equals(assessmentInstanceDim.getBaseLanguage())).collect(Collectors.toMap(SectionQuestionDimDTO::getSectionQuestionId,Function.identity()));
        this.questionDims = questionDims.stream().filter(questionDimDTO -> questionDimDTO.getLanguage().equals(assessmentInstanceDim.getBaseLanguage())).collect(Collectors.toMap(QuestionDimDTO::getQuestionId,Function.identity()));
        this.optionsDims = optionsDims.stream().filter(optionsDimDTO -> optionsDimDTO.getLanguage().equals(assessmentInstanceDim.getBaseLanguage())).collect(Collectors.toMap(OptionsDimDTO::getDefaultOptionId,Function.identity()));
    }
    public static void setQuestionDimFacade(QuestionDimFacade questionDimFacade) {
        AssessmentReportSyncContext.questionDimFacade = questionDimFacade;
    }

    public AssessmentInstanceDimDTO getAssessmentInstanceDim() {
        return assessmentInstanceDim;
    }

    public Set<SectionQuestionDimDTO> getSectionQuestionDims() {
        return new HashSet<>(sectionQuestionDims.values());
    }

    public Set<QuestionDimDTO> getQuestionDims() {
        return new HashSet<>(questionDims.values());
    }

    public Set<OptionsDimDTO> getOptionsDims() {
        return new HashSet<>(optionsDims.values());
    }
    public SectionQuestionDimDTO getSectionQuestionDim(Long sectionQuestionId) {
        return sectionQuestionDims.get(sectionQuestionId);
    }

    public QuestionDimDTO getQuestionDim(Long questionId) {
        QuestionDimDTO questionDimDTO = questionDims.get(questionId);
        if(questionDimDTO==null) questionDimDTO = questionDimFacade.getByQuestionId(questionId,getAssessmentInstanceDim().getBaseLanguage());
        return questionDimDTO;
    }

    public OptionsDimDTO getOptionsDim(Long defaultOptionId) {
        return optionsDims.get(defaultOptionId);
    }

    public Map<Long,SectionQuestionDimDTO> getSectionQuestionDimsMap() {
        return sectionQuestionDims;
    }

    public Map<Long,QuestionDimDTO> getQuestionDimsMap() {
        return questionDims;
    }

    public Map<Long,OptionsDimDTO> getOptionsDimsMap() {
        return optionsDims;
    }

    public Set<QuestionDimDTO> getChildQuestions(Long questionId) {
        AtomicLong atomic = new AtomicLong(questionId);
        return getQuestionDims()
                .stream()
                .filter(dim -> dim.getParentQuestionId()!=null && dim.getParentQuestionId()==atomic.get())
                .collect(Collectors.toSet());
    }

    public Set<OptionsDimDTO> getChildOptions(Long questionId) {
        AtomicLong atomic = new AtomicLong(questionId);
        return getOptionsDims()
                .stream()
                .filter(dim -> dim.getParentQuestionId()!=null && dim.getParentQuestionId()==atomic.get())
                .collect(Collectors.toSet());
    }
}
