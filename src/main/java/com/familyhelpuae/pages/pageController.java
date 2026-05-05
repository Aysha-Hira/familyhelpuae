package com.familyhelpuae.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class pageController {
    @GetMapping("/")
    public String getHome() {
        return "home"; // get home page
    }
    
}
