package com.sachin.app.http;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.common.TextFormat;
import io.prometheus.client.hotspot.DefaultExports;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that writes the metrics to server the request for /metrics endpoint.
 */
public class MetricsHttpResponse {
    private final CollectorRegistry registry = CollectorRegistry.defaultRegistry;
    private final List<String> headers = new ArrayList<String>();
    private static final String VERSION = "HTTP/1.1";

    public void processRequest(OutputStream outputStream) throws IOException {

        Writer writer = new OutputStreamWriter(outputStream);

        setHeaders(ResponseStatus._200);

        for (String header : headers) {
            writer.write(header + "\r"
                    + System.lineSeparator());
        }
        writer.write("\r" + System.lineSeparator());
        try {

            DefaultExports.initialize();
            TextFormat.write004(writer, registry.metricFamilySamples());
            writer.flush();
        } finally {
            writer.close();
        }
    }

    private void setHeaders(ResponseStatus status) {
        headers.add(VERSION + " " + status.toString());
        headers.add(TextFormat.CONTENT_TYPE_004);
    }
}
