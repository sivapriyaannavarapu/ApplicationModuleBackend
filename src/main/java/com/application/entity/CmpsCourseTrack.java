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
@Table(name="sce_cmps_course_track" , schema = "sce_course")
public class CmpsCourseTrack {
	
	
	@Id
	private int cmps_course_track_id;
	private int cmps_id;
	private int acdc_year_id;
	private float course_fee;
	
	@ManyToOne
	@JoinColumn(name = "course_track_id")
	private CourseTrack courseTrack;
	
	@ManyToOne
	@JoinColumn(name = "course_batch_id")
	private CourseBatch courseBatch;
	
	@ManyToOne
	@JoinColumn(name = "fee_set_id")
	private FeeSets feeSets;
}
