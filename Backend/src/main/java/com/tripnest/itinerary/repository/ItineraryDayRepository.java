package com.tripnest.itinerary.repository;

import com.tripnest.itinerary.entity.ItineraryDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ItineraryDayRepository extends JpaRepository<ItineraryDay, Long> {
    List<ItineraryDay> findByTripIdOrderByDateAsc(Long tripId);
    Optional<ItineraryDay> findByTripIdAndDate(Long tripId, LocalDate date);
    
    @Query("SELECT id.dayNumber FROM ItineraryDay id WHERE id.trip.id = :tripId ORDER BY id.dayNumber")
    List<Integer> findDayNumbersByTripId(@Param("tripId") Long tripId);
}
