package com.application.entity;

import jakarta.persistence.Column;
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
@Table(name="sce_stud_conc_detls" , schema = "sce_student")
public class StudentConcessionType {

	@Id
	private int stud_conc_detls_id;
	@Column(name = "acad_id")
	private int acadId;
	@Column(name = "stud_adms_id")
	private int studAdmsId;
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
