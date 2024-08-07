package com.sajal.rest.webservices.restful_web_services.helloworld;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
public class HelloWorldController {

    private MessageSource source;

    public HelloWorldController(MessageSource source){
        this.source = source;
    }

    @GetMapping(path = "/hello-world")
    public String helloWorld(){
        return "Hello World";
    }

    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("Hello World");
    }

    // path parameter
    @GetMapping(path = "/hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloWorldBeanPathVariable(@PathVariable String name) {
        return new HelloWorldBean(
                "hello world, " + name
        );
    }

    @GetMapping(path = "/hello-world-international")
    public String helloWorldInternationalization(){
        Locale locale = LocaleContextHolder.getLocale();
        return source.getMessage("good.morning.message", null, "Default message", locale);
    }
}
