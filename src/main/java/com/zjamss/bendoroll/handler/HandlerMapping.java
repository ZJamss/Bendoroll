package com.zjamss.bendoroll.handler;

/**
 * @Program: cn.zjamss.bendoroll
 * @Description:
 * @Author: ZJamss
 * @Create: 2022-08-04 09:04
 **/
public class HandlerMapping {
    private Handler handler;
    private String path;
    private HandlerType handlerType;

    public HandlerMapping(Handler handler, String path, HandlerType handlerType) {
        this.handler = handler;
        this.path = path;
        this.handlerType = handlerType;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public HandlerType getHandlerType() {
        return handlerType;
    }

    public void setHandlerType(HandlerType handlerType) {
        this.handlerType = handlerType;
    }
}
