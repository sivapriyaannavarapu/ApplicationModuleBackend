package com.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.entity.StudentConcessionType;

@Repository
public interface StudentConcessionTypeRepository extends JpaRepository<StudentConcessionType, Integer>{

}
