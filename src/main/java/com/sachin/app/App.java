package com.sachin.app;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URI;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sachin.app.exceptions.WebServerContextedException;
import io.prometheus.client.Histogram;
import org.apache.log4j.Logger;

/**
 * This is the main entry point into the Multithreaded File Web Server Application.
 */
public class App
{
    private static final Logger LOG = Logger.getLogger(App.class);
    private static final int N_THREADS = 3;
    public static void main( String[] args )
    {
        if(args.length < 3) {
            System.err.println("Require three arguments to run the app. "
                            + "Port Number, Path to the response file and "
                            + "number of threads");
        }

        final int serverPort = validateServerPort(args[0]);

        final URI responseFileURI = Objects.requireNonNull(parseResponseFileURI(args[1]));

        final int threads = Integer.parseInt(args[2]) > 0 ? Integer.parseInt(args[2]) : N_THREADS;

        final Histogram requestLatency = Histogram.build()
                .name("RequestLatency")
                .help("Request Latency in seconds")
                .create();

        requestLatency.register();

        try {
            LOG.info("Starting File Web Server");
            new App().start(serverPort, responseFileURI, threads, requestLatency);
        } catch (Exception e) {
            String message = "Failed to start the web server";
            WebServerContextedException exception = new WebServerContextedException(message, e);
            exception.addContextValue("Port", serverPort);
            exception.addContextValue("ResponseFileRequested", responseFileURI.getPath());
            exception.addContextValue("NumberOfThreads", threads);
            throw exception;
        }

    }

    protected static int validateServerPort(String serverPort) {
        if(Integer.parseInt(serverPort) <= 0 || serverPort.isEmpty()) {
            throw new IllegalArgumentException("Server port must be greater than 0");
        }

        int validPort = Integer.parseInt(serverPort);

        if(validPort > 0 && validPort < 65535) {
            return validPort;
        } else {
            throw new NumberFormatException("Invalid port! Port value is a number between 0 and 65535");
        }
    }

    protected static URI parseResponseFileURI(final String uriString) {
        URI responseFileUri;

        if(!uriString.isEmpty()) {
            responseFileUri = URI.create(uriString);

            if (responseFileUri.getScheme() == null) {
                File file = new File(uriString);

                if (!file.exists()) {
                    LOG.error("File does not exist: " + uriString);
                    throw new IllegalArgumentException("File " + uriString + " does not exist");
                }

                try {
                    responseFileUri = file.getCanonicalFile().toURI();
                } catch (IOException ioe) {
                    LOG.error("Unable to convert file to URI", ioe);
                    System.exit(1);
                }
            }
        } else {
            throw new IllegalArgumentException("URI path cannot be empty");
        }

        return responseFileUri;
    }

    public void start(final int port,
                      final URI responseFileURI,
                      final int threads,
                      final Histogram requestLatency) throws Exception {
        String logMessage = String.format("Starting %d threads", threads);
        LOG.info(logMessage);
        ServerSocket s = new ServerSocket(port);
        LOG.info("Web server listening on port " + port);
        CustomThreadFactory customThreadFactory = new CustomThreadFactory("WebFileServer");
        ExecutorService executor = Executors.newFixedThreadPool(threads, customThreadFactory);
        while (true) {
            executor.submit(new FileRequestHandler(s.accept(), responseFileURI, requestLatency));
        }
    }
}
