package com.platformcommons.platform.service.assessment.exception;

import com.platformcommons.platform.exception.PlatformError;

import com.platformcommons.platform.exception.base.PlatformUncheckedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntity extends PlatformUncheckedException {
    public UnprocessableEntity(final String message) {
        super(message, PlatformError.ERR_PLT_GEN_DUPLICATE);
    }

    public UnprocessableEntity(final String message, final Throwable cause) {
        super(message, PlatformError.ERR_PLT_GEN_DUPLICATE, cause);
    }
}
