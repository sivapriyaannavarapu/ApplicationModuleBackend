package com.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.entity.SctCourseTrack;

@Repository
public interface SctCourseTrackRepository extends JpaRepository<SctCourseTrack, Integer>{

}
