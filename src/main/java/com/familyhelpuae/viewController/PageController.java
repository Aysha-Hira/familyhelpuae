package com.familyhelpuae.viewController;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.familyhelpuae.DTO.Login;
import com.familyhelpuae.DTO.Register;
import com.familyhelpuae.DTO.Relationship;
import com.familyhelpuae.offer.model.Offer;
import com.familyhelpuae.offer.service.offerService;
import com.familyhelpuae.security.CustomUserDetails;
import com.familyhelpuae.user.model.User;
import com.familyhelpuae.user.service.UserProfileService;

@Controller
public class PageController {
    private final UserProfileService userProfileService;

    private final offerService offerService;

    public PageController(offerService offerService, UserProfileService userProfileService) {
        this.offerService = offerService;
        this.userProfileService = userProfileService;
    }
   

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping({ "/" })
    public String landingPage() {
        return "index";
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

    


    // ========== OFFER PAGES ==========
    
    // Show the create offer form
    @GetMapping("/offer/create")
    public String showCreateOfferForm(Model model) {
        model.addAttribute("offer", new Offer());
        model.addAttribute("offerTypes", new String[]{"Childcare", "ElderlyCare", "Tutoring", "Household", "Transportation", "Emergency", "Other"});
        return "create-offer";  // This loads src/main/resources/templates/create-offer.html
    }
    
    // Handle the form submission from create-offer.html
    @PostMapping("/offer/create")
    public String createOffer(Offer offer, Model model) {
        try {
            // Get current logged-in user's family ID from session (you need to implement this)
            // For now, using placeholder
            offer.setOfferingFamilyId("currentFamilyId");  // Get from session
            offer.setOfferingUserId("currentUserId");       // Get from session
            
            offerService.createOffer(offer);
            return "redirect:/offer/my-offers";  // Redirect to view all offers after success
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to create offer: " + e.getMessage());
            model.addAttribute("offerTypes", new String[]{"Childcare", "ElderlyCare", "Tutoring", "Household", "Transportation", "Emergency", "Other"});
            return "create-offer";
        }
    }
    
    // Show all offers for current user
    @GetMapping("/offer/my-offers")
    public String showMyOffers(Model model) {
        // Get current user's offers (you need to implement getting current user ID)
        String currentUserId = "currentUserId";  // Get from session
        model.addAttribute("offers", offerService.getOffersByUser(currentUserId));
        return "my-offers";
    }
    
    // Show edit offer form
    @GetMapping("/offer/edit/{offerId}")
    public String showEditOfferForm(@PathVariable String offerId, Model model) {
        Offer offer = offerService.getOfferById(offerId);
        model.addAttribute("offer", offer);
        model.addAttribute("offerTypes", new String[]{"Childcare", "ElderlyCare", "Tutoring", "Household", "Transportation", "Emergency", "Other"});
        return "edit-offer";
    }
    
    // Handle edit form submission
    @PostMapping("/offer/edit/{offerId}")
    public String updateOffer(@PathVariable String offerId, Offer updatedOffer) {
        offerService.updateOffer(updatedOffer, offerId);
        return "redirect:/offer/my-offers";
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User user = userDetails.getUser();
        model.addAttribute("user", user);
        model.addAttribute("familyMembers", userProfileService.getFamilyMembers(user.getUserID()));
        return "userprofile";
    }

    @GetMapping("/family")
    public String familyProfile() {
        return "familyprofile";
    }

    @GetMapping("/request/new")
    public String newRequest() {
        return "request";
    }

    @GetMapping("/request")
    public String viewRequest() {
        return "request";
    }

    // FIX 1: Added /forgot-password route (was a dead link in login.html)
    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }

    @GetMapping("/register/pending-approval")
    public String pendingApproval() {
        return "register-pending-approval";
    }

    // Redirects back to the verification page
    @GetMapping("/register/resend")
    public String resendVerification() {
        return "redirect:/register/verification";
    }

}
