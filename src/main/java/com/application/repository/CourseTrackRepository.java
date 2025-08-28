package com.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.entity.CourseTrack;

@Repository
public interface CourseTrackRepository extends JpaRepository<CourseTrack, Integer>{

}
