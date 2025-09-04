package com.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.entity.OrganizationBankDetails;

@Repository
public interface OrganizationBankDetailsRepository extends JpaRepository<OrganizationBankDetails, Integer>{

}
