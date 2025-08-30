package com.application.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.dto.DistributionRequestDTO;
import com.application.entity.AcademicYear;
import com.application.entity.BalanceTrack;
import com.application.entity.City;
import com.application.entity.Distribution;
import com.application.entity.Employee;
import com.application.entity.State;
import com.application.entity.StateApp;
import com.application.entity.Zone;
import com.application.entity.ZonalAccountant;
import com.application.repository.AcademicYearRepository;
import com.application.repository.AppIssuedTypeRepository;
import com.application.repository.BalanceTrackRepository;
import com.application.repository.CityRepository;
import com.application.repository.DistributionRepository;
import com.application.repository.EmployeeRepository;
import com.application.repository.StateAppRepository;
import com.application.repository.StateRepository;
import com.application.repository.ZonalAccountantRepository;
import com.application.repository.ZoneRepository;

@Service
public class ZoneService {

    // --- Repositories ---
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
    private EmployeeRepository employeeRepository;
    @Autowired
    private StateAppRepository stateAppRepository;
    @Autowired
    private BalanceTrackRepository balanceTrackRepository;
    @Autowired
    private DistributionRepository distributionRepository;

    
    @Autowired
    private ZonalAccountantRepository zonalAccountantRepository;
 
    // --- Methods for Dropdowns ---
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

    public List<Employee> getIssuableToEmployees() {
        return employeeRepository.findAll();
    }

    public Integer getAppEndNoForUser(int stateId, int userId) {
        return stateAppRepository.findAppEndNoByStateAndUser(stateId, userId);
    }	
    
    public List<Employee> getEmployeesByZone(int zoneId) {
        List<ZonalAccountant> zonalAccountants = zonalAccountantRepository.findByZoneZoneId(zoneId);
        return zonalAccountants.stream()
                               .map(ZonalAccountant::getEmployee)
                               .collect(Collectors.toList());
    }
    // --- Method for Auto-Populating 'Application No From' ---
    public String getNextApplicationNumber(int academicYearId, int stateId, int userId) {
        Integer lastAppNumber = distributionRepository.findMaxAppEndNo(stateId, userId, academicYearId);

        if (lastAppNumber != null) {
            return String.valueOf(lastAppNumber + 1);
        } else {
            Optional<StateApp> stateAppOpt = stateAppRepository.findStartNumber(stateId, userId, academicYearId);
            if (stateAppOpt.isPresent()) {
                return String.valueOf(stateAppOpt.get().getApp_start_no());
            } else {
                return "ERROR_NO_RANGE_ASSIGNED";
            }
        }
    }

    // --- Method for Saving the Distribution and Updating Balances ---
    @Transactional
    public void saveDistribution(DistributionRequestDTO request) {
        // CRITICAL FIX: Always create a new object for each save request.
        Distribution newDistribution = new Distribution();

        // Part 1: Populate and save the main distribution record
        newDistribution.setAcademicYear(academicYearRepository.findById(request.getAcademicYearId()).orElse(null));
        newDistribution.setState(stateRepository.findById(request.getStateId()).orElse(null));
        newDistribution.setZone(zoneRepository.findById(request.getZoneId()).orElse(null));
        newDistribution.setIssuedByType(appIssuedTypeRepository.findById(request.getIssuedByTypeId()).orElse(null));
        newDistribution.setIssuedToType(appIssuedTypeRepository.findById(request.getIssuedToTypeId()).orElse(null));

        City selectedCity = cityRepository.findById(request.getCityId()).orElse(null);
        newDistribution.setCity(selectedCity);
        if (selectedCity != null) {
            newDistribution.setDistrict(selectedCity.getDistrict());
        }
        
        newDistribution.setIssued_to_emp_id(request.getIssuedToEmpId());
        newDistribution.setCreated_by(request.getCreatedBy());
        newDistribution.setAppStartNo(request.getAppStartNo());
        newDistribution.setAppEndNo(request.getAppEndNo());
        newDistribution.setTotalAppCount(request.getRange());
        newDistribution.setIsActive(1);
        newDistribution.setIssueDate(LocalDate.now());

        distributionRepository.save(newDistribution);

        // --- Part 2: Find or Create and then Update Issuer's Balance ---
        Optional<BalanceTrack> issuerBalanceOpt = balanceTrackRepository.findBalanceTrack(request.getAcademicYearId(), request.getCreatedBy());
        BalanceTrack issuerBalance;

        if (issuerBalanceOpt.isPresent()) {
            // If the issuer's balance record already exists, use it.
            issuerBalance = issuerBalanceOpt.get();
        } else {
            // If balance does NOT exist, create it for the first time using the StateApp configuration.
            StateApp stateApp = stateAppRepository.findStartNumber(request.getStateId(), request.getCreatedBy(), request.getAcademicYearId())
                    .orElseThrow(() -> new RuntimeException("Issuer is not configured in StateApp for ID: " + request.getCreatedBy()));

            issuerBalance = new BalanceTrack();
            issuerBalance.setEmployee(employeeRepository.findById(request.getCreatedBy()).orElse(null));
            issuerBalance.setAcademicYear(stateApp.getAcademicYear());
            issuerBalance.setAppFrom(stateApp.getApp_start_no());
            issuerBalance.setAppTo(stateApp.getApp_end_no());
            issuerBalance.setAppAvblCnt(stateApp.getTotal_no_of_app());
            issuerBalance.setIsActive(1);
            issuerBalance.setCreatedBy(request.getCreatedBy());
            
            // BUG FIX: Added the missing line to set the 'issuedByType'
            issuerBalance.setIssuedByType(appIssuedTypeRepository.findById(request.getIssuedByTypeId()).orElse(null));
        }

        // Now, proceed with the distribution.
        int rangeToDistribute = request.getRange();
        if (issuerBalance.getAppAvblCnt() < rangeToDistribute) {
            throw new RuntimeException("Not enough applications in balance. Available: " + issuerBalance.getAppAvblCnt() + ", Tried to distribute: " + rangeToDistribute);
        }
        
        issuerBalance.setAppAvblCnt(issuerBalance.getAppAvblCnt() - rangeToDistribute);
        issuerBalance.setAppFrom(request.getAppEndNo() + 1);
        balanceTrackRepository.save(issuerBalance);

        // --- Part 3: Create or Update the receiver's balance ---
        Optional<BalanceTrack> existingReceiverBalanceOpt = balanceTrackRepository.findBalanceTrack(request.getAcademicYearId(), request.getIssuedToEmpId());
        BalanceTrack receiverBalance;
        if (existingReceiverBalanceOpt.isPresent()) {
            receiverBalance = existingReceiverBalanceOpt.get();
            receiverBalance.setAppAvblCnt(receiverBalance.getAppAvblCnt() + request.getRange());
            receiverBalance.setAppTo(request.getAppEndNo());
        } else {
            receiverBalance = new BalanceTrack();
            Employee receiverEmployee = employeeRepository.findById(request.getIssuedToEmpId())
                    .orElseThrow(() -> new RuntimeException("Receiver employee not found for ID: " + request.getIssuedToEmpId()));
            receiverBalance.setEmployee(receiverEmployee);
            receiverBalance.setAcademicYear(academicYearRepository.findById(request.getAcademicYearId()).orElse(null));
            receiverBalance.setAppFrom(request.getAppStartNo());
            receiverBalance.setAppTo(request.getAppEndNo());
            receiverBalance.setAppAvblCnt(request.getRange());
            receiverBalance.setIssuedByType(appIssuedTypeRepository.findById(request.getIssuedToTypeId()).orElse(null));
            receiverBalance.setIsActive(1);
            receiverBalance.setCreatedBy(request.getCreatedBy());
        }
        balanceTrackRepository.save(receiverBalance);
    }
}