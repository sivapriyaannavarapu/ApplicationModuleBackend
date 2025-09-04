package com.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.dto.AppStatusTrackDTO;
import com.application.dto.MetricCardDTO;
import com.application.service.AppStatusTrackService;

@RestController
@RequestMapping("/api/dashboard/CO")
public class AppStatusTrackController {

	@Autowired
	private AppStatusTrackService appStatusTrackService;

	@GetMapping("/metrics")
	public ResponseEntity<List<MetricCardDTO>> getDashboardCards() {
		List<MetricCardDTO> cards = appStatusTrackService.getDashboardCards();
		return ResponseEntity.ok(cards);
	}
	
	 @GetMapping("/stats-by-issued-type-and-employee")
	    public ResponseEntity<AppStatusTrackDTO> getStatsByIssuedTypeAndEmployee(
	            @RequestParam Integer issuedTypeId,
	            @RequestParam Integer empId) {
	        
	        AppStatusTrackDTO stats = appStatusTrackService.getAppStatusByIssuedTypeAndEmployee(issuedTypeId, empId);
	        return ResponseEntity.ok(stats);
	    }
}