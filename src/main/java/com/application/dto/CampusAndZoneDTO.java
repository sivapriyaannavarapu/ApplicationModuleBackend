package com.application.dto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampusAndZoneDTO {
    private Integer campusId;
    private String campusName;
    private Integer zoneId;
    private String zoneName;
}