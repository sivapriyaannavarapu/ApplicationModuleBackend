package com.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.entity.CampusProView;

@Repository
public interface CampusProViewRepository extends JpaRepository<CampusProView, Integer>{

}
