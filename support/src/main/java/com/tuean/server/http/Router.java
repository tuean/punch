package com.tuean.server.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.tuean.annotation.ApiJson;
import com.tuean.cache.ResourceCache;
import com.tuean.consts.Const;
import com.tuean.consts.ResourceType;
import com.tuean.entity.FileContent;
import com.tuean.entity.RequestHolder;
import com.tuean.exception.DuplicatePathException;
import com.tuean.util.RequestUtils;
import com.tuean.util.Util;
import io.netty.handler.codec.http.*;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import static com.tuean.util.ReflectionUtil.findAnnotatedClasses;


public class Router {

    private static final Logger logger = LoggerFactory.getLogger(Router.class);

    private final Map<ApiJson, Method> apiMappings = Maps.newConcurrentMap();

    private final Map<String, FileContent> fileMappings = Maps.newConcurrentMap();

    private static final ObjectMapper mapper = new ObjectMapper();

    public ObjectMapper getObjectMapper() {
        return mapper;
    }

    static {
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    }
    private String packageName;



    public Router(String packageName) {
        this.packageName = packageName;
    }

    public void init() {
        Set<Class<?>> annotatedClasses = findAnnotatedClasses(this.packageName, ApiJson.class);
        registerRequestMappings(annotatedClasses);
    }

    public void init(ResourceCache cache) {
        registerFiles(cache);
    }

    private void registerRequestMappings(Set<Class<?>> annotatedClasses) {
        // Print the names of the annotated classes
        for (Class<?> annotatedClass : annotatedClasses) {
            try {
                Object bean = context.getBean(annotatedClass);
                Method[] methods = annotatedClass.getMethods();
                Arrays.stream(methods).forEach(method -> {
                    ApiJson apiJson = method.getAnnotation(ApiJson.class);
                    if (apiJson == null) return;
                    String path = apiJson.path();
                    apiMappings.put(apiJson, method);
                    logger.info("add url mapping: {}", apiJson.path());
                });
            } catch (BeansException var) {
                logger.warn("can't find bean of class {}", annotatedClass);
            }
        }
        checkDuplicateUrl();
    }

    public void registerFiles(ResourceCache resourceCache) {
        fileMappings.putAll(resourceCache.getCache().asMap());
        fileMappings.entrySet().forEach(entry -> {
            logger.info("register resource file:{} {}", entry.getKey(), entry.getValue());
        });
    }

    private void checkDuplicateUrl() {
        Set<String> urls = new HashSet<>();
        apiMappings.keySet().forEach(mapping -> {
            String[] paths = mapping.path();
            String[] values = mapping.value();
            Arrays.stream(paths).forEach(n -> {
                if (urls.contains(n)) throw new DuplicatePathException();
                urls.add(n);
            });
            Arrays.stream(values).forEach(n -> {
                if (urls.contains(n)) throw new DuplicatePathException();
                urls.add(n);
            });
        });
    }

    public RequestHolder doRequest(HttpRequest request, HttpContent content) throws JsonProcessingException, InvocationTargetException, IllegalAccessException {
        DefaultHttpRequest fullHttpRequest = (DefaultHttpRequest) request;
        String contentType = request.headers().get(HttpHeaderNames.CONTENT_TYPE);
        HttpMethod httpMethod = fullHttpRequest.method();
        String uri = request.uri(), pureUrl = Util.pureUrl(uri);

        if (contentType != null && contentType.contains("application/json")) {
            Method method = getMethod(httpMethod, pureUrl);

            if (method == null) return new RequestHolder(ResourceType.json, mapper.writeValueAsBytes(Const.not_found));

            Object[] args = parseToArgs(fullHttpRequest, content, method);
            Object methodBean = getMethodClassBean(method);
            Object result = method.invoke(methodBean, args);
            return result == null ? null : new RequestHolder(ResourceType.json, mapper.writeValueAsBytes(result));
        } else if (httpMethod.equals(HttpMethod.GET)) {
            if (StringUtil.isNullOrEmpty(pureUrl) || "/".equals(pureUrl)) pureUrl = "/index.html";
            FileContent fileContent = fileMappings.get(pureUrl);
            if (fileContent == null) new RequestHolder(ResourceType.json, mapper.writeValueAsBytes(Const.not_found));
            return new RequestHolder(fileContent.getResourceType(), fileContent.getBytes());
        }

        return new RequestHolder(ResourceType.json, mapper.writeValueAsBytes(Const.not_found));
    }

    private Method getMethod(HttpMethod httpMethod, String uri) {
        Iterator<Map.Entry<RequestMapping, Method>> iterator = apiMappings.entrySet().iterator();

        // Iterate over the Map using the Iterator
        while (iterator.hasNext()) {
            Map.Entry<RequestMapping, Method> entry = iterator.next();
            RequestMapping mapping = entry.getKey();
            Method method = entry.getValue();
            String[] paths = mapping.path();
            Optional<String> result = Arrays.stream(paths).filter(n -> checkIfMatch(uri, n, httpMethod)).findFirst();
            if (result.isPresent()) return method;
            String[] values = mapping.value();
            result = Arrays.stream(values).filter(n -> checkIfMatch(uri, n, httpMethod)).findFirst();
            if (result.isPresent()) return method;
        }

        return null;
    }

    private Object getMethodClassBean(Method method) {
        return context.getBean(method.getDeclaringClass());
    }

    private Object[] parseToArgs(DefaultHttpRequest request, HttpContent content, Method method) {
        Object[] args = new Object[method.getParameterCount()];
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.uri());
        Map<String, List<String>> params = queryStringDecoder.parameters();
        HttpHeaders headers = request.headers();

        Annotation[][] annotations = method.getParameterAnnotations();
        for (int x = 0; x < annotations.length; x++) {
            for (Annotation a : annotations[x]) {
                if (a instanceof RequestParam) {
                    String key = ((RequestParam) a).value();
                    Class typeClass = method.getParameterTypes()[x];
                    RequestUtils.fillRequestParam(args, typeClass, params, key, x);
                }
                if (a instanceof RequestHeader) {
                    String key = ((RequestHeader) a).value();
                    Class typeClass = method.getParameterTypes()[x];
                    RequestUtils.fillRequestHeader(args, typeClass, headers, key, x);
                }
                if (a instanceof RequestBody) {
                    Class typeClass = method.getParameterTypes()[x];
                    RequestUtils.fillRequestBody(args, typeClass, content, x, mapper);
                }
            }
        }


        return args;
    }

    public static boolean checkIfMatch(String path, String template, HttpMethod method) {
        return new PathPatternParser().parse(template).matches(PathContainer.parsePath(path));
    }





    public static void main(String[] args) {
        String a = "8880342892";
        System.out.println(a);
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Double.MAX_VALUE);
    }




}
