package com.zjamss.bendoroll.context;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Program: cn.zjamss.bendoroll
 * @Description:
 * @Author: ZJamss
 * @Create: 2022-08-04 08:52
 **/
public class DefaultHttpContext implements HttpContext {

    private HttpServletRequest req;

    private HttpServletResponse res;


    public DefaultHttpContext(HttpServletRequest req, HttpServletResponse res) {
        this.req = req;
        this.res = res;
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
        try {
            this.res.setCharacterEncoding("UTF-8");
            this.res.setContentType("text/html;charset=utf-8");
            this.res.getWriter().print(html);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String queryParam(String key) {
        return this.req.getParameter(key);
    }

    @Override
    public String[] queryParams(String key) {
        return this.req.getParameterValues(key);
    }



    @Override
    public void json(String json) {
        try {
            this.res.setCharacterEncoding("UTF-8");
            this.res.setContentType("application/json;charset=utf-8");
            this.res.getWriter().print(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void json(Object o) {
        json(new Gson().toJson(o));
    }

    @Override
    public String formParam(String key) {
//        if(this.req.getContentType().contains("application/x-www-form-urlencoded")){
//
//        }

        return "";
    }

    @Override
    public <T> T body(Class<T> clazz) {
        return null;
    }

    @Override
    public void ok() {

    }

    @Override
    public void error() {

    }
}
