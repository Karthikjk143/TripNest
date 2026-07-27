package com.tripnest.itinerary.repository;

import com.tripnest.itinerary.entity.Activity;
import com.tripnest.itinerary.entity.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByItineraryDayIdOrderByStartTimeAsc(Long itineraryDayId);
    List<Activity> findByItineraryDayIdAndType(Long itineraryDayId, ActivityType type);
    
    @Query("SELECT a FROM Activity a WHERE a.itineraryDay.trip.id = :tripId ORDER BY a.itineraryDay.date, a.startTime")
    List<Activity> findAllActivitiesByTripId(@Param("tripId") Long tripId);
    
    @Query("SELECT a FROM Activity a WHERE a.itineraryDay.date = CURRENT_DATE AND a.remindMinutesBefore IS NOT NULL")
    List<Activity> findActivitiesRequiringReminder();
}
