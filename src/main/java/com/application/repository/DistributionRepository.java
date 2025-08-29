
package com.application.repository;

import com.application.entity.Distribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributionRepository extends JpaRepository<Distribution, Integer> {

    @Query(value = "SELECT MAX(CAST(d.app_end_no AS bigint)) FROM sce_application.sce_app_distrubution d WHERE d.state_id = :stateId AND d.created_by = :userId AND d.acdc_year_id = :academicYearId", nativeQuery = true)
    Long findMaxAppEndNoAsLong(@Param("stateId") int stateId, @Param("userId") int userId, @Param("academicYearId") int academicYearId);
}
