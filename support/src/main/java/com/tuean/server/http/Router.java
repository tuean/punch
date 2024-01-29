package com.tuean.server.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.tuean.annotation.*;
import com.tuean.cache.ResourceCache;
import com.tuean.consts.Const;
import com.tuean.consts.ResourceType;
import com.tuean.entity.FileContent;
import com.tuean.entity.RequestHolder;
import com.tuean.exception.DuplicatePathException;
import com.tuean.helper.context.Bean;
import com.tuean.helper.context.ProjectContext;
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
import static com.tuean.util.RequestUtils.getMathedApiJson;


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

    private ProjectContext projectContext;

    public Router(ProjectContext projectContext) {
        this.projectContext = projectContext;
    }

    public void init(String packageName, ResourceCache cache) {
        Set<Class<?>> annotatedClasses = findAnnotatedClasses(packageName, Api.class);
        registerRequestMappings(annotatedClasses);
        registerFiles(cache);
    }

    private void registerRequestMappings(Set<Class<?>> annotatedClasses) {
        // Print the names of the annotated classes
        for (Class<?> annotatedClass : annotatedClasses) {
            try {
                Bean bean = projectContext.getBeanByClass(annotatedClass);
                Method[] methods = annotatedClass.getMethods();
                Arrays.stream(methods).forEach(method -> {
                    ApiJson apiJson = method.getAnnotation(ApiJson.class);
                    if (apiJson == null) return;
                    String path = apiJson.path();
                    apiMappings.put(apiJson, method);
                    logger.info("register url mapping: {}", apiJson.path());
                });
            } catch (Exception var) {
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
            String path = mapping.path();
            if (urls.contains(path)) throw new DuplicatePathException();
            urls.add(path);
        });
    }

    public RequestHolder doRequest(HttpRequest request, HttpContent content) throws JsonProcessingException, InvocationTargetException, IllegalAccessException {
        DefaultHttpRequest fullHttpRequest = (DefaultHttpRequest) request;
        String contentType = request.headers().get(HttpHeaderNames.CONTENT_TYPE);
        HttpMethod httpMethod = fullHttpRequest.method();
        String uri = request.uri(), pureUrl = Util.pureUrl(uri);
        ApiJson matched = getMathedApiJson(apiMappings.keySet(), httpMethod, pureUrl);

        boolean isJsonApi = matched != null && contentType != null && contentType.startsWith("application/json;");
        boolean isFile = fileMappings.get(pureUrl) != null && httpMethod.equals(HttpMethod.GET);

        if (isJsonApi) {
            Method method = getMethod(httpMethod, pureUrl);
            if (method == null) return new RequestHolder(ResourceType.json, mapper.writeValueAsBytes(Const.not_found));

            Object[] args = parseToArgs(fullHttpRequest, content, method);
            Bean methodBean = getMethodClassBean(method);
            Object result = method.invoke(methodBean.getInstance(), args);
            return result == null ? null : new RequestHolder(ResourceType.json, mapper.writeValueAsBytes(result));
        } else if (true) {
            if (StringUtil.isNullOrEmpty(pureUrl) || "/".equals(pureUrl)) pureUrl = "/index.html";
            FileContent fileContent = fileMappings.get(pureUrl);
            if (fileContent != null) {
                return new RequestHolder(fileContent.getResourceType(), fileContent.getBytes());
            } else {
                fileContent = fileMappings.get("/index.html");
                return new RequestHolder(fileContent.getResourceType(), fileContent.getBytes());
            }

//            return new RequestHolder(ResourceType.json, mapper.writeValueAsBytes(Const.not_found));
        }

        return new RequestHolder(ResourceType.json, mapper.writeValueAsBytes(Const.not_found));
    }


    private Method getMethod(HttpMethod httpMethod, String uri) {
        Iterator<Map.Entry<ApiJson, Method>> iterator = apiMappings.entrySet().iterator();

        // Iterate over the Map using the Iterator
        while (iterator.hasNext()) {
            Map.Entry<ApiJson, Method> entry = iterator.next();
            ApiJson mapping = entry.getKey();
            Method method = entry.getValue();
            String paths = mapping.path();
            if (checkIfMatch(uri, paths, httpMethod)) return method;
        }

        return null;
    }

    private Bean getMethodClassBean(Method method) {
        return ProjectContext.getBeanByClass(method.getDeclaringClass());
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
//        return new PathPatternParser().parse(template).matches(PathContainer.parsePath(path));
        return path.equals(template);
    }





    public static void main(String[] args) {
        String a = "8880342892";
        System.out.println(a);
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Double.MAX_VALUE);
    }




}
