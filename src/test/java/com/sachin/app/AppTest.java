package com.sachin.app;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    @Test
    public void parseCorrectPortParam() {
        String serverPort = "1234";
        Assert.assertEquals(1234, App.validateServerPort(serverPort));
    }

    @Test(expected = IllegalArgumentException.class)
    public void zeroServerPort() {
        String serverPort = "";
        App.validateServerPort(serverPort);
    }

    @Test(expected = NumberFormatException.class)
    public void wrongParamThrowException() {
        String serverPort= "asda";
        App.validateServerPort(serverPort);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidPortThrowException() {
        String serverPort = "0";
        App.validateServerPort(serverPort);
    }

    @Test(expected = NumberFormatException.class)
    public void invalidPortTooHighThrowException() {
        String serverPort = "65536";
        App.validateServerPort(serverPort);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyFileURIThrowsException() {
        String responseFileURI = "";
        App.parseResponseFileURI(responseFileURI);
    }

    @Test(expected = IllegalArgumentException.class)
    public void InvalidFileURIThrowsException() {
        String responseFileURI = "abc";
        App.parseResponseFileURI(responseFileURI);
    }

}
