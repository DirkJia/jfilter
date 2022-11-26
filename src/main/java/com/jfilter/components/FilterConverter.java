package com.jfilter.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfilter.converter.FilterClassWrapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

public class FilterConverter extends AbstractHttpMessageConverter<Object> {
    private FilterConfiguration filterConfiguration;

    public FilterConverter(FilterConfiguration filterConfiguration) {
        this.filterConfiguration = filterConfiguration;
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return filterConfiguration.supportedMediaTypes();
    }

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return filterConfiguration.isEnabled() &&
                (filterConfiguration.supportedMediaTypes().contains(mediaType) || Objects.isNull(mediaType));
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws HttpMessageNotReadableException {
        return null;
    }

    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        MediaType contentType = outputMessage.getHeaders().getContentType();


        //If object is FilterClassWrapper try to serialize object using filters(if configured)
        if (object instanceof FilterClassWrapper) {
            FilterClassWrapper wrapper = (FilterClassWrapper) object;

            //Retrieving ObjectMapper from ObjectMapperCache
            ObjectMapper objectMapper = filterConfiguration.getObjectMapperCache()
                    .findObjectMapper(wrapper.getMethodParameterDetails());

            //Serialize object with ObjectMapper
            objectMapper.writeValue(outputMessage.getBody(), wrapper.getObject());
        } else if(object instanceof String){
            HttpHeaders headers = outputMessage.getHeaders();
            Charset charset = this.getContentTypeCharset(headers.getContentType());
            StreamUtils.copy(String.valueOf(object), charset, outputMessage.getBody());
        } else {
            //Otherwise try to serialize object without filters by default ObjectMapper from filterConfiguration
            ObjectMapper objectMapper = filterConfiguration.getMapper(contentType);
            objectMapper.writeValue(outputMessage.getBody(), object);
        }
    }

    private Charset getContentTypeCharset(MediaType contentType) {
        Charset charset;
        if (contentType != null) {
            charset = contentType.getCharset();
            if (charset != null) {
                return charset;
            }

            if (contentType.isCompatibleWith(MediaType.APPLICATION_JSON)) {
                return StandardCharsets.UTF_8;
            }
        }

        charset = this.getDefaultCharset();
        Assert.state(charset != null, "No default charset");
        return charset;
    }
}
