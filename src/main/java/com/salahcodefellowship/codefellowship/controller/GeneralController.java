package com.salahcodefellowship.codefellowship.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GeneralController {

    @GetMapping
    public String getHomePage(){
        return "splash.html";
    }

    @GetMapping("/admin")
    public String getAdminPage(){
        return "admin";
    }

}
