package com.application.dto;
 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppStatusTrackDTO {
 
    private int totalApplications;
    private int appSold;
    private int appConfirmed;
    private int appAvailable;
    private int appIssued;
    private int appDamaged;
    private int appUnavailable;
    private int withPro;
 
}