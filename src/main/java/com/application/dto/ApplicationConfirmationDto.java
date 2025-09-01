package com.application.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ApplicationConfirmationDto {

    // Admission details
    private String admissionNumber;
    private String firstName;
    private String lastName;
    private String parentName;
    private String gender;

    // Payments
    private float appFee;         // matches app_fee in entity
    private float concAmount;     // matches conc_amount
    

    // Concessions
//    private float firstYearConcession;
//    private float secondYearConcession;
//    private float thirdYearConcession;
    private String concessionReason;
    private String concessionType;

    // Course/Batch details
    private Integer courseTrackId;
    private Integer programId;
    private Integer examProgramId;
    private Integer courseBatchId;
    private Integer sectionId;

    // Auto-populated from DB
    private LocalDate courseStartDate;
    private LocalDate courseEndDate;
    private float courseFee;
}
