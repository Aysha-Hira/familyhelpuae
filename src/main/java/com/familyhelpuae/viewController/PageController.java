package com.familyhelpuae.viewController;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.familyhelpuae.DTO.Login;
import com.familyhelpuae.DTO.Register;
import com.familyhelpuae.DTO.Relationship;
import com.familyhelpuae.security.CustomUserDetails;
import com.familyhelpuae.user.model.User;
import com.familyhelpuae.user.service.UserProfileService;

@Controller
public class PageController {
    private final UserProfileService userProfileService;

    public PageController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/home")
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

    @GetMapping("/register/family")
    public String family(Model model) {
        model.addAttribute("register", new Register());
        return "register-family";
    }

    @GetMapping("/register/members")
    public String membersPage(Model model) {

        Register register = new Register();

        register.getRelationships().add(new Relationship());

        model.addAttribute("register", register);

        return "register-members";
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

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User user = userDetails.getUser();
        model.addAttribute("user", user);
        model.addAttribute("familyMembers", userProfileService.getFamilyMembers(user.getUserID()));
        return "userprofile";
    }

    // form to add offer form
}