package org.example.outbound;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.example.filter.HttpResponseFilter;
import org.example.router.HttpEndpointRouter;
import org.example.router.RandomHttpEndpointRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpOutboundHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpOutboundHandler.class);

    public static List<String> endpoints = new ArrayList<>();
    public static List<HttpResponseFilter> filters = new ArrayList<>();
    private HttpEndpointRouter router = new RandomHttpEndpointRouter();

    public void handle(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        final String url = router.route(endpoints) + fullRequest.uri();
        proxy(url, fullRequest, ctx);
    }

    private void handleResponse(Response res, FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        FullHttpResponse nettyRes = null;
        try {
            nettyRes = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.valueOf(res.code()),
                    Unpooled.wrappedBuffer(res.body().string().getBytes()));
            final HttpHeaders headers = nettyRes.headers();
            res.headers().forEach(h -> headers.add(h.getFirst(), h.getSecond()));

            callFilter(fullRequest, nettyRes, ctx);
        } catch (Exception e) {
            LOGGER.error("outbound handle response error.", e);
            nettyRes = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.INTERNAL_SERVER_ERROR);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(nettyRes).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(nettyRes);
                }
            }
            ctx.flush();
        }
    }

    private void callFilter(FullHttpRequest fullRequest, FullHttpResponse res, ChannelHandlerContext ctx) {
        if (filters == null || filters.isEmpty()) return;

        filters.forEach(f -> f.filter(fullRequest, res, ctx));
    }

    void proxy(String url, FullHttpRequest req, ChannelHandlerContext ctx) {
        if (url == null || url.isEmpty()) {
            return;
        }

        OkHttpClient client = new OkHttpClient();
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<String, String> entry : req.headers()) {
            map.put(entry.getKey(), entry.getValue());
        }

        // todo body
        //RequestBody.create(req.content().toString(CharsetUtil.UTF_8), MediaType.parse("")
        Request request = new Request.Builder()
                .headers(Headers.of(map))
                .method(req.method().toString(), null)
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            handleResponse(response, req, ctx);
        } catch (IOException e) {
            LOGGER.error("okhttp client proxy io error.", e);
            return;
        }
    }
}
