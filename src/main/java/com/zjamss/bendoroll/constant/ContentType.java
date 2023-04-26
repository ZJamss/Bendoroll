package com.zjamss.bendoroll.constant;

/**
 * @Program: cn.zjamss.bendoroll
 * @Description: 内容格式控制
 * @Author: ZJamss
 * @Create: 2022-08-05 10:19
 **/
public enum ContentType {

    TEXT_HTML("text/html;charset=utf-8"),
    TEXT_CSS("text/css;charset=utf-8"),
    APPLICATION_JSON("application/json;charset=utf-8"),
    APPLICATION_JS("application/javascript;charset=utf-8"),
    PDF("application/pdf;charset=utf-8"),
    DOC("application/msword;charset=utf-8"),
    XLS("application/octet-stream;charset=ISO8859-1"),
    TEXT("text/plain;charset=utf-8");

    private final String value;

    ContentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ContentType getFilContentType(String suffix) {
        switch (suffix) {
            case "pdf":
                return PDF;
            case "json":
                return APPLICATION_JSON;
            case "doc":
                return DOC;
            case "xls":
                return XLS;
            case "html":
                return TEXT_HTML;
            case "css":
                return TEXT_CSS;
            case "js":
                return APPLICATION_JS;
            default:
                return TEXT;
        }
    }
}
