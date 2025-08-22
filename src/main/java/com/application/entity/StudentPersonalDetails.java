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
@Table(name="sce_stud_personal_detls" , schema = "sce_student")
public class StudentPersonalDetails {
	
	@Id
	private int stud_personal_id;
	private String father_name;
	private String mother_name;
	private int parent_mobile_no;
	private String parent_mail;
	private int stud_aadhaar_no;
	private String father_occupation;
	private int is_active;
	private int created_by;
	
	@ManyToOne
	@JoinColumn(name = "stud_adms_id")
	private StudentAcademicDetails studentAcademicDetails;

}
