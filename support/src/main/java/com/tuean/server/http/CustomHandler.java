package com.tuean.server.http;

import com.tuean.consts.Const;
import com.tuean.consts.ResourceType;
import com.tuean.entity.HttpHeader;
import com.tuean.entity.RequestHolder;
import com.tuean.exception.BadRequestException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;

import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class CustomHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(CustomHandler.class);

    private Router router;

    public CustomHandler(Router router) {
        this.router = router;
    }

    private HttpRequest request;
    private HttpContent content;
    byte[] responseData;

    private String contentType;


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
        logger.debug("channel read complete");
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
            logger.info("Request: " + fullHttpRequest);
            contentType = request.headers().get(HttpHeaderNames.CONTENT_TYPE);
        }
        if (msg instanceof HttpRequest) {
            HttpRequest request = this.request = (HttpRequest) msg;
            if (HttpUtil.is100ContinueExpected(request)) {
                writeResponse(ctx);
            }
        }

        if (msg instanceof HttpContent) {
            this.content = (HttpContent) msg;

            if (msg instanceof LastHttpContent) {
                LastHttpContent trailer = (LastHttpContent) msg;
                try {
                    RequestHolder holder = router.doRequest(this.request, this.content);
                    writeResponse(ctx, trailer, holder.getResponse(), "UTF-8", holder.getResourceType(), holder.getHeaders());
                } catch (BadRequestException badRequestException) {
                    writeResponse(ctx, trailer, router.getObjectMapper().writeValueAsBytes(Const.bad_request), "UTF-8", ResourceType.json, null);
                } catch (Exception var) {
                    logger.error("", var);
                    writeResponse(ctx, trailer, router.getObjectMapper().writeValueAsBytes(Const.err), "UTF-8", ResourceType.json, null);
                }
            }
        }

        ctx.fireChannelRead(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    private void writeResponse(ChannelHandlerContext ctx) {
        ByteBuf content = ctx.alloc().buffer(); //
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, CONTINUE, Unpooled.EMPTY_BUFFER);
        ctx.write(response);
        logger.debug("finish response:{}", response);
    }

    private void writeResponse(ChannelHandlerContext ctx, LastHttpContent trailer,
                               byte[] responseData, String encoding, ResourceType resourceType, List<HttpHeader> headers) {
        boolean keepAlive = HttpUtil.isKeepAlive(request);
        FullHttpResponse httpResponse = new DefaultFullHttpResponse(HTTP_1_1,
                ((HttpObject) trailer).decoderResult().isSuccess() ? OK : BAD_REQUEST,
                Unpooled.copiedBuffer(responseData == null ? new byte[0] : responseData));

        switch (resourceType) {
            case html -> httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=" + encoding);
            case css -> httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/css; charset=" + encoding);
            case javascript -> httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/javascript; charset=" + encoding);
            case json -> httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=" + encoding);
            default -> {}
        }

        if (keepAlive) {
            httpResponse.headers().setInt(HttpHeaderNames.CONTENT_LENGTH,
                    httpResponse.content().readableBytes());
            httpResponse.headers().set(HttpHeaderNames.CONNECTION,
                    HttpHeaderValues.KEEP_ALIVE);
        }

        if (CollectionUtils.isNotEmpty(headers)) {
            headers.forEach(header -> httpResponse.headers().set(header.getKey(), header.getValue()));
        }

        ctx.write(httpResponse);

        if (!keepAlive) {
            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }

}
