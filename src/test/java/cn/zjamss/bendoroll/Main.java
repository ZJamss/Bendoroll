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
        app.get("/u",ctx -> {
            ctx.html("哈哈哈");
        });
        app.get("/u1",ctx -> {
            ctx.json("{\"a\":\"哈哈哈\"}");
        });
        app.get("/", ctx -> {
            ctx.ok().put("person", new Person(ctx.param("name"), 19, "Dont have delusion"));
        });
        app.aspect(Lifecycle.AROUND, "/", ctx -> {
            ctx.ok().put("around","around");
            return true;
        });
        app.aspect(Lifecycle.BEFORE, "/", ctx -> {
            ctx.ok().put("before","before");
            if(ctx.param("name") == null){
                ctx.error(400,"别来沾边");
            }
            return false;
        });
        app.aspect(Lifecycle.AFTER, "/", ctx -> {
            ctx.ok().put("after","after");
            return true;
        });
        app.aspect(Lifecycle.EXCEPTION, "/", ctx -> {
            ctx.ok().put("exception","exception");
            return true;
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
