package com.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.entity.CourseOrientation;

@Repository
public interface CourseOrientationRepository extends JpaRepository<CourseOrientation, Integer>{

}
