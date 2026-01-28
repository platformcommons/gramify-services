package com.platformcommons.platform.service.assessment.application.validator.impl;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.service.assessment.application.validator.MLTextValidator;
import com.platformcommons.platform.service.entity.common.MLText;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

@Component
public class MLTextValidatorImpl implements MLTextValidator {
    @Override
    public void validate(Set<MLText> texts, String forEntityName) {
        for (MLText text : texts) {
            if(StringUtils.isEmpty(text.getText()) || StringUtils.isBlank(text.getText())){
                throw new InvalidInputException(String.format("Invalid text for %s ",forEntityName));
            }
        }
    }

    @Override
    public void validateNonNull(Set<MLText> texts, String forEntityName) {
        for (MLText text : texts) {
            if(Objects.isNull(text.getText())){
                throw new InvalidInputException(String.format("Invalid text for %s ",forEntityName));
            }
        }
    }


}
