package com.application.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
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
@Table(name = "sce_course_batch", schema = "sce_course")
public class CourseBatch {

	@Id
	@Column(name = "course_batch_id")
	private int courseBatchId;
	@Column(name = "course_batch_name")
	private String courseBatchName;
	private LocalDate start_date;
	private LocalDate end_date;
	private int acdc_id;
	private int status;
	private String display_name;
	private int course_duration;
}
