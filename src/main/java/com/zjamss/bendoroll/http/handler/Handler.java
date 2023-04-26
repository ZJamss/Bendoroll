package com.zjamss.bendoroll.http.handler;

import com.zjamss.bendoroll.http.context.HttpContext;

/**
 * @Program: cn.zjamss.bendoroll
 * @Description:
 * @Author: ZJamss
 * @Create: 2022-08-04 08:41
 **/
@FunctionalInterface
public interface Handler {

    void handle( HttpContext ctx) throws Exception;
}
