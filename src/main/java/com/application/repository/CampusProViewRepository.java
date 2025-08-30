package com.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.application.entity.CampusProView;

@Repository
public interface CampusProViewRepository extends JpaRepository<CampusProView, Integer>{
	
	@Query("SELECT v FROM CampusProView v WHERE v.cmps_id = :campusId")
    List<CampusProView> findByCampusId(@Param("campusId") int campusId);
}
