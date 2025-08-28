package com.application.entity;

import java.time.LocalDate;
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
@Table(name="sce_stud_acdc_detls" , schema = "sce_student")
public class StudentAcademicDetails {

	@Id
	private int stud_adms_id;
	private String stud_adms_no;
	private String ht_no;
	private String first_name;
	private String last_name;
	private LocalDate adms_date;
	private int created_by;
	private LocalDate dob;
	private int course_track_id;
	private int course_batch;
	private String school_name;
	private String admission_referred_by;
	private String score_app_no;
	private int score_marks;
	private LocalDate course_date;
	

	@ManyToOne
	@JoinColumn(name = "acdc_year_id")
	private AcademicYear academicYear;

	@ManyToOne
	@JoinColumn(name = "acdc_lang_id")
	private AcademicLanguage academicLanguage;

	@ManyToOne
	@JoinColumn(name = "gender_id")
	private Gender gender;

	@ManyToOne
	@JoinColumn(name = "adms_type_id")
	private AdmissionType admissionType;

	@ManyToOne
	@JoinColumn(name = "cmps_id")
	private Campus campus;

	@ManyToOne
	@JoinColumn(name = "stud_type_id")
	private StudentType studentType;

	@ManyToOne
	@JoinColumn(name = "orientation_id")
	private Orientation orientation;

	@ManyToOne
	@JoinColumn(name = "section_id")
	private Section section;

	@ManyToOne
	@JoinColumn(name = "quota_id")
	private Quota quota;

	@ManyToOne
	@JoinColumn(name = "status_id")
	private Status status;
	
	@ManyToOne
	@JoinColumn(name = "class_id")
	private StudentClass studentClass;

	@ManyToOne
	@JoinColumn(name = "pro_id", referencedColumnName = "emp_id")
	private Employee employee;
	
	@ManyToOne
	@JoinColumn(name = "school_state_id", referencedColumnName = "state_id")
	private State state;
	
	@ManyToOne
	@JoinColumn(name = "school_district_id", referencedColumnName = "district_id")
	private District district;
	
	@ManyToOne
	@JoinColumn(name = "school_type_id")
	private SchoolType schoolType;
	
	@ManyToOne
	@JoinColumn(name = "stream_id")
	private Stream stream;
	
	@ManyToOne
	@JoinColumn(name = "program_id")
	private ProgramName programName;
	
	@ManyToOne
	@JoinColumn(name = "exam_program_id")
	private ExamProgram examProgram;
	
	@ManyToOne
	@JoinColumn(name = "cmps_course_track_id")
	private CmpsCourseTrack cmpsCourseTrack;

}
