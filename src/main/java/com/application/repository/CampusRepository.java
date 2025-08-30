package com.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.entity.Campus;

@Repository
public interface CampusRepository extends JpaRepository<Campus, Integer>{
	
	List<Campus> findByZoneZoneId(int zoneId);
}
