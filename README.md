# Bendoroll - A lightweight java-web framework

A lightweight java-web framework,reference from [simplify_mvc](https://github.com/ZJamss/Simplify_MVC) and [javalin](https://github.com/javalin/javalin), just use for learning exchange ,I trust that you wont to use for production environment hah hah

一款轻量级java-web框架，参考自SpringMVC与Javalin，仅作学习交流使用

 > Updated Continually 

## TODO:

- [x] Lifecycle AOP
- [ ] file response support
- [ ] any more...

## Features:
  - 😎 Simplify
  - 😍 OOTB 
  - 🚀 Get started quickly
  - 📕 Can be used for learning
  - ✌️ Less code amount
  - 🦾 Also no @Annotation
  - ❤️ Also no reflection
  - 😀 Just function

  You just need call some functions , so easy

## QuickStart

### complie the source code to generate `*.jar` file
import this by `maven` , `gradle`, or move it to your lib-dir

### Start Programming
This example to build and run a simple web application
```java
public class Main {
    public static void main(String[] args) {
        Bendoroll app = Bendoroll.create();
        app.get("/example",ctx->{
            ctx.html("Hello World!");
        });
        app.start();
    }
}
```
### Examples
There exist some examples about how to use the framework

#### Build some restful api
```java
public class Main {
    public static void main(String[] args) {
        Bendoroll app = Bendoroll.create(8080);
        app.get("/user", ctx -> {
            ctx.ok.put(new User("ZJamss","Dont have delusion"));
        });
        app.post("/user", ctx -> {
            final User user = ctx.body(User.class);
            //....  do something in Dao 
            ctx.ok();
        });
        app.put("/user", ctx -> {
            final User user = ctx.body(User.class);
            //.... do something in Dao 
            ctx.ok();
        });
        app.delete("/user", ctx -> {
            final User user = ctx.body(User.class);
            //....  do something in Dao 
            ctx.ok();
        });
        app.start();
    }

    static class User{...}
}
```

#### Build an `ExceptionHandler` to handle some exceptions in the api
```java
public class Main {
    public static void main(String[] args) {
        Bendoroll app = Bendoroll.create(8080);

        app.post("/user", ctx -> {
            final User user = ctx.body(User.class);
            //....  do something in Dao
            if(user == null){
                throw new RuntimeException("cant resolve the request body");
            }
            ctx.ok();
        });
        app.exception(RuntimeException.class,((e, ctx) -> {
            //do something to process the exception, such as log...
            ctx.error(400);
        }));
        app.start();
    }
    
    static class User{...}
}
```

#### Build some `AOP Handler`s for a api
```java
public class Main {
    public static void main(String[] args) {
        Bendoroll app = Bendoroll.create();
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

    static class Person {...}

}
```
