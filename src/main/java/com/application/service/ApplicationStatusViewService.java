package com.application.service;
 
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.dto.ApplicationStatusViewDTO;
import com.application.entity.AppStatusTrackView;
import com.application.repository.AppStatusTrackViewRepository;
 
@Service
public class ApplicationStatusViewService {
 
    @Autowired
    private AppStatusTrackViewRepository appStatusTrackViewRepository;
 
    public List<ApplicationStatusViewDTO> getApplicationStatusViewData() {
        // Retrieve all entities from the repository
        List<AppStatusTrackView> entities = appStatusTrackViewRepository.findAll();
 
        // Use Java Streams to map each entity to the DTO
        return entities.stream().map(entity -> {
            ApplicationStatusViewDTO dto = new ApplicationStatusViewDTO();
            // Note: The 'applicationNo' field is not present in the entity, assuming it's 'num'.
            dto.setApplicationNo(entity.getNum());
            dto.setPro(entity.getPro_name());
            dto.setCampus(entity.getCmps_name());
            dto.setDgm(entity.getDgm_name());
            dto.setZone(entity.getZonal_name());

            dto.setDate(entity.getDate()); // Assuming date is present in entity
            dto.setStatus(entity.getStatus());
            return dto;
        }).collect(Collectors.toList());
    }
}