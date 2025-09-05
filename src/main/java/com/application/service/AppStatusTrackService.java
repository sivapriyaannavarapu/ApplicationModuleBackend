package com.application.service;
 
import com.application.dto.AppStatusTrackDTO;
import com.application.dto.MetricCardDTO;
import com.application.repository.AppStatusTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
@Service
public class AppStatusTrackService {
 
    @Autowired
    private AppStatusTrackRepository appStatusTrackRepository;
 
    public List<MetricCardDTO> getDashboardCards() {
        
        Optional<AppStatusTrackDTO> currentStatsOptional = appStatusTrackRepository.findLatestAggregatedStats();

        AppStatusTrackDTO currentStats = currentStatsOptional.orElse(new AppStatusTrackDTO());
 
        List<MetricCardDTO> cards = new ArrayList<>();

        cards.add(new MetricCardDTO("Total Applications", Math.toIntExact(currentStats.getTotalApplications() != null ? currentStats.getTotalApplications() : 0L), 0, "total_applications"));
        cards.add(new MetricCardDTO("Sold", Math.toIntExact(currentStats.getAppSold() != null ? currentStats.getAppSold() : 0L), 0, "sold"));
        cards.add(new MetricCardDTO("Confirmed", Math.toIntExact(currentStats.getAppConfirmed() != null ? currentStats.getAppConfirmed() : 0L), 0, "confirmed"));
        cards.add(new MetricCardDTO("Available", Math.toIntExact(currentStats.getAppAvailable() != null ? currentStats.getAppAvailable() : 0L), 0, "available"));
        cards.add(new MetricCardDTO("Issued", Math.toIntExact(currentStats.getAppIssued() != null ? currentStats.getAppIssued() : 0L), 0, "issued"));
        cards.add(new MetricCardDTO("Damaged", Math.toIntExact(currentStats.getAppDamaged() != null ? currentStats.getAppDamaged() : 0L), 0, "damaged"));
        cards.add(new MetricCardDTO("Unavailable", Math.toIntExact(currentStats.getAppUnavailable() != null ? currentStats.getAppUnavailable() : 0L), 0, "unavailable"));
 
        return cards;
    }
    
    public AppStatusTrackDTO getAppStatusByIssuedTypeAndEmployee(Integer issuedTypeId, Integer empId) {
        Optional<AppStatusTrackDTO> statsOptional = appStatusTrackRepository.findAggregatedStatsByIssuedTypeAndEmployee(issuedTypeId, empId);

        return statsOptional.orElse(new AppStatusTrackDTO());
    }
    
    
}