package com.application.dto;

import java.util.Date;

import com.application.entity.City;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetailstDTO {

    // Common fields for all payment modes
    private Float applicationFeeAmount;
    private String prePrintedReceiptNo;
    private Integer paymentModeId;
    private Date applicationFeeDate;
    private Integer createdBy;
    private float concessionAmount;

    // DD and Cheque specific fields
    private String chequeDdNo;
    private Integer orgBankId;
    private Integer orgBankBranchId;
    private Integer cityId;
    private String ifscCode;
    private Integer organizationId;
      
    // Links to other entities (by ID)
    private Integer studentAcademicDetailsId;
    private Integer studentConcessionTypeId;
    private Integer academicYearId;
    private Integer studentClassId;
    private Integer statusId;
}