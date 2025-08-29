package com.application.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class DistributionRequestDTO {
    private int academicYearId;
    private int stateId;
    private int cityId;
    private int zoneId;
    private int issuedToEmpId;
    
    private int appStartNo;
    private int appEndNo;
    private int range; // This is the totalAppCount
    
    private LocalDate issueDate;
    private int createdBy; // The ID of the user submitting the form
}