package com.zjamss.bendoroll.handler;

import com.sun.istack.internal.NotNull;
import com.zjamss.bendoroll.context.HttpContext;

/**
 * @Program: cn.zjamss.bendoroll
 * @Description:
 * @Author: ZJamss
 * @Create: 2022-08-04 08:41
 **/
@FunctionalInterface
public interface Handler {

    void handle(@NotNull HttpContext ctx);
}
