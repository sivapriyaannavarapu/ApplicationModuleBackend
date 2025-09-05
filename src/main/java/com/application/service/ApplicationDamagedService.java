//package com.application.service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional; // Import the Optional class
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.application.dto.ApplicationDamagedDto;
//import com.application.entity.AppStatus;
//import com.application.entity.AppStatusTrackView; // Import the new entity
//import com.application.entity.Campus;
//import com.application.entity.Dgm;
//import com.application.entity.Employee;
//import com.application.entity.Status;
//import com.application.entity.Zone;
//import com.application.repository.AppStatusRepository;
//import com.application.repository.AppStatusTrackViewRepository; // Import the new repository
//import com.application.repository.CampusRepository;
//import com.application.repository.DgmRepository;
//import com.application.repository.EmployeeRepository;
//import com.application.repository.StatusRepository;
//import com.application.repository.ZoneRepository;
//
//import jakarta.persistence.EntityNotFoundException;
//
//@Service
//public class ApplicationDamagedService {
//
//    @Autowired
//    private AppStatusRepository appStatusRepository;
//
//    @Autowired
//    private StatusRepository statusRepository;
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    @Autowired
//    private CampusRepository campusRepository;
//    
//    @Autowired
//    private ZoneRepository zoneRepository;
//
//    @Autowired
//    private AppStatusTrackViewRepository appStatusTrackViewRepository; // Autowire the new repository
//    
//    @Autowired
//    private DgmRepository dgmRepository;
//
//    // New method for autopopulation
//    public Optional<AppStatusTrackView> getDetailsByApplicationNo(int num) {
//        return appStatusTrackViewRepository.findById(num);
//    }
//    
//    public List<Employee> getAllProEmployees() {
//        // You'll need to implement a way to filter employees by their role.
//        // For example, if you have a 'role' field in your Employee entity,
//        // you could use: employeeRepository.findByRole("PRO");
//        // For now, we'll return all employees for demonstration.
//        return employeeRepository.findAll();
//    }
//
//    public List<Campus> getAllCampuses() {
//        return campusRepository.findAll();
//    }
//    
//    public List<Status> getAllStatus()
//    {
//    	return statusRepository.findAll();
//    }
//
//    @Transactional
//    public AppStatus saveApplicationStatus(ApplicationDamagedDto dto) {
//        
//        // Step 1: Fetch related entities using the IDs from the DTO.
//        Status status = statusRepository.findById(dto.getStatusId())
//                .orElseThrow(() -> new EntityNotFoundException("Status not found with ID: " + dto.getStatusId()));
//        
//        Campus campus = campusRepository.findById(dto.getCampusId())
//                .orElseThrow(() -> new EntityNotFoundException("Campus not found with ID: " + dto.getCampusId()));
//
//        Employee proEmployee = employeeRepository.findById(dto.getProId())
//                .orElseThrow(() -> new EntityNotFoundException("PRO Employee not found with ID: " + dto.getProId()));
//        
//        Zone zone = zoneRepository.findById(dto.getZoneId())
//        		.orElseThrow(() -> new EntityNotFoundException("Zone not found with ID: " + dto.getDgmEmpId()));
//
//        Employee dgmEmployee = employeeRepository.findById(dto.getDgmEmpId())
//                .orElseThrow(() -> new EntityNotFoundException("DGM Employee not found with ID: " + dto.getDgmEmpId()));
//
//        // Step 2: Create and populate the new AppStatus entity.
//        AppStatus newAppStatus = new AppStatus();
//        newAppStatus.setApp_no(dto.getApplicationNo());
//        newAppStatus.setReason(dto.getReason());
//        
//        // Set the relationships using the objects fetched above
//        newAppStatus.setStatus(status);
//        newAppStatus.setCampus(campus);
//        newAppStatus.setEmployee(proEmployee);     
//        newAppStatus.setZone(zone);   
//        newAppStatus.setEmployee2(dgmEmployee);    
//        newAppStatus.setStatus(status);
//        newAppStatus.setIs_active(1); // Assuming 1 means active
//        newAppStatus.setCreated_by(1); // TODO: Get this from security context (the logged-in user's ID)
//
//        // Step 3: Save the new entity to the database.
//        return appStatusRepository.save(newAppStatus);
//    }
//    
//    
//    
//    
//    public List<String> getDgmNamesByZoneId(int zoneId) {
//        // Fetch only the DGM records that belong to the specified zone
//        List<Dgm> dgmList = dgmRepository.findByZoneId(zoneId);
//        List<String> dgmNames = new ArrayList<>();
//
//        // Iterate and extract the full name from the associated Employee entity
//        for (Dgm dgm : dgmList) {
//            if (dgm.getEmployee() != null) {
//                String fullName = dgm.getEmployee().getFirst_name() + " " + dgm.getEmployee().getLast_name();
//                dgmNames.add(fullName);
//            }
//        }
//        return dgmNames;
//    }
//}


package com.application.service;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.dto.ApplicationDamagedDto;
import com.application.dto.EmployeeDto;
import com.application.entity.AppStatus;
import com.application.entity.AppStatusTrackView;
import com.application.entity.ApplicationStatus;
import com.application.entity.Campus;
import com.application.entity.CampusProView;
import com.application.entity.Dgm;
import com.application.entity.Employee;
import com.application.entity.Status;
import com.application.entity.Zone;
import com.application.repository.AppStatusRepository;
import com.application.repository.AppStatusTrackViewRepository;
import com.application.repository.ApplicationStatusRepository;
import com.application.repository.CampusProViewRepository;
import com.application.repository.CampusRepository;
import com.application.repository.DgmRepository;
import com.application.repository.EmployeeRepository;
import com.application.repository.StatusRepository;
import com.application.repository.ZoneRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
 
@Service
public class ApplicationDamagedService {
    @PersistenceContext
    private EntityManager entityManager;
 
    @Autowired
    private AppStatusRepository appStatusRepository;
 
    @Autowired
    private StatusRepository statusRepository;
 
    @Autowired
    private EmployeeRepository employeeRepository;
 
    @Autowired
    private CampusRepository campusRepository;
 
    @Autowired
    private CampusProViewRepository campusProViewRepository;
 
    @Autowired
    private AppStatusTrackViewRepository appStatusTrackViewRepository;
 
    @Autowired
    public ApplicationStatusRepository applicationStatusRepository;
 
    @Autowired
    public DgmRepository dgmRepository;
 
    @Autowired
    public ZoneRepository zoneRepository;
 
    // New method for autopopulation
    public Optional<AppStatusTrackView> getDetailsByApplicationNo(int num) {
        return appStatusTrackViewRepository.findById(num);
    }
 
    public List<Employee> getAllZoneEmployees() {
        return employeeRepository.findAll();
    }
 
    public List<Campus> getAllCampuses() {
        return campusRepository.findAll();
    }
 
    public List<ApplicationStatus> getAllStatus() {
        return applicationStatusRepository.findAll();
    }
 
    public List<Zone> getAllZones() {
        return zoneRepository.findAll();
    }
 
    @Transactional
    public AppStatus saveOrUpdateApplicationStatus(ApplicationDamagedDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("DTO cannot be null");
        }
 
        Optional<AppStatus> existingAppStatusOpt = dto.getApplicationNo() != null
            ? appStatusRepository.findByAppNo(dto.getApplicationNo())
            : Optional.empty();
        AppStatus appStatus = existingAppStatusOpt.orElse(new AppStatus());
 
        Status status = statusRepository.findById(dto.getStatusId())
                .orElseThrow(() -> new EntityNotFoundException("Status not found with ID: " + dto.getStatusId()));
 
        Campus campus = campusRepository.findById(dto.getCampusId())
                .orElseThrow(() -> new EntityNotFoundException("Campus not found with ID: " + dto.getCampusId()));
 
        Employee proEmployee = employeeRepository.findById(dto.getProId())
                .orElseThrow(() -> new EntityNotFoundException("PRO Employee not found with ID: " + dto.getProId()));
 
        Zone zone = zoneRepository.findById(dto.getZoneId())
                .orElseThrow(() -> new EntityNotFoundException("Zone not found with ID: " + dto.getZoneId()));
 
        Employee dgmEmployee = employeeRepository.findById(dto.getDgmEmpId())
                .orElseThrow(() -> new EntityNotFoundException("DGM Employee not found with ID: " + dto.getDgmEmpId()));

 
        appStatus.setApp_no(dto.getApplicationNo());
        appStatus.setReason(dto.getReason());
        appStatus.setStatus(status);
        appStatus.setCampus(campus);
        appStatus.setEmployee(proEmployee);
        appStatus.setZone(zone);
        appStatus.setEmployee2(dgmEmployee);
        appStatus.setCreated_by(2);
        appStatus.setUpdated_by(2);
        
        String statusName = status.getStatus_type(); 
        if ("Damaged".equalsIgnoreCase(statusName) || "Unavailable".equalsIgnoreCase(statusName)) {
            appStatus.setIs_active(1);
        } else {
            appStatus.setIs_active(0);
        }
        
        return appStatusRepository.save(appStatus);
    }
 
    public List<EmployeeDto> getDgmNamesByZoneId(int zoneId) {
        List<Dgm> dgmList = dgmRepository.findByZoneId(zoneId);
        List<EmployeeDto> dgmEmployees = new ArrayList<>();
 
        for (Dgm dgm : dgmList) {
            if (dgm.getEmployee() != null) {
                EmployeeDto dto = new EmployeeDto();
                dto.setEmpId(dgm.getEmployee().getEmp_id());
                dto.setName(dgm.getEmployee().getFirst_name() + " " + dgm.getEmployee().getLast_name());
                dgmEmployees.add(dto);
            }
        }
        return dgmEmployees;
    }
 
    @Transactional(readOnly = true)
    public List<EmployeeDto> getEmployeeNamesByCampusId(int campusId) {
        entityManager.clear();
        List<CampusProView> views = campusProViewRepository.findByCampusId(campusId);
        System.out.println("Fetched CampusProView records: " + views.size());
        views.forEach(v -> System.out.println("cmps_id: " + v.getCmps_id() + ", emp_id: " + v.getEmp_id() + ", is_active: " + v.getIs_active()));
        return views.stream()
                .map(view -> {
                    int empId = view.getEmp_id();
                    Employee employee = employeeRepository.findById(empId).orElse(null);
                    if (employee != null && employee.getEmp_id() == empId && employee.getFirst_name() != null && employee.getLast_name() != null) {
                        EmployeeDto dto = new EmployeeDto();
                        dto.setEmpId(empId);
                        dto.setName(employee.getFirst_name() + " " + employee.getLast_name());
                        System.out.println("Debug - emp_id: " + empId + ", name: " + dto.getName() + ", hashCode: " + System.identityHashCode(employee));
                        return dto;
                    }
                    System.out.println("Debug - emp_id: " + empId + " not found or invalid");
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}