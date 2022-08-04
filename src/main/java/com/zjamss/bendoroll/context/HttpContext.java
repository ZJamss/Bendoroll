package com.zjamss.bendoroll.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HttpContext {

    HttpServletRequest req();

    HttpServletResponse res();

    String queryParam(String key);

    String[] queryParams(String key);

    String formParam(String key);

    <T> T body(Class<T> clazz);


    void html(String html);

    void json(String json);

    void json(Object o);

    void ok();

    void error();

}
