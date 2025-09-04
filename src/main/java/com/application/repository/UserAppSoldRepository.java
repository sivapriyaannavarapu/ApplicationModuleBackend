package com.application.repository;
import java.util.List;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
 
import com.application.entity.UserAppSold;
 
@Repository
public interface UserAppSoldRepository extends JpaRepository<UserAppSold, Long> {
    
    List<UserAppSold> findByEntityId(Integer entityId);
 
    @Query(value = "SELECT entity_id, (CAST(SUM(sold) AS DECIMAL) / SUM(total_app_count)) * 100 AS performance_percentage FROM sce_application.sce_user_app_sold WHERE entity_id = 2 GROUP BY entity_id ORDER BY performance_percentage DESC", nativeQuery = true)
    List<Object[]> findTopRatedZones();
 
    @Query(value = "SELECT entity_id, (CAST((SUM(total_app_count) - SUM(sold)) AS DECIMAL) / SUM(total_app_count)) * 100 AS performance_percentage FROM sce_application.sce_user_app_sold WHERE entity_id = 2 GROUP BY entity_id ORDER BY performance_percentage DESC", nativeQuery = true)
    List<Object[]> findDropRatedZones();
    
    
    @Query(value = "SELECT entity_id, (CAST(SUM(sold) AS DECIMAL) / SUM(total_app_count)) * 100 AS performance_percentage FROM sce_application.sce_user_app_sold WHERE entity_id = 3 GROUP BY entity_id ORDER BY performance_percentage DESC", nativeQuery = true)
    List<Object[]> findTopRatedDgms();
 
    @Query(value = "SELECT entity_id, (CAST((SUM(total_app_count) - SUM(sold)) AS DECIMAL) / SUM(total_app_count)) * 100 AS performance_percentage FROM sce_application.sce_user_app_sold WHERE entity_id = 3 GROUP BY entity_id ORDER BY performance_percentage DESC", nativeQuery = true)
    List<Object[]> findDropRatedDgms();
    
    @Query(value = "SELECT entity_id, (CAST(SUM(sold) AS DECIMAL) / SUM(total_app_count)) * 100 AS performance_percentage FROM sce_application.sce_user_app_sold WHERE entity_id = 4 GROUP BY entity_id ORDER BY performance_percentage DESC", nativeQuery = true)
    List<Object[]> findTopRatedCampus();
 
    @Query(value = "SELECT entity_id, (CAST((SUM(total_app_count) - SUM(sold)) AS DECIMAL) / SUM(total_app_count)) * 100 AS performance_percentage FROM sce_application.sce_user_app_sold WHERE entity_id = 4 GROUP BY entity_id ORDER BY performance_percentage DESC", nativeQuery = true)
    List<Object[]> findDropRatedCampus();
    
}
 