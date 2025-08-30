package com.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.entity.ZonalAccountant;

@Repository
public interface ZonalAccountantRepository extends JpaRepository<ZonalAccountant, Integer>{
	
	 List<ZonalAccountant> findByZoneZoneId(int zoneId);
}
