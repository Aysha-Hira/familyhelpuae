package com.familyhelpuae.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.familyhelpuae.auth.model.Login;
import com.familyhelpuae.auth.model.Register;

@Controller
public class PageController {

    @GetMapping({ "/", "/home" })
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("login", new Login());
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("register", new Register());
        return "register";
    }

    @GetMapping("/register/members")
    public String members(Model model) {
        model.addAttribute("register", new Register());
        return "register-members";
    }

    @GetMapping("/register/family")
    public String family(Model model) {
        model.addAttribute("register", new Register());
        return "register-family";
    }

    @GetMapping("/register/verification")
    public String verification(Model model) {
        model.addAttribute("register", new Register());
        return "register-verification";
    }

    @GetMapping({ "/error" })
    public String error() {
        return "error";
    }
}