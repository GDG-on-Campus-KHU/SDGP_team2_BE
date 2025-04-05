package com.gdg.coffee.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/api")
@RestController
public class TestController {
    @GetMapping("/test")
    public String hello(){
        return "hello coffee!";
    }

}
