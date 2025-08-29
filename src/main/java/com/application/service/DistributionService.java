package com.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.dto.DistributionRequestDTO;
import com.application.entity.AcademicYear;
import com.application.entity.City;
import com.application.entity.Distribution;
import com.application.entity.Employee;
import com.application.entity.State;
import com.application.entity.StateApp;
import com.application.entity.Zone;
import com.application.repository.AcademicYearRepository;
import com.application.repository.AppIssuedTypeRepository;
import com.application.repository.BalanceTrackRepository;
import com.application.repository.CityRepository;
import com.application.repository.DistributionRepository;
import com.application.repository.EmployeeRepository;
import com.application.repository.StateAppRepository;
import com.application.repository.StateRepository;
import com.application.repository.ZoneRepository;

@Service
public class DistributionService {

	@Autowired
	private AcademicYearRepository academicYearRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private ZoneRepository zoneRepository;

	@Autowired
	private AppIssuedTypeRepository appIssuedTypeRepository;

	@Autowired
	private EmployeeRepository employeeRepository; // Assuming 'Issued To' lists employees

	@Autowired
	private StateAppRepository stateAppRepository;

	public List<AcademicYear> getAllAcademicYears() {
		return academicYearRepository.findAll();
	}

	public List<State> getAllStates() {
		return stateRepository.findAll();
	}

	public List<City> getCitiesByState(int stateId) {
		return cityRepository.findByDistrictStateStateId(stateId);
	}

	public List<Zone> getZonesByCity(int cityId) {
		return zoneRepository.findByCityCityId(cityId);
	}

	// This can be used for the 'Issued To' dropdown if it lists employees
	public List<Employee> getIssuableToEmployees() {
		return employeeRepository.findAll();
	}

	// In DistributionService.java...

	// Add this new dependency at the top
	@Autowired
	private BalanceTrackRepository balanceTrackRepository;

	// Add this new dependency at the top
	@Autowired
	private DistributionRepository distributionRepository;

	// ... (keep the existing methods for dropdowns)

	// In DistributionService.java...
	// In your DistributionService.java
	// In your DistributionService.java
	public String getNextApplicationNumber(int academicYearId, int stateId, int userId) {
		// Find the highest number already distributed
		Long lastAppNumber = distributionRepository.findMaxAppEndNoAsLong(stateId, userId, academicYearId);

		if (lastAppNumber != null) {
			// If a number exists, simply add 1 and return it
			return String.valueOf(lastAppNumber + 1);
		} else {
			// If no number exists, get the starting number from the 'StateApp' table
			Optional<StateApp> stateAppOpt = stateAppRepository.findStartNumber(stateId, userId, academicYearId);

			if (stateAppOpt.isPresent()) {
				return String.valueOf(stateAppOpt.get().getApp_start_no());
			} else {
				return "ERROR_NO_RANGE_ASSIGNED";
			}
		}
	}
	

	public void saveDistribution(DistributionRequestDTO request) {
	    Distribution newDistribution = new Distribution();

	    // Set relationships from IDs
	    newDistribution.setAcademicYear(academicYearRepository.findById(request.getAcademicYearId()).orElse(null));
	    newDistribution.setState(stateRepository.findById(request.getStateId()).orElse(null));
	    newDistribution.setCity(cityRepository.findById(request.getCityId()).orElse(null));
	    newDistribution.setZone(zoneRepository.findById(request.getZoneId()).orElse(null));
	    
	    // Set other fields from the request
	    newDistribution.setIssued_to_emp_id(request.getIssuedToEmpId());
	    newDistribution.setCreated_by(request.getCreatedBy());

	    // --- This is where the Range and App No To are saved ---
	    newDistribution.setAppStartNo(request.getAppStartNo());
	    newDistribution.setAppEndNo(request.getAppEndNo());
	    newDistribution.setTotalAppCount(request.getRange()); // The 'Range' is the total count

	    newDistribution.setIssueDate(request.getIssueDate()); // Assuming you added this field to the entity
	    newDistribution.setIsActive(1); // Set as active

	    // Save the new record to the database
	    distributionRepository.save(newDistribution);
	}
}