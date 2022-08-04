package com.zjamss.bendoroll.exception;

/**
 * @Program: cn.zjamss.bendoroll
 * @Description:
 * @Author: ZJamss
 * @Create: 2022-08-04 19:33
 **/
public class ExceptionMapper {
    private ExceptionHandler exceptionHandler;
    private Class<? extends Exception> exception;

    public ExceptionMapper(ExceptionHandler exceptionHandler, Class<? extends Exception> exception) {
        this.exceptionHandler = exceptionHandler;
        this.exception = exception;
    }

    public ExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }

    public Class<? extends Exception> getException() {
        return exception;
    }
}
