package com.sachin.app;

import com.google.common.base.Stopwatch;
import com.sachin.app.http.HttpRequest;
import com.sachin.app.http.HttpResponse;
import com.sachin.app.http.MetricsHttpResponse;
import io.prometheus.client.Histogram;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.Socket;
import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * This class is responsible to handle request threads and provides {@link HttpRequest}
 * {@link HttpResponse} objects to accept and write response back to the client.
 * This class also handles responding to the /metrics request.
*/
public class FileRequestHandler implements Runnable {
    private static final Logger LOG = Logger.getLogger(FileRequestHandler.class);
    private final Stopwatch stopwatch = Stopwatch.createUnstarted();
    private Socket socket;
    private URI responseFileURI;
    private final Histogram requestLatency;

    FileRequestHandler(final Socket socket,
                       final URI responseFileURI,
                       final Histogram requestLatency) {
        this.socket = socket;
        this.responseFileURI = responseFileURI;
        this.requestLatency = requestLatency;
    }

    public void run() {
        try {
            LOG.info("Processing new HTTP request");
            HttpRequest req = new HttpRequest(socket.getInputStream());
            stopwatch.start();
            if(!req.uri.equals("/")) {
                // Write the histogram metrics for requestLatency to a prometheus compatible endpoint
                if(req.uri.equals("/metrics")) {
                    new MetricsHttpResponse().processRequest(socket.getOutputStream());
                } else {
                    File file = new File(req.uri);
                    if(file.exists()) {
                        Histogram.Timer timer = requestLatency.startTimer();
                        HttpResponse httpResponse = new HttpResponse(req, file.getCanonicalFile().toURI());
                        httpResponse.writeResponse(socket.getOutputStream());
                        timer.observeDuration();
                    }
                }
            } else {
                Histogram.Timer timer = requestLatency.startTimer();
                HttpResponse httpResponse = new HttpResponse(req, responseFileURI);
                httpResponse.writeResponse(socket.getOutputStream());
                timer.observeDuration();
            }
            LOG.info("Request took :" + stopwatch.elapsed(TimeUnit.NANOSECONDS) + " nanoseconds");
            socket.close();
        } catch (Exception e) {
            LOG.error("Failed to write the output", e);
            System.exit(1);
        }
    }
}
