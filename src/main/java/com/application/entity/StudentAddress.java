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
@Table(name="sce_stud_addrs" , schema = "sce_student")
public class StudentAddress {

	@Id
	private int stud_addrs_id;
	private String house_no;
	private String street;
	private String landmark;
	private int postal_code;
	private String city;
	private String state;
	private String country;
	private String campus;
	private String area;
	private String district;

	@ManyToOne
	@JoinColumn(name = "stud_adms_id")
	private StudentAcademicDetails studentAcademicDetails;
}
