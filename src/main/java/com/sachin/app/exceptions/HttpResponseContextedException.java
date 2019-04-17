package com.sachin.app.exceptions;

import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.apache.commons.lang3.exception.ExceptionContext;

/**
 * A contexted exception class that is used in {@link com.sachin.app.http.HttpResponse}
 */
public class HttpResponseContextedException extends ContextedRuntimeException {

    public HttpResponseContextedException() { }

    public HttpResponseContextedException(final String message) {
        super(message);
    }

    public HttpResponseContextedException(final Throwable cause) {
        super(cause);
    }

    public HttpResponseContextedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public HttpResponseContextedException(final String message,
                                             final Throwable cause,
                                             final ExceptionContext exceptionContext) {
        super(message, cause, exceptionContext);
    }
}
