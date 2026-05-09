package com.familyhelpuae.family.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.familyhelpuae.exception.DuplicateFamilyIDException;
import com.familyhelpuae.family.model.Family;
import com.familyhelpuae.family.model.FamilyMember;
import com.familyhelpuae.family.service.FamilyService;

@RestController
@RequestMapping("/family")
public class FamilyController {
	private final FamilyService familyService;
	
	public FamilyController(FamilyService familyService) {
		this.familyService = familyService;
	}
	
	@GetMapping("/{familyId}")
	public ResponseEntity<Family> getFamilyById(@PathVariable String familyId){
		return ResponseEntity.ok(familyService.getFamilyById(familyId));
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Family>> getAllFamilies(){
		return ResponseEntity.ok(familyService.getAllFamilies());
	}
	
	@PostMapping("/save")
	public ResponseEntity<Family> saveFamily(@RequestBody Family newFamily) throws DuplicateFamilyIDException{
		return ResponseEntity.ok(familyService.saveFamily(newFamily));
	}
	
	@PutMapping("/update/{familyId}")
	public ResponseEntity<Family> updateFamily(@PathVariable String familyId, @RequestBody Family newFamily){
		return ResponseEntity.ok(familyService.updateFamily(newFamily, familyId));
	}
	
	@DeleteMapping("/delete/{familyId}")
	public ResponseEntity<Void> deleteFamily(@PathVariable String familyId){
		familyService.deleteFamily(familyId);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/name/{familyName}")
	public ResponseEntity<List<Family>> getFamiliesByName(@PathVariable String familyName){
		return ResponseEntity.ok(familyService.getFamiliesByName(familyName));
	}
	
    @PostMapping("/{familyId}/members")
    public ResponseEntity<Family> addMember(@PathVariable String familyId, @RequestBody FamilyMember member) {
        return ResponseEntity.ok(familyService.addMember(familyId, member));
    }

    @DeleteMapping("/{familyId}/members")
    public ResponseEntity<Family> removeMember(@PathVariable String familyId, @RequestBody FamilyMember member) {
        return ResponseEntity.ok(familyService.removeMember(familyId, member));
    }

    @GetMapping("/{familyId}/trust-score")
    public ResponseEntity<Double> getTrustScore(@PathVariable String familyId) {
        return ResponseEntity.ok(familyService.getTrustScore(familyId));
    }
    
    @GetMapping("/{familyId}/admins")
	public ResponseEntity<List<FamilyMember>> getFamilyAdmins(@PathVariable String familyId){
		return ResponseEntity.ok(familyService.getFamilyAdmins(familyId));
	}
	
    @GetMapping("/{familyId}/active-users")
	public ResponseEntity<List<FamilyMember>> getActiveMembers(@PathVariable String familyId){
		return ResponseEntity.ok(familyService.getActiveMembers(familyId));
	}
}
