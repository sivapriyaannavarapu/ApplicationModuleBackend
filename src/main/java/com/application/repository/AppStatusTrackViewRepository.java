package com.application.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.entity.AppStatusTrackView;

@Repository
public interface AppStatusTrackViewRepository extends JpaRepository<AppStatusTrackView, Integer>{
	
	Optional<AppStatusTrackView> findByNum(int num);
}
