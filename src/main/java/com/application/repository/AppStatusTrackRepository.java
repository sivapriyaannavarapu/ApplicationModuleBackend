package com.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.entity.AppStatusTrack;

@Repository
public interface AppStatusTrackRepository  extends JpaRepository<AppStatusTrack, Integer>{
	
	// This method fetches the top 2 records, ordered by ID in descending order
    List<AppStatusTrack> findTop2ByOrderByAppStatsTrkIdDesc();
}
