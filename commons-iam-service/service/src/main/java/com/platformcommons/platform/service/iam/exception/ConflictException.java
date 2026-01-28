package com.platformcommons.platform.service.iam.exception;

import com.platformcommons.platform.exception.base.BaseError;
import com.platformcommons.platform.exception.base.PlatformUncheckedException;
import org.springframework.http.HttpStatus;

public class ConflictException extends PlatformUncheckedException {

    public ConflictException(final String message) {
        super(message,new ConflictError());
    }

    public ConflictException(final String message, final Throwable cause) {
        super(message,new ConflictError(), cause);
    }

    static class ConflictError implements BaseError{

        private final String code = "ERR_PLATFORM_GEN_CONFLICT";
        @Override
        public String code() {
            return code;
        }

        @Override
        public HttpStatus status() {
            return HttpStatus.CONFLICT;
        }
    }

}
