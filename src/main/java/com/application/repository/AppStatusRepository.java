package com.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.entity.AppStatus;

@Repository
public interface AppStatusRepository extends JpaRepository<AppStatus, Integer>{

}
