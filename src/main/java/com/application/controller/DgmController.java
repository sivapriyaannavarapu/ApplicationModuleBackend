package com.application.controller;
 
import com.application.dto.AppNumberRangeDTO;
import com.application.dto.FormSubmissionDTO;
import com.application.dto.GenericDropdownDTO;
import com.application.service.DgmService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
 
@RestController
@RequestMapping("/api/application")
@CrossOrigin(origins = "*")
public class DgmController {
 
    @Autowired
    private DgmService applicationService;
 
    @GetMapping("/academic-years")
    public List<GenericDropdownDTO> getAcademicYears() {
        return applicationService.getAllAcademicYears();
    }
 
    @GetMapping("/cities")
    public List<GenericDropdownDTO> getCities() {
        return applicationService.getAllCities();
    }
 
    @GetMapping("/zones/{cityId}")
    public List<GenericDropdownDTO> getZonesByCity(@PathVariable int cityId) {
        return applicationService.getZonesByCityId(cityId);
    }
 
    @GetMapping("/campuses/{zoneId}")
    public List<GenericDropdownDTO> getCampusesByZone(@PathVariable int zoneId) {
        return applicationService.getCampusesByZoneId(zoneId);
    }
    
    @GetMapping("/issued-to")
    public List<GenericDropdownDTO> getIssuedToTypes() {
        return applicationService.getAllIssuedToTypes();
    }
 
    @GetMapping("/app-number-ranges")
    public List<AppNumberRangeDTO> getAppNumberRanges(@RequestParam("academicYearId") int academicYearId,
                                                      @RequestParam("employeeId") int employeeId) {
        return applicationService.getAvailableAppNumberRanges(academicYearId, employeeId);
    }
    
    @GetMapping("/mobile-no/{empId}")
    public ResponseEntity<String> getMobileNo(@PathVariable int empId) {
        String mobileNumber = applicationService.getMobileNumberByEmpId(empId);
        if (mobileNumber != null) {
            return ResponseEntity.ok(mobileNumber);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
 
    @PostMapping("/submit-form")
    public ResponseEntity<String> submitForm(@RequestBody FormSubmissionDTO formDto) {
        try {
            applicationService.submitForm(formDto);
            return ResponseEntity.ok("Form submitted successfully!");
        } catch (Exception e) {
            // Return the actual error message for easier debugging on the frontend
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
 