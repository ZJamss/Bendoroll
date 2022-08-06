package cn.zjamss.bendoroll;

import com.sun.javafx.binding.StringFormatter;
import com.zjamss.bendoroll.Bendoroll;
import com.zjamss.bendoroll.context.HttpContext;
import com.zjamss.bendoroll.exception.BaseException;
import com.zjamss.bendoroll.handler.Handler;
import com.zjamss.bendoroll.handler.HandlerType;
import com.zjamss.bendoroll.wrapper.HttpServletWrapper;
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
        app.get("/",ctx -> {
           ctx.json("{\"msg\":\"123\",\"code\":200}");
        });
        app.start();
    }

    static class Person{
        String name;
        int age;
        String tel;;

        public Person(String name, int age, String tel) {
            this.name = name;
            this.age = age;
            this.tel = tel;
        }
    }

}
