package com.zjamss.bendoroll.exception;

import com.zjamss.bendoroll.context.Context;
import com.zjamss.bendoroll.context.HttpContext;

/**
 * @Program: cn.zjamss.bendoroll
 * @Description:
 * @Author: ZJamss
 * @Create: 2022-08-04 19:31
 **/
@FunctionalInterface
public interface ExceptionHandler {
    void handler(Exception e, HttpContext ctx);
}
