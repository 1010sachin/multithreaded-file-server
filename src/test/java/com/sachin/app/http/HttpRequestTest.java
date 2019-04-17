package com.sachin.app.http;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class HttpRequestTest {
    @Test
    public void doHeadRequest() throws IOException {
        HttpRequest req = new HttpRequest(new ByteArrayInputStream("HEAD / HTTP/1.1\n\n".getBytes()));
        Assert.assertEquals(HttpMethods.HEAD, req.method);
    }

    @Test
    public void doGetRequest() throws IOException {
        HttpRequest req = new HttpRequest(new ByteArrayInputStream("GET / HTTP/1.1\n\n".getBytes()));
        Assert.assertEquals(HttpMethods.GET, req.method);
    }

    @Test
    public void doUnknownRequest() throws IOException {
        HttpRequest req = new HttpRequest(new ByteArrayInputStream("WHAT / HTTP/1.1\n\n".getBytes()));
        Assert.assertEquals(HttpMethods.UNRECOGNIZED, req.method);
    }

}
