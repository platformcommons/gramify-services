package com.platformcommons.platform.service.assessment.application.validator;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

@Component
public class CollectionsValidator {

    public <T> void validateCollectionIsEmptyOrNull(Collection<T> collection,String message){
        if (CollectionUtils.isEmpty(collection)) {
            throw new InvalidInputException(message);
        }
    }

}
