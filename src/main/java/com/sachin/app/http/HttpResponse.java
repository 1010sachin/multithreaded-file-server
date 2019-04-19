package com.sachin.app.http;

import com.sachin.app.exceptions.HttpResponseContextedException;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.File;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class that responds to the application's http request
 */
public class HttpResponse {

    private static final Logger LOG = Logger.getLogger(HttpResponse.class);
    private final HttpRequest request;

    static final String VERSION = "HTTP/1.1";

    final List<String> headers = new ArrayList<String>();

    private URI responseFileURI;

    private InputStream resultInputStream = null;

    public HttpResponse(final HttpRequest request,
                        final URI responseFileURI) {
        this.request = request;
        this.responseFileURI = responseFileURI;
        setResponseHeaderAndBody();
    }

    private void setResponseHeaderAndBody() {
        switch (request.method) {
            case GET:
                try {
                    if("file".equals(responseFileURI.getScheme())) {
                        if(new File(responseFileURI).isFile()) {
                            InputStream inputStream = responseFileURI.toURL().openStream();
                            setResultInputStream(inputStream);
                            int contentLength = inputStream.available();
                            if(responseFileURI.getPath().endsWith("html")) {
                                setHeaders(ResponseStatus._200, "text/html", contentLength);
                            }
                            else if(responseFileURI.getPath().endsWith("css")){
                                setHeaders(ResponseStatus._200, "text/css", contentLength);
                            }
                            else {
                                setHeaders(ResponseStatus._200, "text", contentLength);
                            }
                        } else {
                            // In case of directory list the contents
                            StringBuilder result = new StringBuilder("<html><head><title>Index of ");
                            result.append(request.uri);
                            result.append("</title></head><body><h1>Index of ");
                            result.append(responseFileURI.getPath());
                            result.append("</h1><hr><pre>");
                            File files[] = new File(responseFileURI.getPath()).listFiles();
                            for(File file : files) {
                                result.append(" <a href=\"" + file.getPath() + "\">" + file.getPath() + "</a>\n");
                            }
                            result.append("<hr></pre></body></html>");

                            InputStream inputStream = IOUtils.toInputStream(result.toString(), StandardCharsets.UTF_8);
                            int contentLength = inputStream.available();
                            setHeaders(ResponseStatus._200, "text/html", contentLength);
                            setResultInputStream(inputStream);
                        }
                    } else {
                        setHeaders(ResponseStatus._400, null, 0);
                        setResultInputStream(null);
                    }

                } catch (Exception e) {
                    String message = "Failed to read the file as input stream";
                    HttpResponseContextedException exception = new HttpResponseContextedException(message, e);
                    exception.addContextValue("File Path", responseFileURI.getPath());
                    throw exception;
                }
                break;
            case HEAD:
                try {
                    InputStream inputStream = IOUtils.toInputStream(ResponseStatus._200.toString(), StandardCharsets.UTF_8);
                    int contentLength = inputStream.available();
                    setHeaders(ResponseStatus._200, null, contentLength);
                    setResultInputStream(inputStream);
                } catch(Exception e) {
                    String message = "Failed to process request";
                    HttpResponseContextedException exception = new HttpResponseContextedException(message, e);
                    exception.addContextValue("Request", request.method);
                    exception.addContextValue("URI", request.uri);
                    throw exception;
                }
                break;
            case UNRECOGNIZED:
                try {
                    InputStream inputStream = IOUtils.toInputStream(ResponseStatus._400.toString(), StandardCharsets.UTF_8);
                    int contentLength = inputStream.available();
                    setHeaders(ResponseStatus._400, null, contentLength);
                    setResultInputStream(inputStream);
                } catch(Exception e) {
                    String message = "Failed to process request";
                    HttpResponseContextedException exception = new HttpResponseContextedException(message, e);
                    exception.addContextValue("Request", request.method);
                    exception.addContextValue("URI", request.uri);
                    throw exception;
                }
                break;
            default:
                try {
                    InputStream inputStream = IOUtils.toInputStream(ResponseStatus._501.toString(), StandardCharsets.UTF_8);
                    int contentLength = inputStream.available();
                    setHeaders(ResponseStatus._501, null, contentLength);
                    setResultInputStream(inputStream);
                } catch(Exception e) {
                    String message = "Failed to process request";
                    HttpResponseContextedException exception = new HttpResponseContextedException(message, e);
                    exception.addContextValue("Request", request.method);
                    exception.addContextValue("URI", request.uri);
                    throw exception;
                }
        }
    }

    private void setResultInputStream(InputStream inputStream) {
        this.resultInputStream = inputStream;
    }

    public void writeResponse(final OutputStream outputStream) throws IOException {
        InputStream inputStream = this.resultInputStream;
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        for (String header : headers) {
            dataOutputStream.writeBytes(header + "\r"
                    + System.lineSeparator());
        }
        dataOutputStream.writeBytes("\r" + System.lineSeparator());
        try {
            if(inputStream != null) {
                int bytesWritten = IOUtils.copy(inputStream, dataOutputStream);
                String logMessage = String.format("Wrote %d bytes to the response", bytesWritten);
                LOG.info(logMessage);
            } else {
                dataOutputStream.writeBytes("Bad Request!");
            }

        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(dataOutputStream);
            IOUtils.closeQuietly(outputStream);
        }
    }

    private void setHeaders(ResponseStatus status, String contentType, int contentLength) {
        headers.add(VERSION + " " + status.toString());
        headers.add("Server: WebFileServer");
        headers.add("Content-length: " + contentLength);
        headers.add("Content-type: " + contentType);
    }
}
