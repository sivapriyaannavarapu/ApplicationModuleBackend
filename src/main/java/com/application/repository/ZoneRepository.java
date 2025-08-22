package com.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.entity.Zone;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Integer>{

}
