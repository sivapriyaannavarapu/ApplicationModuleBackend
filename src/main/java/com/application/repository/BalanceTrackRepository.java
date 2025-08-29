package com.application.repository;

import com.application.entity.BalanceTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // Import @Query
import org.springframework.data.repository.query.Param; // Import @Param
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BalanceTrackRepository extends JpaRepository<BalanceTrack, Integer> {
    
    // Replace the previous method with this @Query
    @Query("SELECT b FROM BalanceTrack b WHERE b.academicYear.acdcYearId = :academicYearId AND b.employee.emp_id = :employeeId")
    Optional<BalanceTrack> findBalanceTrack(
        @Param("academicYearId") int academicYearId, 
        @Param("employeeId") int employeeId
    );
}