package com.zjamss.bendoroll.context;

import com.zjamss.bendoroll.exception.BaseException;
import com.zjamss.bendoroll.exception.ExceptionHandler;
import com.zjamss.bendoroll.exception.ExceptionMapper;
import com.zjamss.bendoroll.handler.HandlerMapping;
import com.zjamss.bendoroll.handler.HandlerType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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


    public static void putMapping(String path, HandlerType handlerType, HandlerMapping mapping) {
        handlerMappings.put(path + handlerType, mapping);

    }

    public static HandlerMapping matchMapping(String path, HandlerType handlerType) {

        final HandlerMapping mapping = handlerMappings.get(path + handlerType);
        return mapping;
    }

    public static void putMapper(Class<? extends Exception> clazz, ExceptionMapper mapping) {
        exceptionMappers.put(clazz, mapping);
    }

    public static ExceptionMapper matchMapper(Class<? extends Exception> clazz) {
        final ExceptionMapper exceptionMapper = exceptionMappers.get(clazz);
        return exceptionMapper;
    }

}
