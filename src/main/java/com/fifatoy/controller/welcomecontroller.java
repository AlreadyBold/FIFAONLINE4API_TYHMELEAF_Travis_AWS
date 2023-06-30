package com.fifatoy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class welcomecontroller {

    @GetMapping("/index")
    public String index() {
        
        return "/";
    }

    @GetMapping("/join")
    public String join() {
        return "memberjoin";
    }

}