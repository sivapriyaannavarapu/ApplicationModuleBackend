package com.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.dto.DgmToCampusFormDTO;
import com.application.dto.DistributionRequestDTO;
import com.application.dto.FormSubmissionDTO;
import com.application.service.CampusService;
import com.application.service.DgmService;
import com.application.service.ZoneService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/distribution/posts")
@CrossOrigin(origins = "*")
public class DistributionUpdates {
	
	@Autowired
	private ZoneService distributionService;
	
	@Autowired
	private DgmService applicationService;
	 
	@Autowired
	private CampusService dgmService;
	
	 @PutMapping("/update-zone/{id}")
     public ResponseEntity<String> updateDistribution(@PathVariable int id, @RequestBody DistributionRequestDTO request) {
         try {
             distributionService.updateDistribution(id, request);
             return new ResponseEntity<>("Distribution updated successfully", HttpStatus.OK);
         } catch (RuntimeException e) {
             return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
         }
     }
  
     @PutMapping("/update-distribute/{id}")
     public ResponseEntity<String> updateDistribution(@PathVariable Integer id, @RequestBody FormSubmissionDTO formDto) {
         try {
             applicationService.updateForm(id, formDto);
             return ResponseEntity.ok("Distribution record updated successfully.");
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update distribution: " + e.getMessage());
         }
     }
    
     @PutMapping("/update-campus/{Id}")
     public ResponseEntity<Void> updateDgmToCampusForm(
             @PathVariable int distributionId,
             @RequestBody DgmToCampusFormDTO formDto) {
         try {
             dgmService.updateDgmToCampusForm(distributionId, formDto);
             return new ResponseEntity<>(HttpStatus.OK);
         } catch (RuntimeException e) {
             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
         }
     }

}
