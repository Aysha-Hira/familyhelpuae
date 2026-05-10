package com.familyhelpuae.auth.controller;

import com.familyhelpuae.auth.service.impl.AuthServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.familyhelpuae.DTO.Family;
import com.familyhelpuae.DTO.Register;
import com.familyhelpuae.auth.service.AuthService;
import com.familyhelpuae.user.model.User;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class RegisterController {
    AuthService authService;

    public RegisterController(AuthService authService, AuthServiceImpl authServiceImpl) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String submitPersonal(
            @Valid @ModelAttribute("register") Register dto,
            BindingResult result,
            HttpSession session) {

        if (result.hasErrors())
            return "register";

        if (authService.isEmailExisting(dto.getEmail())) {
            result.rejectValue("email", "duplicate", "An account with this email already exists");
            return "register";
        }

        session.setAttribute("pendingUser", dto);
        // return "redirect:/register/family"; // add after family has been completed
        return "redirect:/register/family";
    }

    @PostMapping("/register/family")
    public String submitFamily(
            @ModelAttribute("register") Register dto,
            HttpSession session) {

        Register pending = (Register) session.getAttribute("pendingUser");

        for (Family family : dto.getFamilies()) {
            pending = authService.addFamilies(pending, dto);
            session.setAttribute("pendingUser", pending);
            if (!pending.isCreateFamily()) // joining a new family
                // Send them to a holding page — they need admin approval
                return "redirect:/register/pending-approval";

        }

        // Either creating a family or skipping — continue to members step
        return "redirect:/register/members";
    }

    @PostMapping("/register/members")
    public String submitMembers(
            @ModelAttribute("register") Register dto,
            HttpSession session) {

        Register pending = (Register) session.getAttribute("pendingUser");

        pending.setRelationships(dto.getRelationships());

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
