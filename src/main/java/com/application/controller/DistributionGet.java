package com.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.dto.AppNumberRangeDTO;
import com.application.dto.ApplicationStartEndDto;
import com.application.dto.GenericDropdownDTO;
import com.application.entity.AcademicYear;
import com.application.entity.Campus;
import com.application.entity.City;
import com.application.entity.District;
import com.application.entity.Employee;
import com.application.entity.State;
import com.application.entity.Zone;
import com.application.repository.EmployeeRepository;
import com.application.service.CampusService;
import com.application.service.DgmService;
import com.application.service.ZoneService;

@RestController
@RequestMapping("/distribution/gets")
@CrossOrigin(origins = "*")
public class DistributionGet {
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	CampusService campusService;
	
	@Autowired
	private ZoneService distributionService;
	
	@GetMapping("/academic-years")
	public ResponseEntity<List<AcademicYear>> getAcademicYears() {
		return ResponseEntity.ok(distributionService.getAllAcademicYears());
	}

	@GetMapping("/states")
	public ResponseEntity<List<State>> getStates() {
		return ResponseEntity.ok(distributionService.getAllStates());
	}

	@GetMapping("/city/{stateId}")
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
	
	@GetMapping("/next-app-number")
	public ResponseEntity<String> getNextAppNumber(@RequestParam int academicYearId, @RequestParam int stateId,
			@RequestParam int userId) {

		String nextAppNumber = distributionService.getNextApplicationNumber(academicYearId, stateId, userId);
		return ResponseEntity.ok(nextAppNumber);
	}
	
	@GetMapping("/app-number-from-to")
    public ResponseEntity<ApplicationStartEndDto> getAppNumberRanges(
        @RequestParam int academicYearId,
        @RequestParam int stateId,
        @RequestParam int createdBy) {
        
        try {
            ApplicationStartEndDto ranges = distributionService.getAppNumberRanges(academicYearId, stateId, createdBy);
            return ResponseEntity.ok(ranges);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
	
	@GetMapping("/app-end-number")
	public ResponseEntity<Integer> getAppEndNumber(@RequestParam int stateId, @RequestParam int userId) {
	    Integer endNo = distributionService.getAppEndNoForUser(stateId, userId);
	    
	    if (endNo != null) {
	        // If the number is found, return it
	        return ResponseEntity.ok(endNo);
	    } else {
	        // If no record is found, return a 404 Not Found error
	        return ResponseEntity.notFound().build();
	    }
	}
	
	 @GetMapping("/{empId}/mobile")
	    public String getMobileByEmpId(@PathVariable int empId) {
	        return employeeRepository.findMobileNoByEmpId(empId);
	    }
	   // GET /api/zonal-accountants/zone/1/employees
	    @GetMapping("/zone/{zoneId}/employees")
	    public List<Employee> getEmployeesByZone(@PathVariable int zoneId) {
	        return distributionService.getEmployeesByZone(zoneId);
	    }
	    
	    @Autowired
	    private DgmService applicationService;
	 
	    @GetMapping("/cities")
	    public List<GenericDropdownDTO> getCities() {
	        return applicationService.getAllCities();
	    }
	 
	    @GetMapping("/campus/{zoneId}")
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
	    
	    @Autowired
	    private CampusService dgmService;
	  
	    @GetMapping("/districts/{stateId}")
	    public List<GenericDropdownDTO> getDistrictsByState(@PathVariable int stateId) {
	        return dgmService.getDistrictsByStateId(stateId);
	    }
	 
	    @GetMapping("/cities/{districtId}")
	    public List<GenericDropdownDTO> getCitiesByDistrict(@PathVariable int districtId) {
	        return dgmService.getCitiesByDistrictId(districtId);
	    }
	    
	    @GetMapping("/campuses/{cityId}")
	    public List<GenericDropdownDTO> getCampusesByCity(@PathVariable int cityId) {
	        return dgmService.getCampusesByCityId(cityId);
	    }
	    
	    @GetMapping("/campaign-areas")
	    public List<GenericDropdownDTO> getAllCampaignAreas() {
	        return dgmService.getAllCampaignAreas();
	    }
	    
	    @GetMapping("/pros/{campusId}")
	    public List<GenericDropdownDTO> getProsByCampus(@PathVariable int campusId) {
	        return dgmService.getProsByCampusId(campusId);
	    }
	    
	    @GetMapping("/getalldistricts")
	    public List<District> getAllDistricts()
	    {
	    	return campusService.getAllDistricts();
	    }
	    
	    @GetMapping("/zones")
		public List<Zone> fetchAll(){
			return applicationService.findAllZones();
		}
	    
	    @GetMapping("/Campus")
		public List<Campus> fetchAllCampus(){
			return applicationService.fetchAllCampuses();
		}
	 
	 
}
