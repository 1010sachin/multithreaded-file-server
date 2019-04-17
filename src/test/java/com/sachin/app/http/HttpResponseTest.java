package com.sachin.app.http;

import com.sachin.app.exceptions.HttpResponseContextedException;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URI;

public class HttpResponseTest {

    @Test
    public void validHeadRequest() throws Exception {
        HttpRequest req = new HttpRequest(new ByteArrayInputStream("HEAD / HTTP/1.1\n\n".getBytes()));
        File file = new File("helloWorld.html");
        URI responseFileURI = file.toURI();

        HttpResponse res = new HttpResponse(req, responseFileURI);

        Assert.assertTrue(res.headers.size() > 0);
        Assert.assertEquals(HttpResponse.VERSION + " " + ResponseStatus._200, res.headers.get(0));
    }

    @Test(expected = HttpResponseContextedException.class)
    public void invalidHeadRequest() throws Exception {
        HttpRequest req = new HttpRequest(new ByteArrayInputStream("GET / HTTP/1.1\n\n".getBytes()));
        File file = new File("xyz");
        URI responseFileURI = file.toURI();

        new HttpResponse(req, responseFileURI);
    }
}
