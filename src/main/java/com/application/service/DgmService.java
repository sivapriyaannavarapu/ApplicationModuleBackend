package com.application.service;
 
import com.application.dto.AppNumberRangeDTO;
import com.application.dto.FormSubmissionDTO;
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
public class DgmService {
 
    @Autowired
    private AcademicYearRepository academicYearRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private ZoneRepository zoneRepository;
    @Autowired
    private CampusRepository campusRepository;
    @Autowired
    private AppIssuedTypeRepository appIssuedTypeRepository;
    @Autowired
    private DistributionRepository distributionRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private BalanceTrackRepository balanceTrackRepository;
 
    public List<GenericDropdownDTO> getAllAcademicYears() {
        return academicYearRepository.findAll().stream()
                .map(year -> new GenericDropdownDTO(year.getAcdcYearId(), year.getAcademicYear()))
                .collect(Collectors.toList());
    }
 
    public List<GenericDropdownDTO> getAllCities() {
        return cityRepository.findAll().stream()
                .map(city -> new GenericDropdownDTO(city.getCityId(), city.getCityName()))
                .collect(Collectors.toList());
    }
 
    public List<GenericDropdownDTO> getZonesByCityId(int cityId) {
        return zoneRepository.findByCityCityId(cityId).stream()
                .map(zone -> new GenericDropdownDTO(zone.getZoneId(), zone.getZoneName()))
                .collect(Collectors.toList());
    }
 
    public List<GenericDropdownDTO> getCampusesByZoneId(int zoneId) {
        // You'll need to add 'findByZoneZoneId' to your CampusRepository
        return campusRepository.findByZoneZoneId(zoneId).stream()
                .map(campus -> new GenericDropdownDTO(campus.getCampusId(), campus.getCampusName()))
                .collect(Collectors.toList());
    }
 
    public List<GenericDropdownDTO> getAllIssuedToTypes() {
        return appIssuedTypeRepository.findAll().stream()
                .map(type -> new GenericDropdownDTO(type.getAppIssuedId(), type.getTypeName()))
                .collect(Collectors.toList());
    }
 
    public List<AppNumberRangeDTO> getAvailableAppNumberRanges(int academicYearId, int employeeId) {
        // You'll need to add 'findAppNumberRanges' to your BalanceTrackRepository
        return balanceTrackRepository.findAppNumberRanges(academicYearId, employeeId).stream()
                .map(range -> new AppNumberRangeDTO(range.getAppBalanceTrkId(), range.getAppFrom(), range.getAppTo()))
                .collect(Collectors.toList());
    }
 
    public String getMobileNumberByEmpId(int empId) {
        // You'll need to add 'findMobileNoByEmpId' to your EmployeeRepository
        return employeeRepository.findMobileNoByEmpId(empId);
    }
    
    private int getIssuedTypeByUserId(int userId) {
        // TODO: Implement logic to find the issuer's type ID based on their role
        // For now, hardcoding '2' for Zonal Officer as an example
        return 2;
    }
    
    @Transactional
    public void submitForm(FormSubmissionDTO formDto) {
        int issuerUserId = formDto.getUserId(); // This is the Zonal Officer
        int receiverEmpId = formDto.getDgmEmployeeId(); // This is the DGM
        int issuedById = getIssuedTypeByUserId(issuerUserId); // Get issuer's role ID
        int appNoFrom = Integer.parseInt(formDto.getApplicationNoFrom());
        int appNoTo = Integer.parseInt(formDto.getApplicationNoTo());
 
        // --- Part 1: Update the Zonal Officer's (Issuer's) Balance ---
        BalanceTrack issuerBalance = balanceTrackRepository.findById(formDto.getSelectedBalanceTrackId())
                .orElseThrow(() -> new RuntimeException("The selected application range for the issuer was not found."));
 
        // Validate the submitted range is within the issuer's selected balance track
        if (appNoFrom < issuerBalance.getAppFrom() || appNoTo > issuerBalance.getAppTo()) {
            throw new IllegalStateException("The submitted application number range is outside the issuer's available range.");
        }
        
        // This simple logic assumes ranges are used sequentially from the start.
        // A more complex system could split the BalanceTrack record into two if a middle chunk is taken.
        issuerBalance.setAppAvblCnt(issuerBalance.getAppAvblCnt() - formDto.getRange());
        issuerBalance.setAppFrom(appNoTo + 1);
        balanceTrackRepository.save(issuerBalance);
 
        // --- Part 2: Create or Update the DGM's (Receiver's) Balance ---
        Optional<BalanceTrack> existingReceiverBalanceOpt = balanceTrackRepository.findBalanceTrack(formDto.getAcademicYearId(), receiverEmpId);
        BalanceTrack receiverBalance;
        if (existingReceiverBalanceOpt.isPresent()) {
            // If the DGM's balance exists, UPDATE it
            receiverBalance = existingReceiverBalanceOpt.get();
            receiverBalance.setAppAvblCnt(receiverBalance.getAppAvblCnt() + formDto.getRange());
            receiverBalance.setAppTo(appNoTo);
        } else {
            // If the DGM's balance does NOT exist, CREATE it
            receiverBalance = new BalanceTrack();
            Employee receiverEmployee = employeeRepository.findById(receiverEmpId)
                    .orElseThrow(() -> new RuntimeException("Receiver DGM employee not found for ID: " + receiverEmpId));
            
            receiverBalance.setEmployee(receiverEmployee);
            receiverBalance.setAcademicYear(academicYearRepository.findById(formDto.getAcademicYearId()).orElse(null));
            receiverBalance.setAppFrom(appNoFrom);
            receiverBalance.setAppTo(appNoTo);
            receiverBalance.setAppAvblCnt(formDto.getRange());
            receiverBalance.setIssuedByType(appIssuedTypeRepository.findById(formDto.getIssuedToId()).orElse(null));
            receiverBalance.setIsActive(1);
            receiverBalance.setCreatedBy(issuerUserId);
        }
        balanceTrackRepository.save(receiverBalance);
 
        // --- Part 3: Save the Distribution Record ---
        Distribution distribution = new Distribution();
        
        academicYearRepository.findById(formDto.getAcademicYearId()).ifPresent(distribution::setAcademicYear);
        zoneRepository.findById(formDto.getZoneId()).ifPresent(distribution::setZone);
        campusRepository.findById(formDto.getCampusId()).ifPresent(distribution::setCampus);
        cityRepository.findById(formDto.getCityId()).ifPresent(city -> {
            distribution.setCity(city);
            if (city.getDistrict() != null) {
                distribution.setDistrict(city.getDistrict());
                if (city.getDistrict().getState() != null) {
                    distribution.setState(city.getDistrict().getState());
                }
            }
        });
        
        appIssuedTypeRepository.findById(issuedById).ifPresent(distribution::setIssuedByType);
        appIssuedTypeRepository.findById(formDto.getIssuedToId()).ifPresent(distribution::setIssuedToType);
        
        distribution.setAppStartNo(appNoFrom);
        distribution.setAppEndNo(appNoTo);
        distribution.setTotalAppCount(formDto.getRange());
        distribution.setIssueDate(LocalDate.now());
        distribution.setIsActive(1);
        distribution.setCreated_by(issuerUserId);
        distribution.setIssued_to_emp_id(receiverEmpId);
        
        distributionRepository.save(distribution);
    }
}