package com.platformcommons.platform.service.domain.exception;

import com.platformcommons.platform.exception.base.BaseError;
import com.platformcommons.platform.exception.base.PlatformUncheckedException;
import org.springframework.http.HttpStatus;

public class DetectedLoopException extends PlatformUncheckedException {


    public DetectedLoopException(String message) {
        super(message,LoopError.LOOP_ERROR);
    }

    protected DetectedLoopException(String message,  Throwable cause) {
        super(message,LoopError.LOOP_ERROR, cause);
    }

    public enum LoopError implements BaseError{

        LOOP_ERROR("ERR_PLATFORM_GEN_LOOP",HttpStatus.LOOP_DETECTED);

        private final String code;
        private final HttpStatus status;

        LoopError(String code, HttpStatus status){
            this.code = code;
            this.status = status;
        }

        @Override
        public String code() {
            return code;
        }

        @Override
        public HttpStatus status() {
            return status;
        }
    }

}
