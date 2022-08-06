package com.zjamss.bendoroll.wrapper;

/**
 * @Program: cn.zjamss.bendoroll
 * @Description:
 * @Author: ZJamss
 * @Create: 2022-08-05 10:19
 **/
public enum ContentType {

   TEXT_HTML("text/html;charset=utf-8"),
    APPLICATION_JSON("application/json;charset=utf-8");

    private final String value;

    ContentType(java.lang.String value) {
        this.value = value;
    }

    public java.lang.String getValue() {
        return value;
    }
}
