package com.familyhelpuae.auth.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.familyhelpuae.auth.model.Register;
import com.familyhelpuae.auth.service.AuthService;
import com.familyhelpuae.user.model.User;
import com.familyhelpuae.user.repository.UserRepository;
import com.familyhelpuae.user.service.UserRelationshipService;
import com.familyhelpuae.user.service.impl.UserRelationshipServiceImpl;
import com.familyhelpuae.user.service.impl.UserServiceImpl;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String submitPersonal(
            @Valid @ModelAttribute("register") Register dto,
            BindingResult result,
            HttpSession session) {

        if (result.hasErrors())
            return "register";

        session.setAttribute("pendingUser", dto);
        // return "redirect:/register/family"; // add after family has been completed
        return "redirect:/register/members";
    }

    @PostMapping("/register/members")
    public String submitMembers(
            @ModelAttribute("register") Register dto,
            HttpSession session) {

        Register pending = (Register) session.getAttribute("pendingUser");
        // build and save user
        if (pending == null) {
            // handle error - no pending user in session
            return "redirect:/register";
        }
        // Store relationship data in pending for later use
        pending.setRelatedEmail(dto.getRelatedEmail());
        pending.setRelatedName(dto.getRelatedName());
        pending.setRelationshipType(dto.getRelationshipType());

        // Update session with combined data
        session.setAttribute("pendingUser", pending);

        return "redirect:/register/verification";
    }

    @PostMapping("/register/verification")
public String submitVerification(
        @ModelAttribute("register") Register dto,
        HttpSession session) {

    Register pending = (Register) session.getAttribute("pendingUser");

    if (pending == null) {
        return "redirect:/register";
    }

    // CHECK VERIFICATION CODE HERE
    boolean isCodeCorrect = true;

    if (!isCodeCorrect) {
        return "redirect:/register/verification";
    }

    authService.completeVerifiedRegistration(pending);

    session.removeAttribute("pendingUser");

    return "redirect:/home";
}

    @GetMapping("/register/members/skip")
    public String skipMembers(HttpSession session) {

        Register pending = (Register) session.getAttribute("pendingUser");

        // Build user from step 1 data
        User user = new User();
        user.setFirstName(pending.getFirstName());
        user.setLastName(pending.getLastName());
        user.setPhone(pending.getPhone());
        user.setEmail(pending.getEmail());
        user.setPassword(pending.getPassword());

        authService.completeRegistrationWithoutRelationship(pending);

        session.removeAttribute("pendingUser");
        return "redirect:/home";
    }
}
