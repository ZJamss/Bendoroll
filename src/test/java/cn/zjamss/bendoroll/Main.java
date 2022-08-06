package cn.zjamss.bendoroll;

import com.sun.javafx.binding.StringFormatter;
import com.zjamss.bendoroll.Bendoroll;
import com.zjamss.bendoroll.context.HttpContext;
import com.zjamss.bendoroll.exception.BaseException;
import com.zjamss.bendoroll.handler.Handler;
import com.zjamss.bendoroll.handler.HandlerType;
import com.zjamss.bendoroll.wrapper.HttpServletWrapper;
import com.zjamss.bendoroll.wrapper.Lifecycle;
import org.eclipse.jetty.server.Authentication;

import java.util.ArrayList;
import java.util.List;

/**
 * @Program: cn.zjamss.bendoroll
 * @Description:
 * @Author: ZJamss
 * @Create: 2022-08-04 09:32
 **/
public class Main {
    public static void main(String[] args) {
        Bendoroll app = Bendoroll.create();
        app.get("/", ctx -> {
            ctx.ok().put("person", new Person("ZJamss", 19, "Dont have delusion"));
            throw new BaseException("123");
        });
        app.interceptor(Lifecycle.AROUND, "/", ctx -> {
            ctx.ok().put("around","around");
        });
        app.interceptor(Lifecycle.BEFORE, "/", ctx -> {
            ctx.ok().put("before","before");
        });
        app.interceptor(Lifecycle.AFTER, "/", ctx -> {
            ctx.ok().put("after","after");
        });
        app.interceptor(Lifecycle.EXCEPTION, "/", ctx -> {
            ctx.ok().put("exception","exception");
        });
        app.exception(BaseException.class, (e, ctx) -> {
            ctx.html("error");
        });

        app.start();
    }

    static class Person {
        String name;
        int age;
        String tel;
        ;

        public Person(String name, int age, String tel) {
            this.name = name;
            this.age = age;
            this.tel = tel;
        }
    }

}
