package com.tuean.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuean.exception.BadRequestException;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class RequestUtils {

    private static final Logger logger = LoggerFactory.getLogger(RequestUtils.class);

    public static StringBuilder formatParams(HttpRequest request) {
        StringBuilder responseData = new StringBuilder();
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.uri());
        Map<String, List<String>> params = queryStringDecoder.parameters();
        if (!params.isEmpty()) {
            for (Map.Entry<String, List<String>> p : params.entrySet()) {
                String key = p.getKey();
                List<String> vals = p.getValue();
                for (String val : vals) {
                    responseData.append("Parameter: ").append(key.toUpperCase()).append(" = ")
                            .append(val.toUpperCase()).append("\r\n");
                }
            }
            responseData.append("\r\n");
        }
        return responseData;
    }

    public static StringBuilder formatBody(HttpContent httpContent) {
        StringBuilder responseData = new StringBuilder();
        ByteBuf content = httpContent.content();
        if (content.isReadable()) {
            responseData.append(content.toString(CharsetUtil.UTF_8).toUpperCase())
                    .append("\r\n");
        }
        return responseData;
    }

    public static StringBuilder prepareLastResponse(HttpRequest request, LastHttpContent trailer) {
        StringBuilder responseData = new StringBuilder();
        responseData.append("Good Bye!\r\n");

        if (!trailer.trailingHeaders().isEmpty()) {
            responseData.append("\r\n");
            for (CharSequence name : trailer.trailingHeaders().names()) {
                for (CharSequence value : trailer.trailingHeaders().getAll(name)) {
                    responseData.append("P.S. Trailing Header: ");
                    responseData.append(name).append(" = ").append(value).append("\r\n");
                }
            }
            responseData.append("\r\n");
        }
        return responseData;
    }


    public static void fillRequestParam(Object[] args, Class typeClass, Map<String, List<String>> params, String key, int x) {
        if (params == null) {
            args[x] = null;
            return;
        }
        if (params.get(key) != null) {
            String v = String.join("", params.get(key));
            args[x] = ConvertUtils.convert(v, typeClass);
        } else {
            args[x] = null;
        }
    }


    public static void fillRequestHeader(Object[] args, Class typeClass, HttpHeaders headers, String key, int x) {
        if (headers == null) {
            args[x] = null;
            return;
        }
        if (headers.get(key) != null) {
            args[x] = ConvertUtils.convert(headers.get(key), typeClass);
        } else {
            args[x] = null;
        }
    }

    public static void fillRequestBody(Object[] args, Class typeClass, HttpContent content, int x, ObjectMapper mapper) {
        try {
            args[x] = mapper.readValue(content.content().toString(CharsetUtil.UTF_8), typeClass);
        } catch (Exception var) {
            logger.warn("fail to convert http content", var);
            throw new BadRequestException();
        }
    }

}
