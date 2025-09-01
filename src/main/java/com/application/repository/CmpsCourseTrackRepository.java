package com.application.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.entity.CmpsCourseTrack;

@Repository
public interface CmpsCourseTrackRepository extends JpaRepository<CmpsCourseTrack, Integer>{
	
	 Optional<CmpsCourseTrack> findByCourseTrack_CourseTrackId(int courseTrackId);
}
