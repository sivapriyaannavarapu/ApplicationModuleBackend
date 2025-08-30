package com.application.controller;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
 
import com.application.dto.ApplicationStatusViewDTO;

import com.application.service.ApplicationStatusViewService;
 
@RestController
@RequestMapping("/api/application-status")
public class ApplicationStatusViewController {
 
    @Autowired

    private ApplicationStatusViewService applicationStatusViewService;
 
    @GetMapping("/view")

    public ResponseEntity<List<ApplicationStatusViewDTO>> getApplicationStatusView() {

        List<ApplicationStatusViewDTO> data = applicationStatusViewService.getApplicationStatusViewData();

        return ResponseEntity.ok(data);

    }

}

 