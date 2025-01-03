package com.zosh.treading.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
     @GetMapping
    public String Home(){
        return "welcome to treading application sir";
    }
    @GetMapping("/api")
    public String secure()
    {
        return "welcome to trading platform secure";
    }
}
