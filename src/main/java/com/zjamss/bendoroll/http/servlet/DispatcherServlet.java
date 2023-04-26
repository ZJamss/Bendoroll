package com.zjamss.bendoroll.http.servlet;

import com.sun.javafx.binding.StringFormatter;
import com.zjamss.bendoroll.Bendoroll;
import com.zjamss.bendoroll.context.Context;
import com.zjamss.bendoroll.http.context.impl.DefaultHttpContext;
import com.zjamss.bendoroll.http.context.HttpContext;
import com.zjamss.bendoroll.http.handler.impl.ExceptionMapper;
import com.zjamss.bendoroll.http.handler.Handler;
import com.zjamss.bendoroll.http.handler.impl.HandlerMapping;
import com.zjamss.bendoroll.http.handler.proxy.HandlerProxy;
import com.zjamss.bendoroll.constant.HandlerType;
import com.zjamss.bendoroll.constant.ContentType;
import com.zjamss.bendoroll.http.wrapper.HttpServletWrapper;
import javafx.beans.binding.StringExpression;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @Program: cn.zjamss.bendoroll
 * @Description: 分发Servlet
 * @Author: ZJamss
 * @Create: 2022-08-03 21:07
 **/

public class DispatcherServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        String handlerType = req.getMethod();
        String path = req.getRequestURI();
        if (path.contains(".") && Bendoroll.FILE_ENABLE) {
            try {
                doFile(path, req, resp);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("File not exist");
            }
        } else {
            final HandlerMapping mapping = Context.matchMapping(path, HandlerType.valueOf(handlerType));
            try {
                //process request
                final Handler handler = mapping.getHandler();
                doProcess(req, resp, handler);
            } catch (NullPointerException e) {
                //access nonexistent path
                resp.setStatus(404);
                StringExpression format = StringFormatter.format("%s %s %s", handlerType, path, "404");
                new RuntimeException(format.getValue()).printStackTrace();
            }
        }
    }

    void doFile(String path, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        File file = new File(Bendoroll.RESOURCE_PATH + Bendoroll.FOLDER_NAME + path);
        if (file.exists()) {
            FileInputStream inputStream = new FileInputStream(file);
            BufferedInputStream bis = null;
            OutputStream os = null;
            String fileSuffix = path.split("\\.")[1];
            resp.reset();
            resp.setCharacterEncoding("utf-8");
            //match corresponding content-type
            resp.setContentType(ContentType.getFilContentType(fileSuffix).getValue());
//            resp.setHeader("Content-Disposition", "attachment; filename=" + path.replace("/",""));
            bis = new BufferedInputStream(inputStream);
            byte[] b = new byte[bis.available() + 1000];
            int i = 0;
            os = resp.getOutputStream();
            while ((i = bis.read(b)) != -1) {
                os.write(b, 0, i);
            }
            inputStream.close();
            bis.close();
            os.flush();
            os.close();
        } else {
            throw new RuntimeException("File "+file.getAbsolutePath()+" is not exist");
        }
    }

    void doProcess(HttpServletRequest req, HttpServletResponse res, Handler handler) {
        //get static-proxy object
        final HandlerProxy proxy = new HandlerProxy(handler);
        HttpServletWrapper wrapper = new HttpServletWrapper(res, req, ContentType.APPLICATION_JSON, "");
        try {
            //get processed wrapper
            wrapper = proxy.invoke(new DefaultHttpContext(wrapper));
        } catch (Exception e) {
            //handle exception
            final ExceptionMapper exceptionMapper = Context.matchMapper(e.getClass());
            if (exceptionMapper != null) {
                final DefaultHttpContext ctx = new DefaultHttpContext(wrapper);
                exceptionMapper.getExceptionHandler().handle(e, ctx);
                wrapper = getWrapper(ctx);
            } else {
                e.printStackTrace();
            }
        }
        wrapper.wrapper();
    }


    private HttpServletWrapper getWrapper(HttpContext ctx) {
        return ((DefaultHttpContext) ctx).httpServletWrapper;
    }


}
