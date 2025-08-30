//package com.application.controller;
// 
//import com.application.dto.AppNumberRangeDTO;
//import com.application.dto.DgmToCampusFormDTO;
//import com.application.dto.GenericDropdownDTO;
//import com.application.service.CampusService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
// 
//import java.util.List;
// 
//@RestController
//@RequestMapping("/distribution/campus")
//@CrossOrigin(origins = "*")
//public class CampusController {
// 
//    @Autowired
//    private CampusService dgmService;
// 
//    // --- Endpoints for Dropdowns ---
//    @GetMapping("/academic-years")
//    public List<GenericDropdownDTO> getAcademicYears() {
//        return dgmService.getAllAcademicYears();
//    }
// 
//    @GetMapping("/states")
//    public List<GenericDropdownDTO> getStates() {
//        return dgmService.getAllStates();
//    }
//    
//    @GetMapping("/districts/{stateId}")
//    public List<GenericDropdownDTO> getDistrictsByState(@PathVariable int stateId) {
//        return dgmService.getDistrictsByStateId(stateId);
//    }
// 
//    @GetMapping("/cities/{districtId}")
//    public List<GenericDropdownDTO> getCitiesByDistrict(@PathVariable int districtId) {
//        return dgmService.getCitiesByDistrictId(districtId);
//    }
//    
//    @GetMapping("/campuses/{cityId}")
//    public List<GenericDropdownDTO> getCampusesByCity(@PathVariable int cityId) {
//        return dgmService.getCampusesByCityId(cityId);
//    }
//    
//    @GetMapping("/campaign-areas")
//    public List<GenericDropdownDTO> getAllCampaignAreas() {
//        return dgmService.getAllCampaignAreas();
//    }
//    
//    @GetMapping("/pros/{campusId}")
//    public List<GenericDropdownDTO> getProsByCampus(@PathVariable int campusId) {
//        return dgmService.getProsByCampusId(campusId);
//    }
//    
//    @GetMapping("/issued-to")
//    public List<GenericDropdownDTO> getIssuedToTypes() {
//        return dgmService.getAllIssuedToTypes();
//    }
// 
//    @GetMapping("/app-number-ranges")
//    public List<AppNumberRangeDTO> getAppNumberRanges(@RequestParam int academicYearId, @RequestParam int employeeId) {
//        return dgmService.getAvailableAppNumberRanges(employeeId, academicYearId);
//    }
//    
//    @GetMapping("/mobile-no/{empId}")
//    public ResponseEntity<String> getMobileNo(@PathVariable int empId) {
//        String mobileNumber = dgmService.getMobileNumberByEmpId(empId);
//        if (mobileNumber != null) {
//            return ResponseEntity.ok(mobileNumber);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
// 
//    // --- Endpoint for Form Submission ---
//    @PostMapping("/save")
//    public ResponseEntity<String> submitForm(@RequestBody DgmToCampusFormDTO formDto) {
//        try {
//            dgmService.submitDgmToCampusForm(formDto);
//            return ResponseEntity.ok("DGM distribution saved successfully!");
//        } catch (Exception e) {
//            // Return the actual error message for easier debugging on the frontend
//            return ResponseEntity.internalServerError().body(e.getMessage());
//        }
//    }
//}