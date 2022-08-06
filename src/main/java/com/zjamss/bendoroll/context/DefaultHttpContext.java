package com.zjamss.bendoroll.context;

import com.google.gson.Gson;
import com.zjamss.bendoroll.wrapper.ContentType;
import com.zjamss.bendoroll.wrapper.HttpServletWrapper;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @Program: cn.zjamss.bendoroll
 * @Description:
 * @Author: ZJamss
 * @Create: 2022-08-04 08:52
 **/
public class DefaultHttpContext implements HttpContext {

    private final HttpServletRequest req;
    private final HttpServletResponse res;
    public final HttpServletWrapper httpServletWrapper;
    private final Map<String, Object> result = new HashMap<>();


    public DefaultHttpContext(HttpServletWrapper httpServletWrapper) {
        this.req = httpServletWrapper.getHttpServletRequest();
        this.res = httpServletWrapper.getHttpServletResponse();
        this.httpServletWrapper = httpServletWrapper;
    }

    @Override
    public HttpServletRequest req() {
        return this.req;
    }

    @Override
    public HttpServletResponse res() {
        return this.res;
    }

    @Override
    public void html(String html) {
        httpServletWrapper.setContentType(ContentType.TEXT_HTML);
        httpServletWrapper.setData(html);
    }

    @Override
    public String param(String key) {
        return req.getParameter(key);
    }

    @Override
    public String[] params(String key) {
        return req.getParameterValues(key);
    }

    @Override
    public void json(String json) {
        httpServletWrapper.setData(json);
    }

    @Override
    public <T> T body(Class<T> clazz) {
        try {
            int len = req.getContentLength();
            ServletInputStream body = req.getInputStream();
            byte[] buffer = new byte[len];
            body.read(buffer, 0, len);
            final T t = new Gson().fromJson(new String(buffer, StandardCharsets.UTF_8), clazz);
            return t;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> ok(int code, String msg) {
        result.put("code", code);
        result.put("msg", msg);
        httpServletWrapper.setData(result);
        return result;
    }

    @Override
    public Map<String, Object> ok() {
        result.put("code", 200);
        result.put("msg", "success");
        httpServletWrapper.setData(result);
        return result;
    }

    @Override
    public Map<String, Object> error() {
        result.put("code", 400);
        result.put("msg", "error");
        httpServletWrapper.setData(result);
        return result;
    }

    @Override
    public Map<String, Object> error(int code, String msg) {
        result.put("code", code);
        result.put("msg", msg);
        httpServletWrapper.setData(result);
        return result;
    }

}
