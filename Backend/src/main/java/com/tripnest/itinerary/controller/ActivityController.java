package com.tripnest.itinerary.controller;

import com.tripnest.itinerary.dto.ActivityDTO;
import com.tripnest.itinerary.service.ActivityService;
import com.tripnest.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/activities")
@CrossOrigin(origins = "*")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ActivityDTO>> createActivity(@RequestBody ActivityDTO activityDTO) {
        ActivityDTO created = activityService.createActivity(activityDTO);
        return ResponseEntity.ok(ApiResponse.success(created, "Activity created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ActivityDTO>> getActivityById(@PathVariable Long id) {
        ActivityDTO activity = activityService.getActivityById(id);
        return ResponseEntity.ok(ApiResponse.success(activity, "Activity retrieved successfully"));
    }

    @GetMapping("/itinerary-day/{itineraryDayId}")
    public ResponseEntity<ApiResponse<List<ActivityDTO>>> getActivitiesByItineraryDay(@PathVariable Long itineraryDayId) {
        List<ActivityDTO> activities = activityService.getActivitiesByItineraryDay(itineraryDayId);
        return ResponseEntity.ok(ApiResponse.success(activities, "Activities retrieved successfully"));
    }

    @GetMapping("/trip/{tripId}")
    public ResponseEntity<ApiResponse<List<ActivityDTO>>> getActivitiesByTrip(@PathVariable Long tripId) {
        List<ActivityDTO> activities = activityService.getActivitiesByTrip(tripId);
        return ResponseEntity.ok(ApiResponse.success(activities, "Activities retrieved successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ActivityDTO>> updateActivity(@PathVariable Long id, @RequestBody ActivityDTO activityDTO) {
        ActivityDTO updated = activityService.updateActivity(id, activityDTO);
        return ResponseEntity.ok(ApiResponse.success(updated, "Activity updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Activity deleted successfully"));
    }
}
