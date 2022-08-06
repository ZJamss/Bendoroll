package com.zjamss.bendoroll.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public interface HttpContext {

    HttpServletRequest req();

    HttpServletResponse res();

    String param(String key);

    String[] params(String key);

    <T> T body(Class<T> clazz);


    void html(String html);

    void json(String json);

    Map<String, Object> ok(int code, String msg);

    Map<String, Object> ok();

    Map<String, Object> error();

    Map<String, Object> error(int code,String msg);

//    void file(String path) throws IOException;

}
