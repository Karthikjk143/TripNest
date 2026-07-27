package com.tripnest.trip.repository;

import com.tripnest.trip.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {
    Optional<Destination> findByName(String name);
    List<Destination> findByCountry(String country);
    List<Destination> findByRegion(String region);
    
    @Query("SELECT d FROM Destination d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(d.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Destination> searchDestinations(@Param("query") String query);
    
    @Query("SELECT d FROM Destination d ORDER BY d.popularityScore DESC LIMIT 10")
    List<Destination> getTopDestinations();
}
