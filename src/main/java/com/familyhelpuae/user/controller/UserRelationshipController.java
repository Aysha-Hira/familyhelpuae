package com.familyhelpuae.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
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
   public ResponseEntity<List<UserRelationship>> getAllRelationships(@PathVariable String userId) {
      return ResponseEntity.ok(userRelationshipService.getAllRelationships(userId));
   }

   @GetMapping("/type/{relationshipType}")
   public ResponseEntity<List<UserRelationship>> getRelationshipsByType(@PathVariable String userId,
         @PathVariable String relationshipType) {
      return ResponseEntity.ok(userRelationshipService.getRelationshipsByType(userId, relationshipType));
   }

   @GetMapping("/registered/{anotherUserID}")
   public ResponseEntity<Boolean> hasRelationshipWithRegisteredUser(@PathVariable String userId,
         @PathVariable String anotherUserID) {
      return ResponseEntity.ok(userRelationshipService.hasRelationshipWithRegisteredUser(userId, anotherUserID));
   }

   @GetMapping("/non-registered")
   public ResponseEntity<Boolean> hasRelationshipWithNonRegisteredUser(@PathVariable String userId,
         @RequestParam String name,
         @RequestParam String email) {
      return ResponseEntity.ok(userRelationshipService.hasRelationshipWithNonRegisteredUser(userId, name, email));
   }

   @PostMapping("/add")
   public ResponseEntity<User> addRelationship(@PathVariable String userId, @RequestParam String anotherUserID,
         @RequestParam String relationshipType) {
      return ResponseEntity.ok(userRelationshipService.addRelationship(userId, anotherUserID, relationshipType));
   }

   @PostMapping("/add/non-registered")
   public ResponseEntity<User> addRelationship(@PathVariable String userId, @RequestParam String name,
         @RequestParam String email,
         @RequestParam String relationshipType) {
      return ResponseEntity.ok(userRelationshipService.addRelationship(userId, name, email, relationshipType));
   }

   @PutMapping("/update")
   public ResponseEntity<User> updateRelationship(@PathVariable String userId, @RequestParam String anotherUserID,
         @RequestParam String relationshipType) {
      return ResponseEntity.ok(userRelationshipService.updateRelationship(userId, anotherUserID, relationshipType));
   }

   @PutMapping("/update/non-registered")
   public ResponseEntity<User> updateRelationship(@PathVariable String userId, @RequestParam String name,
         @RequestParam String email,
         @RequestParam String relationshipType) {
      return ResponseEntity.ok(userRelationshipService.updateRelationship(userId, name, email, relationshipType));
   }

   @DeleteMapping("/remove")
   public ResponseEntity<Void> removeRelationship(@PathVariable String userId, @RequestParam String anotherUserID) {
      userRelationshipService.removeRelationship(userId, anotherUserID);
      return ResponseEntity.noContent().build();
   }

   @DeleteMapping("/remove/non-registered")
   public ResponseEntity<Void> removeRelationship(@PathVariable String userId, @RequestParam String name,
         @RequestParam String email) {
      userRelationshipService.removeRelationship(userId, name, email);
      return ResponseEntity.noContent().build();
   }

}
