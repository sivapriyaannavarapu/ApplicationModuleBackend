package com.application.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.entity.CourseBatch;

@Repository
public interface CourseBatchRepository extends JpaRepository<CourseBatch, Integer>{
	
	 Optional<CourseBatch> findByCourseBatchName(String courseBatchName);
}
