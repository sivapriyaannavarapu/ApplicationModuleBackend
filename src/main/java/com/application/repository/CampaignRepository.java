package com.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.entity.Campaign;

@Repository
public interface CampaignRepository  extends JpaRepository<Campaign, Integer>{

}
