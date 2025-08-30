package com.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.dto.ApplicationDamagedDto;
import com.application.entity.AppStatus;
import com.application.repository.AppStatusRepository;
import com.application.repository.CampusRepository;
import com.application.repository.EmployeeRepository;
import com.application.repository.StatusRepository;

@Service
public class ApplicationDamagedService {

    @Autowired
    private AppStatusRepository appStatusRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private StatusRepository statusRepo;

    @Autowired
    private CampusRepository campusRepo;

    public AppStatus saveAppStatus(ApplicationDamagedDto dto) {
        AppStatus appStatus = new AppStatus();

        appStatus.setApp_no(dto.getAppNo());
        appStatus.setReason(dto.getReason());
        appStatus.setIs_active(1); // default active
        appStatus.setCreated_by(dto.getCreatedBy());

        // Relations (fetching by IDs)
        appStatus.setEmployee(employeeRepo.findById(dto.getProId()).orElse(null));
        appStatus.setEmployee1(employeeRepo.findById(dto.getZoneEmpId()).orElse(null));
        appStatus.setEmployee2(employeeRepo.findById(dto.getDgmEmpId()).orElse(null));
        appStatus.setStatus(statusRepo.findById(dto.getStatusId()).orElse(null));
        appStatus.setCampus(campusRepo.findById(dto.getCampusId()).orElse(null));

        return appStatusRepo.save(appStatus);
    }
}
