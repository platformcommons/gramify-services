package com.platformcommons.platform.service.iam.exception;

import com.platformcommons.platform.exception.base.BaseError;
import com.platformcommons.platform.exception.base.PlatformUncheckedException;
import org.springframework.http.HttpStatus;

public class RequestThrottledException extends PlatformUncheckedException {
    public RequestThrottledException(final String message) {
        super(message,new RequestThrottledException.RequestThrottledError());
    }

    public RequestThrottledException(final String message, final Throwable cause) {
        super(message,new RequestThrottledException.RequestThrottledError(), cause);
    }
    static class RequestThrottledError implements BaseError {

        private final String code = "ERR_PLATFORM_GEN_THROTTLED";
        @Override
        public String code() {
            return code;
        }

        @Override
        public HttpStatus status() {
            return HttpStatus.TOO_MANY_REQUESTS;
        }
    }
}
