// package com.familyhelpuae.user.controller;

// import java.util.List;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import com.familyhelpuae.user.model.User;
// import com.familyhelpuae.user.model.UserFamily;
// import com.familyhelpuae.user.service.UserFamilyService;

// @RestController
// @RequestMapping("/user/{userId}/family")
// public class UserFamilyController {
//    private final UserFamilyService userFamilyService;

//    public UserFamilyController(UserFamilyService userFamilyService) {
//       this.userFamilyService = userFamilyService;
//    }

//    @GetMapping()
//    public ResponseEntity<List<UserFamily>> getAllFamilies(@PathVariable String userId) {
//       return ResponseEntity.ok(userFamilyService.getAllFamilies(userId));
//    }

//    @GetMapping("/registered/{familyId}")
//    public ResponseEntity<Boolean> isRelatedToFamily(@PathVariable String userId, @PathVariable String familyId) {
//       return ResponseEntity.ok(userFamilyService.isRelatedToFamily(userId, familyId));
//    }

//    @PostMapping("/add")
//    public ResponseEntity<User> addRelationship(@PathVariable String userId, @RequestParam String familyId,
//          @RequestParam String role) {
//       return ResponseEntity.ok(userFamilyService.addFamily(userId, familyId, role));
//    }

//    @PutMapping("/update")
//    public ResponseEntity<User> updateRelationship(@PathVariable String userId, @RequestParam String familyId,
//          @RequestParam String role) {
//       return ResponseEntity.ok(userFamilyService.updateFamily(userId, familyId, role));
//    }

//    @DeleteMapping("/remove")
//    public ResponseEntity<Void> removeRelationship(@PathVariable String userId, @RequestParam String familyId) {
//       userFamilyService.removeFamily(userId, familyId);
//       return ResponseEntity.noContent().build();
//    }

// }
