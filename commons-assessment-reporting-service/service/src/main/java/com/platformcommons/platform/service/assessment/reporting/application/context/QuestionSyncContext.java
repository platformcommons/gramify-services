package com.platformcommons.platform.service.assessment.reporting.application.context;

import com.platformcommons.platform.service.assessment.reporting.domain.OptionsDim;
import com.platformcommons.platform.service.assessment.reporting.domain.QuestionDim;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;
@Getter
@Setter
public class QuestionSyncContext {


    private Set<QuestionDim> questionDims;
    private Set<OptionsDim> optionsDims;
    public QuestionSyncContext(Set<QuestionDim> questionDims, Set<OptionsDim> optionsDims){
        this.optionsDims = optionsDims==null?new LinkedHashSet<>():optionsDims;
        this.questionDims = questionDims==null?new LinkedHashSet<>():questionDims;
    }

    public void addQuestionDim(QuestionDim questionDim){
        questionDims.add(questionDim);
    }
    public void addOptionsDim(OptionsDim optionsDim){
        optionsDims.add(optionsDim);
    }

}
