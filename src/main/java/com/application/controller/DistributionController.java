package com.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.dto.DistributionRequestDTO;
import com.application.entity.AcademicYear;
import com.application.entity.City;
import com.application.entity.Employee;
import com.application.entity.State;
import com.application.entity.Zone;
import com.application.repository.EmployeeRepository;
import com.application.service.DistributionService;

@RestController
@RequestMapping("/api/distribution-options") // Base URL for these endpoints
@CrossOrigin("*") // Allows requests from your frontend
public class DistributionController {

	@Autowired
	private DistributionService distributionService;
	
	@Autowired
	EmployeeRepository employeeRepository;

	@GetMapping("/academic-years")
	public ResponseEntity<List<AcademicYear>> getAcademicYears() {
		return ResponseEntity.ok(distributionService.getAllAcademicYears());
	}

	@GetMapping("/states")
	public ResponseEntity<List<State>> getStates() {
		return ResponseEntity.ok(distributionService.getAllStates());
	}

	@GetMapping("/cities/{stateId}")
	public ResponseEntity<List<City>> getCitiesByState(@PathVariable int stateId) {
		return ResponseEntity.ok(distributionService.getCitiesByState(stateId));
	}

	@GetMapping("/zones/{cityId}")
	public ResponseEntity<List<Zone>> getZonesByCity(@PathVariable int cityId) {
		return ResponseEntity.ok(distributionService.getZonesByCity(cityId));
	}

	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getEmployees() {
		return ResponseEntity.ok(distributionService.getIssuableToEmployees());
	}

	// In DistributionController.java...

	// ... (keep the existing @GetMappings for dropdowns)

	// In your DistributionController.java file

	// In your DistributionController.java

	// In your DistributionController.java
	@GetMapping("/next-app-number")
	public ResponseEntity<String> getNextAppNumber(@RequestParam int academicYearId, @RequestParam int stateId,
			@RequestParam int userId) {

		String nextAppNumber = distributionService.getNextApplicationNumber(academicYearId, stateId, userId);
		return ResponseEntity.ok(nextAppNumber);
	}
	

	@PostMapping("/save")
	public ResponseEntity<String> saveDistribution(@RequestBody DistributionRequestDTO request) {
	    try {
	        distributionService.saveDistribution(request);
	        return ResponseEntity.ok("Distribution saved successfully!");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Failed to save distribution: " + e.getMessage());
	    }
	}
	
	 @GetMapping("/{empId}/mobile")
	    public String getMobileByEmpId(@PathVariable int empId) {
	        return employeeRepository.findMobileNoByEmpId(empId);
	    }
}