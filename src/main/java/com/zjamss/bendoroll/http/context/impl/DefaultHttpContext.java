package com.zjamss.bendoroll.http.context.impl;

import com.google.gson.Gson;
import com.zjamss.bendoroll.Bendoroll;
import com.zjamss.bendoroll.http.context.HttpContext;
import com.zjamss.bendoroll.constant.ContentType;
import com.zjamss.bendoroll.http.wrapper.HttpServletWrapper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
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
        httpServletWrapper.setData(html.getBytes(StandardCharsets.UTF_8));
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
        httpServletWrapper.setData(json.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public <T> T body(Class<T> clazz) {
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setHeaderEncoding("UTF-8");
            boolean multipartContent = ServletFileUpload
                    .isMultipartContent(req);
            //if not file
            if (!multipartContent) {
                int len = req.getContentLength();
                ServletInputStream body = req.getInputStream();
                byte[] buffer = new byte[len];
                body.read(buffer, 0, len);
                //return object
                final T t = new Gson().fromJson(new String(buffer, StandardCharsets.UTF_8), clazz);
                return t;
            }
            //file
            List<FileItem> parseRequest = upload.parseRequest(req);
            if (parseRequest != null) {
                for (FileItem fileItem : parseRequest) {
                    if (!fileItem.isFormField()) {
                        //return file inputStream
                        return (T) fileItem.getInputStream();
                    }
                }
            }
            throw new RuntimeException("No file uploaded");
        } catch (Exception e) {
            e.printStackTrace();
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
        //set response data
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

    @Override
    public void file(String fileName) {
        File file = new File(Bendoroll.RESOURCE_PATH + Bendoroll.FOLDER_NAME + "/" + fileName);
        if (file.exists()) {
            try {
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                OutputStream os = null;
                String fileSuffix = fileName.split("\\.")[1];
                res.reset();
                res.setCharacterEncoding("utf-8");
                //match corresponding content-type
                httpServletWrapper.setContentType(ContentType.getFilContentType(fileSuffix));
                //return file
                res.setHeader("Content-Disposition", "attachment; filename=" + fileName);
                byte[] b = new byte[bis.available()];
                bis.read(b);
                httpServletWrapper.setData(b);
                bis.close();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        } else {
            throw new RuntimeException("File" + file.getAbsolutePath() + "is not exist");
        }
    }
}
