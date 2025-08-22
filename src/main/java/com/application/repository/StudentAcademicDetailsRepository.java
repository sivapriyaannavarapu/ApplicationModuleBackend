package com.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.entity.StudentAcademicDetails;

@Repository
public interface StudentAcademicDetailsRepository  extends JpaRepository<StudentAcademicDetails, Integer>{

}
