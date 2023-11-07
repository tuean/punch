---
title: HttpMessageConverter
date: 2021-05-20 10:08:59
tags:
- spring
cover: https://i.loli.net/2021/05/20/FpWJ9MoRzDeKr7N.jpg
---


### HttpMessageConverter
本文我们来讨论下spring中的HttpMessageConverter。
spring版本：5.3.4

#### HttpMessageConverter
首先我们来看下interface定义
```java
/**
 * Strategy interface for converting from and to HTTP requests and responses.
 *
 * @author Arjen Poutsma
 * @author Juergen Hoeller
 * @author Rossen Stoyanchev
 * @since 3.0
 * @param <T> the converted object type
 */
public interface HttpMessageConverter<T> {

    
    boolean canRead(Class<?> clazz, @Nullable MediaType mediaType);

    boolean canWrite(Class<?> clazz, @Nullable MediaType mediaType);
    
    List<MediaType> getSupportedMediaTypes();
    
    default List<MediaType> getSupportedMediaTypes(Class<?> clazz) {
        return (canRead(clazz, null) || canWrite(clazz, null) ?
                getSupportedMediaTypes() : Collections.emptyList());
    }
    
    T read(Class<? extends T> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException;
    
    void write(T t, @Nullable MediaType contentType, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException;

}
```
根据注释及接口名称，可以看到该接口是用来转换http输入与输出；

接口提供了几个方法，可以很清晰的发现方法的作用：
* canRead  适配当前类型则可读
* canWrite 适配当前类型则可写
* getSupportedMediaTypes 返回当前类支持的媒体类型
* read 读操作
* write 写操作


#### 如何配置
开发者只需要实现该类即可提供不同类型的请求的适配。具体方式为通过实现*WebMvcConfigurer*接口进行配置。
例如笔者个人习惯使用*GsonHttpMessageConverter*，可通过以下配置进行HttpMessageConverter的配置。
```java
@Configuration
public class HttpMessageConverterConfig implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Iterator<HttpMessageConverter<?>> iterator = converters.iterator();
        while (iterator.hasNext()) {
            HttpMessageConverter converter = iterator.next();
            if (converter instanceof MappingJackson2HttpMessageConverter) iterator.remove();
            if (converter instanceof MappingJackson2XmlHttpMessageConverter) iterator.remove();
            if (converter instanceof FastJsonHttpMessageConverter) iterator.remove();
        }
        converters.add(new GsonHttpMessageConverter());
    }

}
```
以上代码去除了springboot默认添加的*Jackson*及*FastJson*提供的HttpMessageConverter。

1. springboot框架秉持开箱可用的基础理念，默认提供了以下几种消息转换器：
2. ByteArrayHttpMessageConverter – converts byte arrays
3. StringHttpMessageConverter – converts Strings
4. ResourceHttpMessageConverter – converts org.springframework.core.io.Resource for any type of octet stream
5. SourceHttpMessageConverter – converts javax.xml.transform.Source
6. FormHttpMessageConverter – converts form data to/from a MultiValueMap<String, String>.
7. Jaxb2RootElementHttpMessageConverter – converts Java objects to/from XML (added only if JAXB2 is present on the classpath)
8. MappingJackson2HttpMessageConverter – converts JSON (added only if Jackson 2 is present on the classpath)
9. MappingJacksonHttpMessageConverter – converts JSON (added only if Jackson is present on the classpath)
10. AtomFeedHttpMessageConverter – converts Atom feeds (added only if Rome is present on the classpath)
11. RssChannelHttpMessageConverter – converts RSS feeds (added only if Rome is present on the classpath)
以上默认提供的已经满足我们大部分情况下的业务使用情况。
    

#### spring源码
当我们使用默认配置或自定义配置时，spring读取请求对象操作可见*AbstractMessageConverterMethodArgumentResolver*
```java
protected <T> Object readWithMessageConverters(HttpInputMessage inputMessage, MethodParameter parameter,
			Type targetType) throws IOException, HttpMediaTypeNotSupportedException, HttpMessageNotReadableException {

		MediaType contentType;
		boolean noContentType = false;
		try {
			contentType = inputMessage.getHeaders().getContentType();
		}
		catch (InvalidMediaTypeException ex) {
			throw new HttpMediaTypeNotSupportedException(ex.getMessage());
		}
		if (contentType == null) {
			noContentType = true;
			contentType = MediaType.APPLICATION_OCTET_STREAM;
		}

		Class<?> contextClass = parameter.getContainingClass();
		Class<T> targetClass = (targetType instanceof Class ? (Class<T>) targetType : null);
		if (targetClass == null) {
			ResolvableType resolvableType = ResolvableType.forMethodParameter(parameter);
			targetClass = (Class<T>) resolvableType.resolve();
		}

		HttpMethod httpMethod = (inputMessage instanceof HttpRequest ? ((HttpRequest) inputMessage).getMethod() : null);
		Object body = NO_VALUE;

		EmptyBodyCheckingHttpInputMessage message;
		try {
			message = new EmptyBodyCheckingHttpInputMessage(inputMessage);

			for (HttpMessageConverter<?> converter : this.messageConverters) {
				Class<HttpMessageConverter<?>> converterType = (Class<HttpMessageConverter<?>>) converter.getClass();
				GenericHttpMessageConverter<?> genericConverter =
						(converter instanceof GenericHttpMessageConverter ? (GenericHttpMessageConverter<?>) converter : null);
				if (genericConverter != null ? genericConverter.canRead(targetType, contextClass, contentType) :
						(targetClass != null && converter.canRead(targetClass, contentType))) {
					if (message.hasBody()) {
						HttpInputMessage msgToUse =
								getAdvice().beforeBodyRead(message, parameter, targetType, converterType);
						body = (genericConverter != null ? genericConverter.read(targetType, contextClass, msgToUse) :
								((HttpMessageConverter<T>) converter).read(targetClass, msgToUse));
						body = getAdvice().afterBodyRead(body, msgToUse, parameter, targetType, converterType);
					}
					else {
						body = getAdvice().handleEmptyBody(null, message, parameter, targetType, converterType);
					}
					break;
				}
			}
		}
		catch (IOException ex) {
			throw new HttpMessageNotReadableException("I/O error while reading input message", ex, inputMessage);
		}

		if (body == NO_VALUE) {
			if (httpMethod == null || !SUPPORTED_METHODS.contains(httpMethod) ||
					(noContentType && !message.hasBody())) {
				return null;
			}
			throw new HttpMediaTypeNotSupportedException(contentType,
					getSupportedMediaTypes(targetClass != null ? targetClass : Object.class));
		}

		MediaType selectedContentType = contentType;
		Object theBody = body;
		LogFormatUtils.traceDebug(logger, traceOn -> {
			String formatted = LogFormatUtils.formatValue(theBody, !traceOn);
			return "Read \"" + selectedContentType + "\" to [" + formatted + "]";
		});

		return body;
	}
```
请求头中的*Content-Type*参数匹配对应的消息转换器，若不存在则使用默认的*application/octet-stream*。
通过每个实现类中的canRead方法判断当前转换器是否匹配该请求，匹配上则调用read方法读取请求的实际对象。



spring操作返回对象则是使用*AbstractMessageConverterMethodProcessor*
```java
protected <T> void writeWithMessageConverters(@Nullable T value, MethodParameter returnType,
			ServletServerHttpRequest inputMessage, ServletServerHttpResponse outputMessage)
			throws IOException, HttpMediaTypeNotAcceptableException, HttpMessageNotWritableException {

		Object body;
		Class<?> valueType;
		Type targetType;

		if (value instanceof CharSequence) {
			body = value.toString();
			valueType = String.class;
			targetType = String.class;
		}
		else {
			body = value;
			valueType = getReturnValueType(body, returnType);
			targetType = GenericTypeResolver.resolveType(getGenericType(returnType), returnType.getContainingClass());
		}

		if (isResourceType(value, returnType)) {
			outputMessage.getHeaders().set(HttpHeaders.ACCEPT_RANGES, "bytes");
			if (value != null && inputMessage.getHeaders().getFirst(HttpHeaders.RANGE) != null &&
					outputMessage.getServletResponse().getStatus() == 200) {
				Resource resource = (Resource) value;
				try {
					List<HttpRange> httpRanges = inputMessage.getHeaders().getRange();
					outputMessage.getServletResponse().setStatus(HttpStatus.PARTIAL_CONTENT.value());
					body = HttpRange.toResourceRegions(httpRanges, resource);
					valueType = body.getClass();
					targetType = RESOURCE_REGION_LIST_TYPE;
				}
				catch (IllegalArgumentException ex) {
					outputMessage.getHeaders().set(HttpHeaders.CONTENT_RANGE, "bytes */" + resource.contentLength());
					outputMessage.getServletResponse().setStatus(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE.value());
				}
			}
		}

		MediaType selectedMediaType = null;
		MediaType contentType = outputMessage.getHeaders().getContentType();
		boolean isContentTypePreset = contentType != null && contentType.isConcrete();
		if (isContentTypePreset) {
			if (logger.isDebugEnabled()) {
				logger.debug("Found 'Content-Type:" + contentType + "' in response");
			}
			selectedMediaType = contentType;
		}
		else {
			HttpServletRequest request = inputMessage.getServletRequest();
			List<MediaType> acceptableTypes = getAcceptableMediaTypes(request);
			List<MediaType> producibleTypes = getProducibleMediaTypes(request, valueType, targetType);

			if (body != null && producibleTypes.isEmpty()) {
				throw new HttpMessageNotWritableException(
						"No converter found for return value of type: " + valueType);
			}
			List<MediaType> mediaTypesToUse = new ArrayList<>();
			for (MediaType requestedType : acceptableTypes) {
				for (MediaType producibleType : producibleTypes) {
					if (requestedType.isCompatibleWith(producibleType)) {
						mediaTypesToUse.add(getMostSpecificMediaType(requestedType, producibleType));
					}
				}
			}
			if (mediaTypesToUse.isEmpty()) {
				if (body != null) {
					throw new HttpMediaTypeNotAcceptableException(producibleTypes);
				}
				if (logger.isDebugEnabled()) {
					logger.debug("No match for " + acceptableTypes + ", supported: " + producibleTypes);
				}
				return;
			}

			MediaType.sortBySpecificityAndQuality(mediaTypesToUse);

			for (MediaType mediaType : mediaTypesToUse) {
				if (mediaType.isConcrete()) {
					selectedMediaType = mediaType;
					break;
				}
				else if (mediaType.isPresentIn(ALL_APPLICATION_MEDIA_TYPES)) {
					selectedMediaType = MediaType.APPLICATION_OCTET_STREAM;
					break;
				}
			}

			if (logger.isDebugEnabled()) {
				logger.debug("Using '" + selectedMediaType + "', given " +
						acceptableTypes + " and supported " + producibleTypes);
			}
		}

		if (selectedMediaType != null) {
			selectedMediaType = selectedMediaType.removeQualityValue();
			for (HttpMessageConverter<?> converter : this.messageConverters) {
				GenericHttpMessageConverter genericConverter = (converter instanceof GenericHttpMessageConverter ?
						(GenericHttpMessageConverter<?>) converter : null);
				if (genericConverter != null ?
						((GenericHttpMessageConverter) converter).canWrite(targetType, valueType, selectedMediaType) :
						converter.canWrite(valueType, selectedMediaType)) {
					body = getAdvice().beforeBodyWrite(body, returnType, selectedMediaType,
							(Class<? extends HttpMessageConverter<?>>) converter.getClass(),
							inputMessage, outputMessage);
					if (body != null) {
						Object theBody = body;
						LogFormatUtils.traceDebug(logger, traceOn ->
								"Writing [" + LogFormatUtils.formatValue(theBody, !traceOn) + "]");
						addContentDispositionHeader(inputMessage, outputMessage);
						if (genericConverter != null) {
							genericConverter.write(body, targetType, selectedMediaType, outputMessage);
						}
						else {
							((HttpMessageConverter) converter).write(body, selectedMediaType, outputMessage);
						}
					}
					else {
						if (logger.isDebugEnabled()) {
							logger.debug("Nothing to write: null body");
						}
					}
					return;
				}
			}
		}

		if (body != null) {
			Set<MediaType> producibleMediaTypes =
					(Set<MediaType>) inputMessage.getServletRequest()
							.getAttribute(HandlerMapping.PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE);

			if (isContentTypePreset || !CollectionUtils.isEmpty(producibleMediaTypes)) {
				throw new HttpMessageNotWritableException(
						"No converter for [" + valueType + "] with preset Content-Type '" + contentType + "'");
			}
			throw new HttpMediaTypeNotAcceptableException(getSupportedMediaTypes(body.getClass()));
		}
	}
```
根据请求与代码找到可能的MediaType，根据系统配置的*List<HttpMessageConverter>*找到支持的转换器并进行转换。





#### 参考文档
[1]: https://www.baeldung.com/spring-httpmessageconverter-rest
[2]: https://my.oschina.net/lichhao/blog/172562






