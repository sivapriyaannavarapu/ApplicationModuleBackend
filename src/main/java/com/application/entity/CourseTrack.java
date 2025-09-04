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
@Table(name="sce_course_track" , schema = "sce_course")
public class CourseTrack {
	
	@Id
	@Column(name = "course_track_id")
    private int courseTrackId;
	private String course_track_code;
	private String course_track_name;
	private Integer acdc_id;
	
	@ManyToOne
	@JoinColumn(name = "orientation_id")
	private Orientation orientation;
	
	@ManyToOne
	@JoinColumn(name = "track_type_id")
	private TrackType trackType;

	@ManyToOne
	@JoinColumn(name = "group_id")
	private CourseGroup courseGroup;
	
	@ManyToOne
	@JoinColumn(name = "class_id")
	private StudentClass studentClass;
	
}
