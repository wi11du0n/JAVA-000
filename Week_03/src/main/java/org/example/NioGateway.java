package org.example;


import org.example.filter.HttpLogRequestFilter;
import org.example.filter.HttpLogResponseFilter;
import org.example.inbound.HttpInboundHandler;
import org.example.inbound.HttpInboundServer;
import org.example.outbound.HttpOutboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.System.out;

public class NioGateway {
    public final static String GATEWAY_NAME = "NIOGateway";
    public final static String GATEWAY_VERSION = "1.0.0";
    private final static Logger LOGGER = LoggerFactory.getLogger(NioGateway.class);

    public static void main(String[] args) {
        String proxyServer = System.getProperty("proxyServer", "http://localhost:8088");
        String port = System.getProperty("proxyPort", "8888");
        int p = tryParsePort(port);
        HttpOutboundHandler.endpoints.add(proxyServer);
        HttpOutboundHandler.endpoints.add(proxyServer);
        HttpOutboundHandler.endpoints.add(proxyServer);

        HttpOutboundHandler.filters.add(new HttpLogResponseFilter());
        HttpInboundHandler.filters.add(new HttpLogRequestFilter());

        out.println(GATEWAY_NAME + " " + GATEWAY_VERSION + " starting...");

        HttpInboundServer server = new HttpInboundServer(p);
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION + " started at http://localhost:"
                + port + " for server:" + proxyServer);
        try {
            server.run();
        } catch (Exception e) {
            LOGGER.error("server run error.", e);
        }
    }

    private static int tryParsePort(String port) {
        int p;
        try {
            p = Integer.parseInt(port);
        } catch (Exception e) {
            p = 8888;
        }

        return p;
    }
}
