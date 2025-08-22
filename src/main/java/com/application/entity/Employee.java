package com.application.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sce_emp", schema = "sce_employee")
public class Employee {

	@Id
	private int emp_id;
	private String first_name;
	private String last_name;
	private String primary_mobile_no;
}
