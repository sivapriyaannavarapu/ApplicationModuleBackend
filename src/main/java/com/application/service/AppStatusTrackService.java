package com.application.service;
 
import com.application.dto.MetricCardDTO;
import com.application.entity.AppStatusTrack;
import com.application.repository.AppStatusTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
 
@Service
public class AppStatusTrackService {
 
    @Autowired
    private AppStatusTrackRepository appStatusTrackRepository;
 
    public List<MetricCardDTO> getDashboardCards() {
        // Fetch the latest 2 records from the database
        List<AppStatusTrack> stats = appStatusTrackRepository.findTop2ByOrderByAppStatsTrkIdDesc();
 
        if (stats.size() < 2) {
            // Not enough data to calculate percentages, return an empty list
            return new ArrayList<>();
        }
 
        AppStatusTrack currentStats = stats.get(0);
        AppStatusTrack previousStats = stats.get(1);
        List<MetricCardDTO> cards = new ArrayList<>();
 
        // Helper function to calculate percentage change and handle division by zero
        BiFunction<Integer, Integer, Integer> calculatePercentage = (current, previous) -> {
            if (previous == 0) {
                return 0; // Avoid division by zero
            }
            return (int) (((double) (current - previous) / previous) * 100);
        };
 
        // Map each metric to a DTO with its calculated percentage
        cards.add(new MetricCardDTO("Total Applications", currentStats.getTotalApp(), calculatePercentage.apply(currentStats.getTotalApp(), previousStats.getTotalApp()), "total_applications"));
        cards.add(new MetricCardDTO("Sold", currentStats.getAppSold(), calculatePercentage.apply(currentStats.getAppSold(), previousStats.getAppSold()), "sold"));
        cards.add(new MetricCardDTO("Confirmed", currentStats.getAppConfirmed(), calculatePercentage.apply(currentStats.getAppConfirmed(), previousStats.getAppConfirmed()), "confirmed"));
        cards.add(new MetricCardDTO("Available", currentStats.getAppAvailable(), calculatePercentage.apply(currentStats.getAppAvailable(), previousStats.getAppAvailable()), "available"));
        cards.add(new MetricCardDTO("Issued", currentStats.getAppIssued(), calculatePercentage.apply(currentStats.getAppIssued(), previousStats.getAppIssued()), "issued"));
        cards.add(new MetricCardDTO("Damaged", currentStats.getAppDamaged(), calculatePercentage.apply(currentStats.getAppDamaged(), previousStats.getAppDamaged()), "damaged"));
//        cards.add(new MetricCardDTO("With Pro", currentStats.getWithPro(), calculatePercentage.apply(currentStats.getWithPro(), previousStats.getWithPro()), "with_pro"));
        cards.add(new MetricCardDTO("Unavailable", currentStats.getAppUnavailable(), calculatePercentage.apply(currentStats.getAppUnavailable(), previousStats.getAppUnavailable()), "unavailable"));
 
        return cards;
    }
}