package com.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.entity.BalanceTrack;

@Repository
public interface BalanceTrackRepository extends JpaRepository<BalanceTrack, Integer> {

}
