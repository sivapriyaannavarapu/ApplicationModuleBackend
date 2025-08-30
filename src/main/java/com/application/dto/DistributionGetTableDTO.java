package com.application.dto;

import lombok.AllArgsConstructor;

import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistributionGetTableDTO {

	private int appDistributionId;
	private int appStartNo;
	private int appEndNo;
	private int totalAppCount;
	private float amount;
	private int issued_by_type_id;
	private int issued_to_type_id;

}
