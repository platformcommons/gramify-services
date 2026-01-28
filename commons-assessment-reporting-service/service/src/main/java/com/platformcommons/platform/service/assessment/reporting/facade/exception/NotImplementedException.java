package com.platformcommons.platform.service.assessment.reporting.facade.exception;

import com.platformcommons.platform.exception.base.PlatformUncheckedException;
import com.platformcommons.platform.service.assessment.reporting.facade.exception.error.NotImplementedError;

public class NotImplementedException extends PlatformUncheckedException  {
    public NotImplementedException(final String message) {
        super(message,new NotImplementedError());
    }

    public NotImplementedException(final String message, final Throwable cause) {
        super(message,new NotImplementedError(), cause);
    }

}
