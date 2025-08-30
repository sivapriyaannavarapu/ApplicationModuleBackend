package com.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.entity.Dgm;

@Repository
public interface DgmRepository extends JpaRepository<Dgm, Integer> {
    // Your custom query methods here
}