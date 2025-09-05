package com.application.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class StudentAdmissionDTO {
    
    // --- General Information ---
    private String studAdmsNo;
    private String htNo;
    private Long aadharCardNo;
    private Integer appTypeId;
    private String surname;
    private String studentName;
    private int admsTypeId;
    private String fatherName;
    private String occupation;
    private Long phoneNumber;
    private Integer studentTypeId;
    private Date dob;
    private Integer genderId;
    private Integer campusId;
    private Integer joinIntoId;
    private Integer classId;
    private Integer sectionId;
    private Integer courseId;
    private Integer courseBatchId;
    private Integer preSchoolStateId;
    private Integer preSchoolDistrictId;
    private Integer schoolTypeId;
    private String schoolName;
    private Integer quotaId;
    private String scoreAppNo;
    private Integer marks;
    private String admissionReferredBy;
    private Integer additionalCourseFee;
    private Integer statusId;
    private LocalDate DateOfJoin;
    private Integer proId;
    private Date appSaleDate;
    private Integer CreatedBY;
  
    
    // --- Sibling Information ---
    private List<SiblingDTO> siblings;

    // --- Class Information ---
   

    // --- NESTED DTOs FOR OTHER SECTIONS ---
    private AddressDetailsDTO addressDetails;
    private PaymentDetailstDTO paymentDetails;
    private StudentConcessionDetailsDTO concessionDetails;
    

    @Data
    public static class SiblingDTO {
        private String fullName;
        private Integer relationTypeId;
        private Integer classId;
        private String schoolName;
        private Integer genderId;
        private Integer createdBy;
    }
}