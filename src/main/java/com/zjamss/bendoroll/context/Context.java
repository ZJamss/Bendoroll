package com.zjamss.bendoroll.context;

import com.zjamss.bendoroll.http.handler.impl.ExceptionMapper;
import com.zjamss.bendoroll.http.handler.AspectHandler;
import com.zjamss.bendoroll.http.handler.impl.HandlerMapping;
import com.zjamss.bendoroll.constant.HandlerType;
import com.zjamss.bendoroll.constant.Lifecycle;

import java.util.HashMap;
import java.util.Map;

/**
 * @Program: cn.zjamss.bendoroll
 * @Description:
 * @Author: ZJamss
 * @Create: 2022-08-04 09:16
 **/
public class Context {

    private static final Map<String, HandlerMapping> handlerMappings = new HashMap<>();
    private static final Map<Class<? extends Exception>, ExceptionMapper> exceptionMappers = new HashMap<>();

    private static final Map<String, AspectHandler> aspects = new HashMap<>();

    static {
        init();
    }

    protected static void init() {
 /*       putMapping(
                "/public",
                HandlerType.REQUEST,
                new HandlerMapping(ctx -> {
                    ((DefaultHttpContext) ctx).httpServletWrapper.setContentType(ContentType.TEXT_HTML);
                },
                        "/public", HandlerType.REQUEST)
        );*/
    }

    public static void putMapping(String path, HandlerType handlerType, HandlerMapping mapping) {
        if (handlerType.equals(HandlerType.REQUEST)) {
            handlerMappings.put(path + HandlerType.GET, mapping);
            handlerMappings.put(path + HandlerType.POST, mapping);
            handlerMappings.put(path + HandlerType.PUT, mapping);
            handlerMappings.put(path + HandlerType.DELETE, mapping);
        } else handlerMappings.put(path + handlerType, mapping);
    }

    public static HandlerMapping matchMapping(String path, HandlerType handlerType) {
        HandlerMapping mapping = handlerMappings.get(path + handlerType);
 /*       if (mapping == null) {
            Pattern pattern = Pattern.compile("\\/.*(?=\\/)");
            Matcher matcher = pattern.matcher(path);
            if (matcher.find())
                mapping = handlerMappings.get(matcher.group() + handlerType);
        }*/
        return mapping;
    }

    public static void putMapper(Class<? extends Exception> clazz, ExceptionMapper mapping) {
        exceptionMappers.put(clazz, mapping);
    }

    public static ExceptionMapper matchMapper(Class<? extends Exception> clazz) {
        final ExceptionMapper exceptionMapper = exceptionMappers.get(clazz);
        return exceptionMapper;
    }

    public static void putAspect(Lifecycle lifecycle, String path, AspectHandler handler) {
        aspects.put(path + lifecycle, handler);
    }

    public static AspectHandler matchInterceptor(Lifecycle lifecycle, String path) {
        AspectHandler aspect = aspects.get(path + lifecycle);
        if(aspect == null){
            aspect = ctx -> true;
        }
        return aspect;
    }


}
