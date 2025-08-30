package com.application.dto;
 
import lombok.AllArgsConstructor;
import lombok.Data;
 
@Data
@AllArgsConstructor
public class AppNumberRangeDTO {
    private int id; // This will be the balance_track_id
    private int appFrom;
    private int appTo;
}
 