package com.application.repository;
 

import com.application.dto.ApplicationStartEndDto;
import com.application.entity.StateApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
 
@Repository
public interface StateAppRepository extends JpaRepository<StateApp, Integer> {
 
    // CORRECTED QUERY: Changed "s.createdBy" to "s.created_by"
    @Query("SELECT s FROM StateApp s WHERE s.state.stateId = :stateId AND s.created_by = :userId AND s.academicYear.acdcYearId = :academicYearId")
    Optional<StateApp> findStartNumber(@Param("stateId") int stateId, @Param("userId") int userId, @Param("academicYearId") int academicYearId);
// In StateAppRepository.java
    @Query("SELECT s.app_end_no FROM StateApp s WHERE s.state.stateId = :stateId AND s.created_by = :userId")
    Integer findAppEndNoByStateAndUser(@Param("stateId") int stateId, @Param("userId") int userId);
    
    @Query("SELECT new com.application.dto.ApplicationStartEndDto(s.app_start_no, s.app_end_no) " +
            "FROM StateApp s " +
            "WHERE s.academicYear.acdcYearId = :academicYearId " +
            "AND s.state.stateId = :stateId " +
            "AND s.created_by = :createdBy")
     Optional<ApplicationStartEndDto> findAppNumberRanges(
         @Param("academicYearId") int academicYearId,
         @Param("stateId") int stateId,
         @Param("createdBy") int createdBy);
}