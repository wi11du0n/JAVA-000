package org.example.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpLogResponseFilter implements HttpResponseFilter {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpLogResponseFilter.class);
    @Override
    public void filter(FullHttpRequest req, FullHttpResponse res, ChannelHandlerContext ctx) {
        LOGGER.info("res status {}", res.status());
    }
}
