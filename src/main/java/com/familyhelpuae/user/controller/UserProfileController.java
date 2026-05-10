package com.familyhelpuae.user.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.familyhelpuae.security.CustomUserDetails;
import com.familyhelpuae.user.model.User;
import com.familyhelpuae.user.service.UserFamilyService;
import com.familyhelpuae.user.service.UserRelationshipService;
import com.familyhelpuae.user.service.UserService;

/**
 * FIX 5: Handles the four profile endpoints that userprofile.html posts to:
 * POST /profile/update – form-based profile field update
 * POST /profile/connections/add – add a relationship (fetch/JSON)
 * POST /profile/connections/delete – remove a relationship (fetch/JSON)
 * POST /profile/families/leave – leave a family (form-based)
 */
@Controller
@RequestMapping("/profile")
public class UserProfileController {

    private final UserService userService;
    private final UserRelationshipService userRelationshipService;
    private final UserFamilyService userFamilyService;

    public UserProfileController(
            UserService userService,
            UserRelationshipService userRelationshipService,
            UserFamilyService userFamilyService) {
        this.userService = userService;
        this.userRelationshipService = userRelationshipService;
        this.userFamilyService = userFamilyService;
    }

    /**
     * Handles the profile edit form in userprofile.html.
     * Fields sent: firstName, lastName, phone, languagesSpoken, nationality
     * (email is disabled in the form so it is NOT submitted — we never overwrite it
     * here)
     */
    @PostMapping("/update")
    public String updateProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String phone,
            @RequestParam(required = false) String languagesSpoken,
            @RequestParam(required = false) String nationality) {

        // TODO: ADD userProfileService function here to return a Register object

        User patch = new User();
        patch.setFirstName(firstName);
        patch.setLastName(lastName);
        patch.setPhone(phone);

        // The template sends these as comma-separated strings
        if (languagesSpoken != null && !languagesSpoken.isBlank()) {
            List<String> langs = Arrays.stream(languagesSpoken.split(","))
                    .map(String::trim) // removes spaces for each String in the list
                    .filter(s -> !s.isEmpty())
                    .toList();
            patch.setLanguagesSpoken(langs);
        }

        if (nationality != null && !nationality.isBlank()) {
            List<String> nats = Arrays.stream(nationality.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toList();
            patch.setNationality(nats);
        }

        userService.update(userDetails.getUser().getUserID(), patch);

        return "redirect:/profile";
    }

    /**
     * Handles the fetch() call from addConnection() in userprofile.html.
     * Body: { "userId": "<relatedUserId>", "relationshipType": "<type>" }
     */
    @PostMapping("/connections/add")
    @ResponseBody
    public User addConnection(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody Map<String, String> body) {

        String currentUserId = userDetails.getUser().getUserID();
        String relatedUserId = body.get("userId");
        String relationshipType = body.get("relationshipType");

        return userRelationshipService.addRelationship(currentUserId, relatedUserId, relationshipType);
    }

    /**
     * Handles the fetch() call from deleteConnection() in userprofile.html.
     * Body: { "userId": "<relatedUserId>" }
     */
    @PostMapping("/connections/delete")
    @ResponseBody
    public void deleteConnection(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody Map<String, String> body) {

        String currentUserId = userDetails.getUser().getUserID();
        String relatedUserId = body.get("userId");

        userRelationshipService.removeRelationship(currentUserId, relatedUserId);
    }

    /**
     * Handles the "Leave family" form in userprofile.html.
     * Field sent: familyId
     */
    @PostMapping("/families/leave")
    public String leaveFamily(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam String familyId) {

        userFamilyService.removeFamily(userDetails.getUser().getUserID(), familyId);

        return "redirect:/profile";
    }
}
