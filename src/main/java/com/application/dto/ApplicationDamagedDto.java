package com.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDamagedDto {
	
    private int appNo;
    private String reason;
    private int createdBy;
    private int proId;
    private int zoneEmpId;
    private int dgmEmpId;
    private int statusId;
    private int campusId;
}
