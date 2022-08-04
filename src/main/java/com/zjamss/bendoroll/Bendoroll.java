package com.zjamss.bendoroll;

import com.zjamss.bendoroll.central.DispatcherServlet;
import com.zjamss.bendoroll.context.Context;
import com.zjamss.bendoroll.exception.ExceptionHandler;
import com.zjamss.bendoroll.exception.ExceptionMapper;
import com.zjamss.bendoroll.handler.Handler;
import com.zjamss.bendoroll.handler.HandlerMapping;
import com.zjamss.bendoroll.handler.HandlerType;
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

    public Bendoroll start(int port) {
        if (this.STARTED) {
            throw new RuntimeException("服务已经启动");
        }
        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new DispatcherServlet()), "/");
        try {
            server.start();
            server.join();
            this.STARTED = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public Bendoroll start() {
        return start(this.port);
    }


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

    public Bendoroll exception(Class<? extends Exception> exception, ExceptionHandler handler) {
        Context.putMapper(exception, new ExceptionMapper(handler, exception));
        return this;
    }
}
