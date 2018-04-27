package com.gk.scheduler.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import org.bson.types.ObjectId;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.util.Date;

public class MessageConverter extends AbstractHttpMessageConverter<Object> {

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    public MessageConverter() {
        super(MediaType.APPLICATION_JSON);
    }

    @Override
    protected Object readInternal(Class<?> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return this.supports(clazz) && this.canWrite(mediaType);
    }

    ValueFilter filter = (obj, s, v) -> {
        if (v instanceof ObjectId)  {
            return v.toString();
        }
        if (v instanceof Date) {
            return ((Date) v).getTime();
        }
        return v;
    };

    @Override
    protected void writeInternal(Object o, HttpOutputMessage httpOutputMessage) throws IOException {
        FileCopyUtils.copy(JSON.toJSONString(o, filter, SerializerFeature.DisableCircularReferenceDetect).getBytes(), httpOutputMessage.getBody());
    }

}
