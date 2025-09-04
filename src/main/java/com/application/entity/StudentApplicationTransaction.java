package com.application.entity;

import java.util.Date;

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
@Table(name = "sce_app_transactions" , schema = "sce_student")
public class StudentApplicationTransaction {
	
	@Id
	private int app_transaction_id;
	private int org_id;
	private String cheque_no;
	private String dd_no;
	private String city_name;
	private String bank_name;
	private String branch_name;
	private String ifsc_code;
	private Date date;
	private Date application_fee_pay_date;
}
