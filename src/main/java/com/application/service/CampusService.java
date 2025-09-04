package com.application.service;
 
import com.application.dto.AppNumberRangeDTO;
import com.application.dto.DgmToCampusFormDTO;
import com.application.dto.GenericDropdownDTO;
import com.application.entity.*;
import com.application.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
 
@Service
public class CampusService {
 
    @Autowired private AcademicYearRepository academicYearRepository;
    @Autowired private StateRepository stateRepository;
    @Autowired private DistrictRepository districtRepository;
    @Autowired private CityRepository cityRepository;
    @Autowired private CampusRepository campusRepository;
    @Autowired private CampaignRepository campaignRepository;
    @Autowired private AppIssuedTypeRepository appIssuedTypeRepository;
    @Autowired private DistributionRepository distributionRepository;
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private BalanceTrackRepository balanceTrackRepository;
    @Autowired private CampusProViewRepository campusProViewRepository;
 
    // --- Dropdown Logic ---
    public List<GenericDropdownDTO> getAllAcademicYears() {
        return academicYearRepository.findAll().stream()
                .map(y -> new GenericDropdownDTO(y.getAcdcYearId(), y.getAcademicYear())).collect(Collectors.toList());
    }
    public List<GenericDropdownDTO> getAllStates() {
        return stateRepository.findAll().stream()
                .map(s -> new GenericDropdownDTO(s.getStateId(), s.getStateName())).collect(Collectors.toList());
    }
    
    public List<District> getAllDistricts()
    {
    	return districtRepository.findAll();
    }
    
    public List<GenericDropdownDTO> getDistrictsByStateId(int stateId) {
        return districtRepository.findByStateStateId(stateId).stream()
                .map(d -> new GenericDropdownDTO(d.getDistrictId(), d.getDistrictName())).collect(Collectors.toList());
    }
    public List<GenericDropdownDTO> getCitiesByDistrictId(int districtId) {
        return cityRepository.findByDistrictDistrictId(districtId).stream()
                .map(c -> new GenericDropdownDTO(c.getCityId(), c.getCityName())).collect(Collectors.toList());
    }
    public List<GenericDropdownDTO> getCampusesByCityId(int cityId) {
        return campusRepository.findByCityCityId(cityId).stream()
                .map(c -> new GenericDropdownDTO(c.getCampusId(), c.getCampusName())).collect(Collectors.toList());
    }
    public List<GenericDropdownDTO> getAllCampaignAreas() {
        return campaignRepository.findAll().stream()
                .map(c -> new GenericDropdownDTO(c.getCampaignId(), c.getAreaName())).collect(Collectors.toList());
    }
    public List<GenericDropdownDTO> getProsByCampusId(int campusId) {
        List<Integer> employeeIds = campusProViewRepository.findByCampusId(campusId).stream()
                                                  .map(CampusProView::getEmp_id)
                                                  .collect(Collectors.toList());
        if (employeeIds.isEmpty()) {
            return List.of();
        }
        return employeeRepository.findAllById(employeeIds).stream()
                .map(employee -> new GenericDropdownDTO(employee.getEmp_id(), employee.getFirst_name() + " " + employee.getLast_name()))
                .collect(Collectors.toList());
    }
    public List<GenericDropdownDTO> getAllIssuedToTypes() {
        return appIssuedTypeRepository.findAll().stream()
                .map(t -> new GenericDropdownDTO(t.getAppIssuedId(), t.getTypeName())).collect(Collectors.toList());
    }
    public List<AppNumberRangeDTO> getAvailableAppNumberRanges(int employeeId, int academicYearId) {
        return balanceTrackRepository.findAppNumberRanges(academicYearId, employeeId).stream()
                .map(r -> new AppNumberRangeDTO(r.getAppBalanceTrkId(), r.getAppFrom(), r.getAppTo())).collect(Collectors.toList());
    }
    public String getMobileNumberByEmpId(int empId) {
        return employeeRepository.findMobileNoByEmpId(empId);
    }
    
    private int getDgmUserTypeId() {
        // TODO: Implement logic to find the DGM's type ID based on their role
        return 3; // Hardcoding '3' for DGM as an example
    }
    
    // --- Form Submission Logic ---
    @Transactional
    public void submitDgmToCampusForm(DgmToCampusFormDTO formDto) {
        int dgmUserId = formDto.getUserId();
        int proEmployeeId = formDto.getProEmployeeId();
        int dgmUserTypeId = getDgmUserTypeId();
        int appNoFrom = Integer.parseInt(formDto.getApplicationNoFrom());
        int appNoTo = Integer.parseInt(formDto.getApplicationNoTo());
 
        // --- 1. Update the DGM's (Issuer's) Balance ---
        BalanceTrack dgmBalance = balanceTrackRepository.findById(formDto.getSelectedBalanceTrackId())
                .orElseThrow(() -> new RuntimeException("The selected application range for the DGM was not found."));
        
        if (appNoFrom < dgmBalance.getAppFrom() || appNoTo > dgmBalance.getAppTo()) {
            throw new IllegalStateException("The submitted application number range is outside the DGM's available range.");
        }
        
        dgmBalance.setAppAvblCnt(dgmBalance.getAppAvblCnt() - formDto.getRange());
        dgmBalance.setAppFrom(appNoTo + 1);
        balanceTrackRepository.save(dgmBalance);
 
        // --- 2. Create or Update the PRO's (Receiver's) Balance ---
        Optional<BalanceTrack> proBalanceOpt = balanceTrackRepository.findBalanceTrack(formDto.getAcademicYearId(), proEmployeeId);
        BalanceTrack proBalance;
        if (proBalanceOpt.isPresent()) {
            proBalance = proBalanceOpt.get();
            proBalance.setAppAvblCnt(proBalance.getAppAvblCnt() + formDto.getRange());
            proBalance.setAppTo(appNoTo);
        } else {
            proBalance = new BalanceTrack();
            Employee proEmployee = employeeRepository.findById(proEmployeeId)
                    .orElseThrow(() -> new RuntimeException("Receiver PRO employee not found for ID: " + proEmployeeId));
            proBalance.setEmployee(proEmployee);
            proBalance.setAcademicYear(academicYearRepository.findById(formDto.getAcademicYearId()).orElse(null));
            proBalance.setAppFrom(appNoFrom);
            proBalance.setAppTo(appNoTo);
            proBalance.setAppAvblCnt(formDto.getRange());
            proBalance.setIssuedByType(appIssuedTypeRepository.findById(formDto.getIssuedToId()).orElse(null));
            proBalance.setIsActive(1);
            proBalance.setCreatedBy(dgmUserId);
        }
        balanceTrackRepository.save(proBalance);
 
        // --- 3. Save the Distribution Record ---
        Distribution distribution = new Distribution();
        
        academicYearRepository.findById(formDto.getAcademicYearId()).ifPresent(distribution::setAcademicYear);
        districtRepository.findById(formDto.getDistrictId()).ifPresent(distribution::setDistrict);
        cityRepository.findById(formDto.getCityId()).ifPresent(distribution::setCity);
        campusRepository.findById(formDto.getCampusId()).ifPresent(distribution::setCampus);
//        campaignRepository.findById(formDto.getCampaignId()).ifPresent(distribution::setCampaign);
        appIssuedTypeRepository.findById(dgmUserTypeId).ifPresent(distribution::setIssuedByType);
        appIssuedTypeRepository.findById(formDto.getIssuedToId()).ifPresent(distribution::setIssuedToType);
        
        Campus selectedCampus = campusRepository.findById(formDto.getCampusId()).orElse(null);
        distribution.setCampus(selectedCampus);
 
        // Now, if the campus was found, get its parent Zone and set it
        if (selectedCampus != null) {
            distribution.setZone(selectedCampus.getZone());
        }
 
        // This logic correctly derives the State from the selected District
        if (distribution.getDistrict() != null) {
            distribution.setState(distribution.getDistrict().getState());
        }
        
        distribution.setAppStartNo(appNoFrom);
        distribution.setAppEndNo(appNoTo);
        distribution.setTotalAppCount(formDto.getRange());
        distribution.setIssueDate(LocalDate.now());
        distribution.setIsActive(1);
        distribution.setCreated_by(dgmUserId);
        distribution.setIssued_to_emp_id(proEmployeeId);
        
        distributionRepository.save(distribution);
    }
    
    //update
    @Transactional
    public void updateDgmToCampusForm(int distributionId, DgmToCampusFormDTO formDto) {
        // --- 1. Fetch Existing Records ---
        Distribution oldDistribution = distributionRepository.findById(distributionId)
                .orElseThrow(() -> new RuntimeException("Original distribution record not found for ID: " + distributionId));
 
        int oldProId = oldDistribution.getIssued_to_emp_id();
        int oldAppStartNo = oldDistribution.getAppStartNo();
        int oldAppEndNo = oldDistribution.getAppEndNo();
        int oldRange = oldAppEndNo - oldAppStartNo + 1;
 
        // --- 2. Revert the Original Transaction ---
        // Return the original range to the DGM's balance
        BalanceTrack dgmBalance = balanceTrackRepository.findById(formDto.getSelectedBalanceTrackId())
                .orElseThrow(() -> new RuntimeException("DGM's balance track not found."));
        dgmBalance.setAppAvblCnt(dgmBalance.getAppAvblCnt() + oldRange);
        dgmBalance.setAppFrom(oldAppStartNo); // Revert the 'from' number to the original start
        balanceTrackRepository.save(dgmBalance);
 
        // Revert the original PRO's balance
        BalanceTrack oldProBalance = balanceTrackRepository.findBalanceTrack(oldDistribution.getAcademicYear().getAcdcYearId(), oldProId)
                .orElseThrow(() -> new RuntimeException("Original PRO's balance track not found."));
        oldProBalance.setAppAvblCnt(oldProBalance.getAppAvblCnt() - oldRange);
        oldProBalance.setAppTo(oldAppStartNo - 1);
        balanceTrackRepository.save(oldProBalance);
 
        // --- 3. Apply the New Transaction (same logic as submitDgmToCampusForm) ---
        int dgmUserId = formDto.getUserId();
        int newProEmployeeId = formDto.getProEmployeeId();
        int dgmUserTypeId = getDgmUserTypeId();
        int newAppNoFrom = Integer.parseInt(formDto.getApplicationNoFrom());
        int newAppNoTo = Integer.parseInt(formDto.getApplicationNoTo());
        int newRange = formDto.getRange();
 
        // Check if the new range is within the DGM's new available range
        if (newAppNoFrom < dgmBalance.getAppFrom() || newAppNoTo > dgmBalance.getAppTo()) {
            throw new IllegalStateException("The new application number range is outside the DGM's available range after reversion.");
        }
 
        dgmBalance.setAppAvblCnt(dgmBalance.getAppAvblCnt() - newRange);
        dgmBalance.setAppFrom(newAppNoTo + 1);
        balanceTrackRepository.save(dgmBalance);
 
        // Create or Update the new PRO's balance
        Optional<BalanceTrack> newProBalanceOpt = balanceTrackRepository.findBalanceTrack(formDto.getAcademicYearId(), newProEmployeeId);
        BalanceTrack newProBalance;
        if (newProBalanceOpt.isPresent()) {
            newProBalance = newProBalanceOpt.get();
            newProBalance.setAppAvblCnt(newProBalance.getAppAvblCnt() + newRange);
            newProBalance.setAppTo(newAppNoTo);
        } else {
            newProBalance = new BalanceTrack();
            Employee newProEmployee = employeeRepository.findById(newProEmployeeId)
                    .orElseThrow(() -> new RuntimeException("New Receiver PRO employee not found for ID: " + newProEmployeeId));
            newProBalance.setEmployee(newProEmployee);
            newProBalance.setAcademicYear(academicYearRepository.findById(formDto.getAcademicYearId()).orElse(null));
            newProBalance.setAppFrom(newAppNoFrom);
            newProBalance.setAppTo(newAppNoTo);
            newProBalance.setAppAvblCnt(newRange);
            newProBalance.setIssuedByType(appIssuedTypeRepository.findById(formDto.getIssuedToId()).orElse(null));
            newProBalance.setIsActive(1);
            newProBalance.setCreatedBy(dgmUserId);
        }
        balanceTrackRepository.save(newProBalance);
 
        // --- 4. Update the Distribution Record ---
        oldDistribution.setAppStartNo(newAppNoFrom);
        oldDistribution.setAppEndNo(newAppNoTo);
        oldDistribution.setTotalAppCount(newRange);
        oldDistribution.setIssued_to_emp_id(newProEmployeeId);
        oldDistribution.setCreated_by(dgmUserId);
        oldDistribution.setIssueDate(LocalDate.now()); // Update the issue date
       
        // Update other fields as needed
        academicYearRepository.findById(formDto.getAcademicYearId()).ifPresent(oldDistribution::setAcademicYear);
        districtRepository.findById(formDto.getDistrictId()).ifPresent(oldDistribution::setDistrict);
        cityRepository.findById(formDto.getCityId()).ifPresent(oldDistribution::setCity);
       
        Campus selectedCampus = campusRepository.findById(formDto.getCampusId()).orElse(null);
        oldDistribution.setCampus(selectedCampus);
 
        if (selectedCampus != null) {
            oldDistribution.setZone(selectedCampus.getZone());
        }
       
        if (oldDistribution.getDistrict() != null) {
            oldDistribution.setState(oldDistribution.getDistrict().getState());
        }
 
        appIssuedTypeRepository.findById(dgmUserTypeId).ifPresent(oldDistribution::setIssuedByType);
        appIssuedTypeRepository.findById(formDto.getIssuedToId()).ifPresent(oldDistribution::setIssuedToType);
 
        distributionRepository.save(oldDistribution);
    }
 
}