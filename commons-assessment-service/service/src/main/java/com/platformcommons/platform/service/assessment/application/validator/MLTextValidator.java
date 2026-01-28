package com.platformcommons.platform.service.assessment.application.validator;

import com.platformcommons.platform.service.entity.common.MLText;

import java.util.Set;

public interface MLTextValidator {
    void validate(Set<MLText> texts, String forEntityName);

    void validateNonNull(Set<MLText> texts, String forEntityName);

}
