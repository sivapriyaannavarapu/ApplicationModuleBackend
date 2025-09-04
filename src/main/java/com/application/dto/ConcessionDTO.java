package com.application.dto;

import lombok.Data;

@Data
public class ConcessionDTO {
    private Integer concessionTypeId;   // 1st/2nd/3rd year
    private Double concessionAmount;
    private Integer reasonId;           // dropdown reason
}
