package com.zjamss.bendoroll;

import com.zjamss.bendoroll.http.servlet.DispatcherServlet;
import com.zjamss.bendoroll.context.Context;
import com.zjamss.bendoroll.http.handler.ExceptionHandler;
import com.zjamss.bendoroll.http.handler.impl.ExceptionMapper;
import com.zjamss.bendoroll.http.handler.AspectHandler;
import com.zjamss.bendoroll.http.handler.Handler;
import com.zjamss.bendoroll.http.handler.impl.HandlerMapping;
import com.zjamss.bendoroll.constant.HandlerType;
import com.zjamss.bendoroll.constant.Lifecycle;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * @Program: cn.zjamss.bendoroll
 * @Description:
 * @Author: ZJamss
 * @Create: 2022-08-03 14:14
 **/
public class Bendoroll {

    private static Bendoroll instance;

    private boolean STARTED = false;
    private int port = 8080;

    private static final DispatcherServlet dispatcherServlet = new DispatcherServlet();


    protected Bendoroll(int port) {
        this.port = port;
    }

    public static Bendoroll create(int port) {
        if (instance == null) {
            Bendoroll bendoroll = new Bendoroll(port);
            return bendoroll;
        } else {
            return instance;
        }
    }

    public static Bendoroll create() {
        return create(8080);
    }

    /**
     * @return
     * @Author ZJamss
     * @Description Start Application
     * @Date 2022/8/6 16:19
     * @Param run-port
     **/
    public Bendoroll start(int port) {
        if (this.STARTED) {
            throw new RuntimeException("服务已经启动");
        }
        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(dispatcherServlet), "/");
        try {
            server.start();
            server.join();
            this.STARTED = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    /**
     * Start Application
     **/
    public Bendoroll start() {
        return start(this.port);
    }


    /**
     * build a api
     **/
    public Bendoroll addHandler(HandlerType handlerType, String path, Handler handler) {
        Context.putMapping(path, handlerType, new HandlerMapping(handler, path, handlerType));
        return this;
    }

    public Bendoroll get(String path, Handler handler) {
        return this.addHandler(HandlerType.GET, path, handler);
    }

    public Bendoroll post(String path, Handler handler) {
        return this.addHandler(HandlerType.POST, path, handler);
    }

    public Bendoroll put(String path, Handler handler) {
        return this.addHandler(HandlerType.PUT, path, handler);
    }

    public Bendoroll delete(String path, Handler handler) {
        return this.addHandler(HandlerType.DELETE, path, handler);
    }

    /**
     * build a exceptionHandler
     **/
    public Bendoroll exception(Class<? extends Exception> exception, ExceptionHandler handler) {
        Context.putMapper(exception, new ExceptionMapper(handler, exception));
        return this;
    }

    /**
     * build a handler to handle the lifecycle of api
     **/
    public Bendoroll aspect(Lifecycle lifecycle, String path, AspectHandler handler) {
        Context.putAspect(lifecycle, path, handler);
        return this;
    }
}
