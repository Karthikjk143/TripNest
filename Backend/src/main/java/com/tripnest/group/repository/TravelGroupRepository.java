package com.tripnest.group.repository;

import com.tripnest.group.entity.TravelGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TravelGroupRepository extends JpaRepository<TravelGroup, Long> {
    List<TravelGroup> findByCreatedById(Long userId);
    
    @Query("SELECT g FROM TravelGroup g JOIN g.members m WHERE m.id = :userId")
    List<TravelGroup> findGroupsByMemberId(@Param("userId") Long userId);
    
    @Query("SELECT g FROM TravelGroup g WHERE LOWER(g.groupName) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<TravelGroup> searchGroups(@Param("query") String query);
}
