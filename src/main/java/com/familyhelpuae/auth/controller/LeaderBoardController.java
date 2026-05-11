package com.familyhelpuae.auth.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.familyhelpuae.family.service.FamilyService;
import com.familyhelpuae.security.CustomUserDetails;
import com.familyhelpuae.user.model.User;

@Controller
public class LeaderBoardController {
    private final FamilyService FamilyService;

    LeaderBoardController(FamilyService FamilyService) {
        this.FamilyService = FamilyService;
    }
    @GetMapping("/leaderboard")
    public String leaderboard(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        // Get all families sorted by trust score descending
        List<com.familyhelpuae.family.model.Family> families = FamilyService.getAllFamilies()
                .stream()
                .sorted((a, b) -> Double.compare(b.getFamilyTrustScore(), a.getFamilyTrustScore()))
                .collect(java.util.stream.Collectors.toList());

        model.addAttribute("families", families);

        // Highlight the logged-in user's family
        User user = userDetails.getUser();
        if (user.getFamilies() != null && !user.getFamilies().isEmpty()) {
            model.addAttribute("currentFamilyId", user.getFamilies().get(0).getFamilyId());
        } else {
            model.addAttribute("currentFamilyId", null);
        }

        return "leaderboard";
    }
}
