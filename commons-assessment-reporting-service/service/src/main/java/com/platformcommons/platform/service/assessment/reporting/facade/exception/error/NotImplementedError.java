package com.platformcommons.platform.service.assessment.reporting.facade.exception.error;

import com.platformcommons.platform.exception.base.BaseError;
import org.springframework.http.HttpStatus;

public class NotImplementedError implements BaseError {

    private final HttpStatus  status = HttpStatus.NOT_IMPLEMENTED;
    @Override
    public String code() {
        return "ERR_PLATFORM_GEN_NOT_IMPLEMENTED";
    }
    @Override
    public HttpStatus status() {
        return status;
    }

}
