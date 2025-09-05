package com.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentConcessionDetailsDTO {

	private int studentConcessionTypeId;
	private int academicYearId;
	private float concessionAmount;
	private int concessionIssuedBy;
	private int concessionAuthorisedBy;
	private Integer concTypeId;
	private Integer concessionReasonId;
	private Integer CreatedBy;

}