package com.zjamss.bendoroll.central;

import com.zjamss.bendoroll.context.Context;
import com.zjamss.bendoroll.context.DefaultHttpContext;
import com.zjamss.bendoroll.exception.ExceptionMapper;
import com.zjamss.bendoroll.handler.Handler;
import com.zjamss.bendoroll.handler.HandlerMapping;
import com.zjamss.bendoroll.handler.HandlerProxy;
import com.zjamss.bendoroll.handler.HandlerType;
import com.zjamss.bendoroll.wrapper.ContentType;
import com.zjamss.bendoroll.wrapper.HttpServletWrapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Program: cn.zjamss.bendoroll
 * @Description:
 * @Author: ZJamss
 * @Create: 2022-08-03 21:07
 **/

public class DispatcherServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String handlerType = req.getMethod();
        String path = req.getRequestURI();
        final HandlerMapping mapping = Context.matchMapping(path, HandlerType.valueOf(handlerType));
        if (mapping != null) {
            final Handler handler = mapping.getHandler();
            doProcess(req, resp, handler);
        } else {
            throw new RuntimeException("404");
        }
    }

    void doProcess(HttpServletRequest req, HttpServletResponse res, Handler handler) {
        final HandlerProxy proxy = new HandlerProxy(handler);
        HttpServletWrapper wrapper = new HttpServletWrapper(res,req, ContentType.APPLICATION_JSON,"");
        try {
            wrapper = proxy.invoke(new DefaultHttpContext(wrapper));
        } catch (Exception e) {
            final ExceptionMapper exceptionMapper = Context.matchMapper(e.getClass());
            if (exceptionMapper != null) {
                  exceptionMapper.getExceptionHandler().handle(e, new DefaultHttpContext(wrapper));
            } else {
                throw e;
            }
        }
        wrapper.wrapper();
    }

}
