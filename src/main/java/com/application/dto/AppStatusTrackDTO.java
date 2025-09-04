package com.application.dto;
 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppStatusTrackDTO {
 
    private Long totalApplications;
    private Long appSold;
    private Long appConfirmed;
    private Long appAvailable;
    private Long appIssued;
    private Long appDamaged;
    private Long appUnavailable;
 
}