package com.zjamss.bendoroll.handler;

import com.zjamss.bendoroll.context.DefaultHttpContext;
import com.zjamss.bendoroll.context.HttpContext;
import com.zjamss.bendoroll.wrapper.HttpServletWrapper;

/**
 * @Program: cn.zjamss.bendoroll
 * @Description:
 * @Author: ZJamss
 * @Create: 2022-08-06 12:23
 **/
public class HandlerProxy implements Handler{

    private final Handler handler;

    public HandlerProxy(Handler handler){
        this.handler = handler;
    }

    @Override
    public void handle(HttpContext ctx) {
        handler.handle(ctx);
    }

    public HttpServletWrapper invoke(HttpContext ctx) {
        handle(ctx);
        final HttpServletWrapper httpServletWrapper = ((DefaultHttpContext) ctx).httpServletWrapper;
        return httpServletWrapper;
    }
}
