package com.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DgmRepository extends JpaRepository<DgmRepository, Integer>{

}
