package com.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.entity.AppStatusTrack;
import com.application.repository.AppStatusTrackRepository;
import com.application.exception.ResourceNotFoundException;

@Service
public class AnalyticsService {
	
	
	@Autowired
	AppStatusTrackRepository appStatusTrackRepository;
	
	public AppStatusTrack getStatusTrack() {
        return appStatusTrackRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No status track record found in database"));
    }	
}
