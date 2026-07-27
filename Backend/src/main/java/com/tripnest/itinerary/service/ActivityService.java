package com.tripnest.itinerary.service;

import com.tripnest.itinerary.dto.ActivityDTO;
import java.util.List;

public interface ActivityService {
    ActivityDTO createActivity(ActivityDTO activityDTO);
    ActivityDTO updateActivity(Long id, ActivityDTO activityDTO);
    void deleteActivity(Long id);
    ActivityDTO getActivityById(Long id);
    List<ActivityDTO> getActivitiesByItineraryDay(Long itineraryDayId);
    List<ActivityDTO> getActivitiesByTrip(Long tripId);
    List<ActivityDTO> getActivitiesRequiringReminder();
}
