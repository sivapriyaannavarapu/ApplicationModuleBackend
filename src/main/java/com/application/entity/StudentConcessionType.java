package com.application.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="sce_stud_conc" , schema = "sce_student")
public class StudentConcessionType {

	@Id
	private int stud_conc_type_id;
	private int acad_id;
	private int stud_adms_id;
	private float conc_amount;
	private int conc_issued_by;
	private int conc_authorised_by;

	@ManyToOne
	@JoinColumn(name = "conc_type_id")
	private ConcessionType concessionType;

	@ManyToOne
	@JoinColumn(name = "reason_conc_id", referencedColumnName = "conc_reason_id")
	private ConcessionReason concessionReason;
}
