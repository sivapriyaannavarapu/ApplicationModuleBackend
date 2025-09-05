package com.application.controller;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.application.dto.UserAppSoldDTO;
import com.application.dto.ZonePerformanceDTO;
import com.application.service.UserAppSoldService;
 
@RestController
@RequestMapping("/api/analytics/graphs")
public class UserAppSoldController {
 
    @Autowired
    private UserAppSoldService userAppSoldService;
 
    @GetMapping("/entity/{entityId}")
    public List<UserAppSoldDTO> getAnalyticsByEntityId(@PathVariable Integer entityId) {
        return userAppSoldService.getAnalyticsByEntityId(entityId);
    }
 
    @GetMapping("/zones/top-rated")
    public List<ZonePerformanceDTO> getTopRatedZones() {
        return userAppSoldService.getTopRatedZones();
    }
 
    @GetMapping("/zones/drop-rated")
    public List<ZonePerformanceDTO> getDropRatedZones() {
        return userAppSoldService.getDropRatedZones();
    }
    
    @GetMapping("/dgms/top-rated")
    public List<ZonePerformanceDTO> getTopRatedDgms() {
        return userAppSoldService.getTopRatedDgms();
    }
 
    @GetMapping("/dgms/drop-rated")
    public List<ZonePerformanceDTO> getDropRatedDgms() {
        return userAppSoldService.getDropRatedDgms();
    }
    
    @GetMapping("/campus/top-rated")
    public List<ZonePerformanceDTO> getTopRatedCampus() {
        return userAppSoldService.getTopRatedCampus();
    }
 
    @GetMapping("/campus/drop-rated")
    public List<ZonePerformanceDTO> getDropRatedCampus() {
        return userAppSoldService.getDropRatedCampus();
    }
}