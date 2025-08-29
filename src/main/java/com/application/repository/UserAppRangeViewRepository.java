package com.application.repository;

import com.application.entity.UserAppRangeView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserAppRangeViewRepository extends JpaRepository<UserAppRangeView, Integer> {
    
    /**
     * Finds the available application range for a specific employee and academic year.
     * We use a @Query annotation here to explicitly define the query,
     * which avoids any issues with Spring Data JPA parsing the method name.
     * * @param empId The employee's ID.
     * @param acdcYearId The academic year's ID.
     * @return An Optional containing the UserAppRangeView if found.
     */
    @Query("SELECT v FROM UserAppRangeView v WHERE v.emp_id = :empId AND v.acdc_year_id = :acdcYearId")
    Optional<UserAppRangeView> findByEmployeeAndYear(
        @Param("empId") int empId, 
        @Param("acdcYearId") int acdcYearId
    );
}