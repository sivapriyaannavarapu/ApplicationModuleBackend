package com.application.dto;
 
import lombok.Data;
 
@Data
public class FormSubmissionDTO {
    // ID of the logged-in user (the Zonal Officer)
    private int userId;
    
    private int academicYearId;
    private int cityId;
    private int zoneId;
    private int campusId;
    private int issuedToId; // The ID for "DGM" from the issued_to dropdown
    private int dgmEmployeeId; // The ID of the specific DGM employee selected
    
    // The BalanceTrack ID of the range the Zonal Officer is distributing from
    private int selectedBalanceTrackId;
    
    private String applicationNoFrom;
    private String applicationNoTo;
    private int range;
}