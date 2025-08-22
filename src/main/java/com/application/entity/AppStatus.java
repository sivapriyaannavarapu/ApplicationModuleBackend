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
@Table(name = "sce_app_status" , schema="sce_application")
public class AppStatus {

	@Id
	private int app_status_id;
	private int app_no;
	private String reason;
	private int is_active;
	private int created_by;

	@ManyToOne
	@JoinColumn(name = "app_issued_type_id", referencedColumnName = "app_issued_id")
	private AppIssuedType appIssuedType;

	@ManyToOne
	@JoinColumn(name = "emp_id")
	private Employee employee;

	@ManyToOne
	@JoinColumn(name = "status_id")
	private Status status;
}
