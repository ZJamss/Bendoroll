package com.zjamss.bendoroll.http.wrapper;

import com.google.gson.Gson;
import com.zjamss.bendoroll.constant.ContentType;

import javax.servlet.ServletOutputStream;
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
            //final response body
            if (contentType == ContentType.APPLICATION_JSON && !(data instanceof String))
                data = new Gson().toJson(data).getBytes(StandardCharsets.UTF_8);

            httpServletResponse.setContentType(contentType.getValue());
            httpServletResponse.setCharacterEncoding("UTF-8");
            //改用io流返回文件，而不是getWriter().print(),print只能转换字符,无法处理字节
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            outputStream.write((byte[]) data);
        } catch (IOException e) {
            throw new RuntimeException("Wrapper Error");
        }
    }

    public HttpServletWrapper(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, ContentType contentType, String data) {
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

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
