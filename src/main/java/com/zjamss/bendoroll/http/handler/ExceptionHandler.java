package com.zjamss.bendoroll.http.handler;

import com.zjamss.bendoroll.http.context.HttpContext;

/**
 * @Program: cn.zjamss.bendoroll
 * @Description:
 * @Author: ZJamss
 * @Create: 2022-08-04 19:31
 **/
@FunctionalInterface
public interface ExceptionHandler {
    void handle(Exception e, HttpContext ctx);
}
