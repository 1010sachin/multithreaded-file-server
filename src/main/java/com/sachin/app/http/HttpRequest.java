package com.sachin.app.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that reads and parses the incoming http requests.
 */
public class HttpRequest {

    private List<String> headers = new ArrayList<String>();
    private String version;

    HttpMethods method;

    public String uri;

    public HttpRequest(InputStream is) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String str = reader.readLine();
        parseRequestLine(str);

        while (!str.equals("")) {
            str = reader.readLine();
            parseRequestHeader(str);
        }
    }

    private void parseRequestLine(String str) {
        String[] split = str.split("\\s+");
        try {
            method = HttpMethods.valueOf(split[0]);
        } catch (Exception e) {
            method = HttpMethods.UNRECOGNIZED;
        }
        uri = split[1];
        version = split[2];
    }

    private void parseRequestHeader(String str) {
        headers.add(str);
    }

}

