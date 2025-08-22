package com.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.entity.ConcessionType;

@Repository
public interface ConcessionTypeRepository extends JpaRepository<ConcessionType, Integer> {

}
