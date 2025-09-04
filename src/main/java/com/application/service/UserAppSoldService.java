package com.application.service;
 
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.application.dto.UserAppSoldDTO;
import com.application.dto.ZonePerformanceDTO;
import com.application.entity.UserAppSold;
import com.application.repository.UserAppSoldRepository;
 
@Service
public class UserAppSoldService {
 
    @Autowired
    private UserAppSoldRepository userAppSoldRepository;
 
    public List<UserAppSoldDTO> getAnalyticsByEntityId(Integer entityId) {
        List<UserAppSold> analyticsData = userAppSoldRepository.findByEntityId(entityId);
        return analyticsData.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
 
    private UserAppSoldDTO convertToDto(UserAppSold userAppSold) {
        UserAppSoldDTO dto = new UserAppSoldDTO();
        dto.setEmpId(userAppSold.getEmpId());
        dto.setEntityId(userAppSold.getEntityId());
        dto.setAcdcYearId(userAppSold.getAcdcYearId());
        dto.setTotalAppCount(userAppSold.getTotalAppCount());
        dto.setSold(userAppSold.getSold());
        return dto;
    }
 
    public List<ZonePerformanceDTO> getTopRatedZones() {
        List<Object[]> rawData = userAppSoldRepository.findTopRatedZones();
        return rawData.stream()
                .limit(4)
                .map(result -> {
                    ZonePerformanceDTO dto = new ZonePerformanceDTO();
                    dto.setEntityId((Integer) result[0]);
                    dto.setPerformancePercentage(((BigDecimal) result[1]).doubleValue());
                    return dto;
                })
                .collect(Collectors.toList());
    }
 
    public List<ZonePerformanceDTO> getDropRatedZones() {
        List<Object[]> rawData = userAppSoldRepository.findDropRatedZones();
        return rawData.stream()
                .limit(4)
                .map(result -> {
                    ZonePerformanceDTO dto = new ZonePerformanceDTO();
                    dto.setEntityId((Integer) result[0]);
                    dto.setPerformancePercentage(((BigDecimal) result[1]).doubleValue());
                    return dto;
                })
                .collect(Collectors.toList());
    }
 
    public List<ZonePerformanceDTO> getTopRatedDgms() {
        List<Object[]> rawData = userAppSoldRepository.findTopRatedDgms();
        return rawData.stream()
                .limit(4)
                .map(result -> {
                    ZonePerformanceDTO dto = new ZonePerformanceDTO();
                    dto.setEntityId((Integer) result[0]);
                    dto.setPerformancePercentage(((BigDecimal) result[1]).doubleValue());
                    return dto;
                })
                .collect(Collectors.toList());
    }
 
    public List<ZonePerformanceDTO> getDropRatedDgms() {
        List<Object[]> rawData = userAppSoldRepository.findDropRatedDgms();
        return rawData.stream()
                .limit(4)
                .map(result -> {
                    ZonePerformanceDTO dto = new ZonePerformanceDTO();
                    dto.setEntityId((Integer) result[0]);
                    dto.setPerformancePercentage(((BigDecimal) result[1]).doubleValue());
                    return dto;
                })
                .collect(Collectors.toList());
    }
 
    public List<ZonePerformanceDTO> getTopRatedCampus() {
        List<Object[]> rawData = userAppSoldRepository.findDropRatedCampus();
        return rawData.stream()
                .limit(4)
                .map(result -> {
                    ZonePerformanceDTO dto = new ZonePerformanceDTO();
                    dto.setEntityId((Integer) result[0]);
                    dto.setPerformancePercentage(((BigDecimal) result[1]).doubleValue());
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    // Corrected method for drop-rated campuses
    public List<ZonePerformanceDTO> getDropRatedCampus() {
        List<Object[]> rawData = userAppSoldRepository.findTopRatedCampus();
        return rawData.stream()
                .limit(4)
                .map(result -> {
                    ZonePerformanceDTO dto = new ZonePerformanceDTO();
                    dto.setEntityId((Integer) result[0]);
                    dto.setPerformancePercentage(((BigDecimal) result[1]).doubleValue());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}