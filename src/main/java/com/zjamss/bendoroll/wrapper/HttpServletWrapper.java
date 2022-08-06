package com.zjamss.bendoroll.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Program: cn.zjamss.bendoroll
 * @Description:
 * @Author: ZJamss
 * @Create: 2022-08-05 10:19
 **/
public class HttpServletWrapper {
    private HttpServletResponse httpServletResponse;

    private HttpServletRequest httpServletRequest;
    private ContentType ContentType;
    private Object data;

    public void wrapper() {
        try {
            httpServletResponse.getWriter().print(data);
            httpServletResponse.setContentType(ContentType.getValue());
            httpServletResponse.setCharacterEncoding("UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpServletWrapper(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, com.zjamss.bendoroll.wrapper.ContentType contentType, String data) {
        this.httpServletResponse = httpServletResponse;
        this.httpServletRequest = httpServletRequest;
        ContentType = contentType;
        this.data = data;
    }

    public HttpServletResponse getHttpServletResponse() {
        return httpServletResponse;
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public com.zjamss.bendoroll.wrapper.ContentType getContentType() {
        return ContentType;
    }

    public void setContentType(com.zjamss.bendoroll.wrapper.ContentType contentType) {
        ContentType = contentType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
