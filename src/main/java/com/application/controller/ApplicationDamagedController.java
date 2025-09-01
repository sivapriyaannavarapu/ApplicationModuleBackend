package com.application.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.dto.ApplicationDamagedDto;
import com.application.entity.AppStatus;
import com.application.entity.AppStatusTrackView;
import com.application.entity.Campus;
import com.application.entity.Employee;
import com.application.entity.Status;
import com.application.service.ApplicationDamagedService;

@RestController
@RequestMapping("/api/applications")
public class ApplicationDamagedController {

    @Autowired
    private ApplicationDamagedService applicationDamagedService;
    
 // Add these new endpoints to your ApplicationDamagedController class

    @GetMapping("/pro-employees")
    public ResponseEntity<List<Employee>> getAllProEmployees() {
        List<Employee> employees = applicationDamagedService.getAllProEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/zone-employees")
    public ResponseEntity<List<Employee>> getAllZoneEmployees() {
        List<Employee> employees = applicationDamagedService.getAllZoneEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/dgm-employees")
    public ResponseEntity<List<Employee>> getAllDgmEmployees() {
        List<Employee> employees = applicationDamagedService.getAllDgmEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/campuses")
    public ResponseEntity<List<Campus>> getAllCampuses() {
        List<Campus> campuses = applicationDamagedService.getAllCampuses();
        return new ResponseEntity<>(campuses, HttpStatus.OK);
    }

    // Endpoint for saving the application status (POST)
    @PostMapping("/status")
    public ResponseEntity<?> createApplicationStatus(@RequestBody ApplicationDamagedDto requestDTO) {
        try {
            AppStatus savedStatus = applicationDamagedService.saveApplicationStatus(requestDTO);
            return new ResponseEntity<>(savedStatus, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error saving application status: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint for getting all status options (for the dropdown)
    @GetMapping("/statuses")
    public ResponseEntity<List<Status>> getAllStatuses() {
        List<Status> statuses = applicationDamagedService.getAllStatus();
        return new ResponseEntity<>(statuses, HttpStatus.OK);
    }
    
    // Endpoint for autopopulating form fields based on application number
    @GetMapping("/{applicationNo}")
    public ResponseEntity<?> getApplicationDetails(@PathVariable Integer applicationNo) {
        Optional<AppStatusTrackView> details = applicationDamagedService.getDetailsByApplicationNo(applicationNo);
        if (details.isPresent()) {
            return new ResponseEntity<>(details.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Application not found with number: " + applicationNo, HttpStatus.NOT_FOUND);
        }
    }
}