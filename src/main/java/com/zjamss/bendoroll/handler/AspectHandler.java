package com.zjamss.bendoroll.handler;

import com.zjamss.bendoroll.context.HttpContext;

/**
 * @Program: cn.zjamss.bendoroll
 * @Description:
 * @Author: ZJamss
 * @Create: 2022-08-06 15:10
 **/
@FunctionalInterface
public interface AspectHandler {
    boolean handle(HttpContext ctx);
}
