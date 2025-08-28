package com.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.entity.ExamProgram;

@Repository
public interface ExamProgramRepository extends JpaRepository<ExamProgram, Integer>{

}
