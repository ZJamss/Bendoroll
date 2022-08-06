package com.zjamss.bendoroll.wrapper;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Program: cn.zjamss.bendoroll
 * @Description:
 * @Author: ZJamss
 * @Create: 2022-08-05 10:19
 **/
public class HttpServletWrapper {
    private HttpServletResponse httpServletResponse;

    private HttpServletRequest httpServletRequest;
    private ContentType contentType;
    private Object data;

    public void wrapper() {
        try {
            httpServletResponse.setContentType(contentType.getValue());
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.getWriter().print(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpServletWrapper(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, com.zjamss.bendoroll.wrapper.ContentType contentType, String data) {
        this.httpServletResponse = httpServletResponse;
        this.httpServletRequest = httpServletRequest;
        this.contentType = contentType;
        this.data = data;
    }

    public HttpServletResponse getHttpServletResponse() {
        return httpServletResponse;
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public com.zjamss.bendoroll.wrapper.ContentType getContentType() {
        return contentType;
    }

    public void setContentType(com.zjamss.bendoroll.wrapper.ContentType contentType) {
        this.contentType = contentType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        if (contentType == ContentType.APPLICATION_JSON && !(data instanceof String))
            data = new Gson().toJson(data);
        this.data = data;
    }
}
