package org.example.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import org.example.filter.HttpRequestFilter;
import org.example.outbound.HttpOutboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpInboundHandler.class);
    private HttpOutboundHandler outboundHandler = new HttpOutboundHandler();

    public static List<HttpRequestFilter> filters = new ArrayList<>();

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            callFilter(fullRequest, ctx);
            outboundHandler.handle(fullRequest, ctx);
        } catch (Exception e) {
            LOGGER.error("outbound handle error.", e);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    private void callFilter(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) {
        if (filters == null || filters.isEmpty()) return;

        filters.forEach(f -> f.filter(fullRequest, ctx));
    }
}
