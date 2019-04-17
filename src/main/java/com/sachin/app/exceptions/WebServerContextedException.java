package com.sachin.app.exceptions;

import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.apache.commons.lang3.exception.ExceptionContext;

/**
 * A contexted exception class that is used in {@link com.sachin.app.App}
 */
public class WebServerContextedException extends ContextedRuntimeException {

    public WebServerContextedException() { }

    public WebServerContextedException(final String message) {
        super(message);
    }

    public WebServerContextedException(final Throwable cause) {
        super(cause);
    }

    public WebServerContextedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public WebServerContextedException(final String message,
                                       final Throwable cause,
                                       final ExceptionContext exceptionContext) {
        super(message, cause, exceptionContext);
    }
}
