package com.familyhelpuae.user.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.familyhelpuae.user.model.User;
import com.familyhelpuae.user.model.UserRelationship;
import com.familyhelpuae.user.service.UserRelationshipService;

@RestController
@RequestMapping("/user/{userId}/relationships")
public class UserRelationshipController {
   private final UserRelationshipService userRelationshipService;

   public UserRelationshipController(UserRelationshipService userRelationshipService) {
      this.userRelationshipService = userRelationshipService;
   }

   @GetMapping()
   public List<UserRelationship> getAllRelationships(@PathVariable String userId) {
      return userRelationshipService.getAllRelationships(userId);
   }

   @GetMapping("/type/{relationshipType}")
   public List<UserRelationship> getRelationshipsByType(@PathVariable String userId,
         @PathVariable String relationshipType) {
      return userRelationshipService.getRelationshipsByType(userId, relationshipType);
   }

   @GetMapping("/registered/{anotherUserID}")
   public boolean hasRelationshipWithRegisteredUser(@PathVariable String userId, @PathVariable String anotherUserID) {
      return userRelationshipService.hasRelationshipWithRegisteredUser(userId, anotherUserID);
   }

   @GetMapping("/non-registered")
   public boolean hasRelationshipWithNonRegisteredUser(@PathVariable String userId, @RequestParam String name,
         @RequestParam String email) {
      return userRelationshipService.hasRelationshipWithNonRegisteredUser(userId, name, email);
   }

   @PostMapping("/add")
   public User addRelationship(@PathVariable String userId, @RequestParam String anotherUserID,
         @RequestParam String relationshipType) {
      return userRelationshipService.addRelationship(userId, anotherUserID, relationshipType);
   }

   @PostMapping("/add/non-registered")
   public User addRelationship(@PathVariable String userId, @RequestParam String name, @RequestParam String email,
         @RequestParam String relationshipType) {
      return userRelationshipService.addRelationship(userId, name, email, relationshipType);
   }

   @PutMapping("/update")
   public User updateRelationship(@PathVariable String userId, @RequestParam String anotherUserID,
         @RequestParam String relationshipType) {
      return userRelationshipService.updateRelationship(userId, anotherUserID, relationshipType);
   }

   @PutMapping("/update/non-registered")
   public User updateRelationship(@PathVariable String userId, @RequestParam String name, @RequestParam String email,
         @RequestParam String relationshipType) {
      return userRelationshipService.updateRelationship(userId, name, email, relationshipType);
   }

   @DeleteMapping("/remove")
   public void removeRelationship(@PathVariable String userId, @RequestParam String anotherUserID) {
      userRelationshipService.removeRelationship(userId, anotherUserID);
   }

   @DeleteMapping("/remove/non-registered")
   public void removeRelationship(@PathVariable String userId, @RequestParam String name, @RequestParam String email) {
      userRelationshipService.removeRelationship(userId, name, email);
   }

}
