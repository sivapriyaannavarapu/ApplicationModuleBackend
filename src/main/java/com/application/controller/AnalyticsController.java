package com.application.controller;

import com.application.entity.AppStatusTrack;
import com.application.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/status-track")
    public ResponseEntity<AppStatusTrack> getStatusTrack() {
        AppStatusTrack statusTrack = analyticsService.getStatusTrack();
        return ResponseEntity.ok(statusTrack);
    }
}
