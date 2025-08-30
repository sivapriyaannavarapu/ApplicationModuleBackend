package com.application.controller;
 
import com.application.dto.MetricCardDTO;

import com.application.service.AppStatusTrackService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
 
@RestController

@RequestMapping("/api/dashboard")

public class AppStatusTrackController {
 
    @Autowired

    private AppStatusTrackService appStatusTrackService;
 
    @GetMapping("/metrics")

    public ResponseEntity<List<MetricCardDTO>> getDashboardCards() {

        List<MetricCardDTO> cards = appStatusTrackService.getDashboardCards();

        return ResponseEntity.ok(cards);

    }

}

 