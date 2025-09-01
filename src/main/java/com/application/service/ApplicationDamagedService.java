package com.application.service;

import java.util.List;
import java.util.Optional; // Import the Optional class

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.dto.ApplicationDamagedDto;
import com.application.entity.AppStatus;
import com.application.entity.AppStatusTrackView; // Import the new entity
import com.application.entity.Campus;
import com.application.entity.Employee;
import com.application.entity.Status;
import com.application.repository.AppStatusRepository;
import com.application.repository.AppStatusTrackViewRepository; // Import the new repository
import com.application.repository.CampusRepository;
import com.application.repository.EmployeeRepository;
import com.application.repository.StatusRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ApplicationDamagedService {

    @Autowired
    private AppStatusRepository appStatusRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CampusRepository campusRepository;

    @Autowired
    private AppStatusTrackViewRepository appStatusTrackViewRepository; // Autowire the new repository

    // New method for autopopulation
    public Optional<AppStatusTrackView> getDetailsByApplicationNo(int num) {
        return appStatusTrackViewRepository.findById(num);
    }
    
    public List<Employee> getAllProEmployees() {
        // You'll need to implement a way to filter employees by their role.
        // For example, if you have a 'role' field in your Employee entity,
        // you could use: employeeRepository.findByRole("PRO");
        // For now, we'll return all employees for demonstration.
        return employeeRepository.findAll();
    }

    public List<Employee> getAllZoneEmployees() {
        // Similarly, filter by 'ZONE' role
        return employeeRepository.findAll();
    }

    public List<Employee> getAllDgmEmployees() {
        // Similarly, filter by 'DGM' role
        return employeeRepository.findAll();
    }

    public List<Campus> getAllCampuses() {
        return campusRepository.findAll();
    }
    
    public List<Status> getAllStatus()
    {
    	return statusRepository.findAll();
    }

    @Transactional
    public AppStatus saveApplicationStatus(ApplicationDamagedDto dto) {
        
        // Step 1: Fetch related entities using the IDs from the DTO.
        Status status = statusRepository.findById(dto.getStatusId())
                .orElseThrow(() -> new EntityNotFoundException("Status not found with ID: " + dto.getStatusId()));
        
        Campus campus = campusRepository.findById(dto.getCampusId())
                .orElseThrow(() -> new EntityNotFoundException("Campus not found with ID: " + dto.getCampusId()));

        Employee proEmployee = employeeRepository.findById(dto.getProId())
                .orElseThrow(() -> new EntityNotFoundException("PRO Employee not found with ID: " + dto.getProId()));
        
        Employee zoneEmployee = employeeRepository.findById(dto.getZoneEmpId())
                .orElseThrow(() -> new EntityNotFoundException("Zone Employee not found with ID: " + dto.getZoneEmpId()));

        Employee dgmEmployee = employeeRepository.findById(dto.getDgmEmpId())
                .orElseThrow(() -> new EntityNotFoundException("DGM Employee not found with ID: " + dto.getDgmEmpId()));

        // Step 2: Create and populate the new AppStatus entity.
        AppStatus newAppStatus = new AppStatus();
        newAppStatus.setApp_no(dto.getApplicationNo());
        newAppStatus.setReason(dto.getReason());
        
        // Set the relationships using the objects fetched above
        newAppStatus.setStatus(status);
        newAppStatus.setCampus(campus);
        newAppStatus.setEmployee(proEmployee);     
        newAppStatus.setEmployee1(zoneEmployee);   
        newAppStatus.setEmployee2(dgmEmployee);    
        newAppStatus.setStatus(status);
        newAppStatus.setIs_active(1); // Assuming 1 means active
        newAppStatus.setCreated_by(1); // TODO: Get this from security context (the logged-in user's ID)

        // Step 3: Save the new entity to the database.
        return appStatusRepository.save(newAppStatus);
    }
}