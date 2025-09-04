// StudentDetailsDTO.java
package com.application.dto;

import java.util.List;
import lombok.Data;

@Data
public class StudentDetailsDTO {
    private String studentName;
    private String surname;
    private String parentName;
    private String gender;
    private float applicationFee;
    private float confirmationAmount; // Assuming this is `paid_amount` from PaymentDetails
    private List<Float> concessionAmounts;
}