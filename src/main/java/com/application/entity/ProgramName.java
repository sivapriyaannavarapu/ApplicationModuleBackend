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
@Table(name = "sce_program_name", schema = "sce_course")
public class ProgramName {

	@Id
	private int program_id;
	@Column(name = "program_name")
	private String programName;
	private int stream_id;
	private int class_id;
	private int promoting_program_id;
	private int no_hours_day;
	private int no_days_week;
	private int no_of_holidays;
	private int status;

	@ManyToOne
	@JoinColumn(name = "course_track_id")
	private CourseTrack courseTrack;
}
