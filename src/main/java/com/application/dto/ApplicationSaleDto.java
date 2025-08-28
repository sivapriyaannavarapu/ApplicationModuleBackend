package com.application.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationSaleDto {
    // StudentAcademicDetails fields
    private String htNo;
    private String firstName;
    private String lastName;
    private LocalDate admissionDate;
    private int createdBy;
    private int genderId;
    private int admissionTypeId;
    private int campusId;
    private int studentTypeId;
    private int classId;
    private int employeeId;

    // StudentPersonalDetails fields
    private String fatherName;
    private String motherName;
    private Long parentMobileNo;
    private String parentMail;
    private int studAadhaarNo;
    private String fatherOccupation;

    // StudentAddress fields
    private String houseNo;
    private String street;
    private String landmark;
    private int postalCode;
    private String city;
//    private String state;
//    private String country;
//    private String campus;
    private String area;
    private String mandal;
    private String district;
    
    
    //StudentPaymnet Details
    private int mainPayModeId; // e.g., Cash, DD
    private LocalDate paymentDate;
    private float amountPaid;
    private String prePrintedReceiptNo;
    private int appFeePayModeId;
    private LocalDate appFeePayDate;
    private Double appFeeAmount;
    private String appFeePrePrintedReceiptNo;
}