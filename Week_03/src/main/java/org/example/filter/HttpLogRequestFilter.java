package org.example.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpLogRequestFilter implements HttpRequestFilter {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpLogRequestFilter.class);
    @Override
    public void filter(FullHttpRequest req, ChannelHandlerContext ctx) {
        LOGGER.info("request uri is {}", req.uri());
    }
}
