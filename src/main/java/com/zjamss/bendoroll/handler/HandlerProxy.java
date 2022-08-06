package com.zjamss.bendoroll.handler;

import com.zjamss.bendoroll.context.Context;
import com.zjamss.bendoroll.context.DefaultHttpContext;
import com.zjamss.bendoroll.context.HttpContext;
import com.zjamss.bendoroll.wrapper.HttpServletWrapper;
import com.zjamss.bendoroll.wrapper.Lifecycle;

/**
 * @Program: cn.zjamss.bendoroll
 * @Description:
 * @Author: ZJamss
 * @Create: 2022-08-06 12:23
 **/
public class HandlerProxy implements Handler {

    private final Handler handler;

    public HandlerProxy(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void handle(HttpContext ctx) throws Exception {
        if (Context.matchInterceptor(Lifecycle.AROUND, ctx.req().getRequestURI()).handle(ctx))
            if (Context.matchInterceptor(Lifecycle.BEFORE, ctx.req().getRequestURI()).handle(ctx))
                try {
                    handler.handle(ctx);
                    Context.matchInterceptor(Lifecycle.AFTER, ctx.req().getRequestURI()).handle(ctx);
                    Context.matchInterceptor(Lifecycle.AROUND, ctx.req().getRequestURI()).handle(ctx);
                } catch (Exception e) {
                    Context.matchInterceptor(Lifecycle.EXCEPTION, ctx.req().getRequestURI()).handle(ctx);
                    throw e;
                }
    }

    public HttpServletWrapper invoke(HttpContext ctx) throws Exception {
        handle(ctx);
        final HttpServletWrapper httpServletWrapper = ((DefaultHttpContext) ctx).httpServletWrapper;
        return httpServletWrapper;
    }
}
