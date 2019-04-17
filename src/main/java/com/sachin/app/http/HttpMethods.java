package com.sachin.app.http;

/**
 * Enum to provide various HTTP methods to be used in {@link HttpResponse}
 */
public enum HttpMethods {

    GET("GET"),
    HEAD("HEAD"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    TRACE("TRACE"),
    CONNECT("CONNECT"),
    UNRECOGNIZED(null);

    private final String method;

    HttpMethods(String method) {
        this.method = method;
    }

}
