package com.tripnest.itinerary.service;

import com.tripnest.itinerary.dto.ActivityDTO;
import com.tripnest.itinerary.entity.Activity;
import com.tripnest.itinerary.entity.ItineraryDay;
import com.tripnest.itinerary.repository.ActivityRepository;
import com.tripnest.itinerary.repository.ItineraryDayRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final ItineraryDayRepository itineraryDayRepository;

    public ActivityServiceImpl(ActivityRepository activityRepository, ItineraryDayRepository itineraryDayRepository) {
        this.activityRepository = activityRepository;
        this.itineraryDayRepository = itineraryDayRepository;
    }

    @Override
    public ActivityDTO createActivity(ActivityDTO activityDTO) {
        ItineraryDay itineraryDay = itineraryDayRepository.findById(activityDTO.getItineraryDayId())
                .orElseThrow(() -> new RuntimeException("Itinerary Day not found"));
        
        Activity activity = mapToEntity(activityDTO, itineraryDay);
        Activity savedActivity = activityRepository.save(activity);
        return mapToDTO(savedActivity);
    }

    @Override
    public ActivityDTO updateActivity(Long id, ActivityDTO activityDTO) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found"));
        
        activity.setTitle(activityDTO.getTitle());
        activity.setDescription(activityDTO.getDescription());
        activity.setType(activityDTO.getType());
        activity.setStartTime(activityDTO.getStartTime());
        activity.setEndTime(activityDTO.getEndTime());
        activity.setLocation(activityDTO.getLocation());
        activity.setLatitude(activityDTO.getLatitude());
        activity.setLongitude(activityDTO.getLongitude());
        activity.setImageUrl(activityDTO.getImageUrl());
        activity.setEstimatedCost(activityDTO.getEstimatedCost());
        activity.setRemindMinutesBefore(activityDTO.getRemindMinutesBefore());
        
        Activity updatedActivity = activityRepository.save(activity);
        return mapToDTO(updatedActivity);
    }

    @Override
    public void deleteActivity(Long id) {
        activityRepository.deleteById(id);
    }

    @Override
    public ActivityDTO getActivityById(Long id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found"));
        return mapToDTO(activity);
    }

    @Override
    public List<ActivityDTO> getActivitiesByItineraryDay(Long itineraryDayId) {
        return activityRepository.findByItineraryDayIdOrderByStartTimeAsc(itineraryDayId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ActivityDTO> getActivitiesByTrip(Long tripId) {
        return activityRepository.findAllActivitiesByTripId(tripId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ActivityDTO> getActivitiesRequiringReminder() {
        return activityRepository.findActivitiesRequiringReminder().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ActivityDTO mapToDTO(Activity activity) {
        return ActivityDTO.builder()
                .id(activity.getId())
                .itineraryDayId(activity.getItineraryDay().getId())
                .title(activity.getTitle())
                .description(activity.getDescription())
                .type(activity.getType())
                .startTime(activity.getStartTime())
                .endTime(activity.getEndTime())
                .location(activity.getLocation())
                .latitude(activity.getLatitude())
                .longitude(activity.getLongitude())
                .imageUrl(activity.getImageUrl())
                .estimatedCost(activity.getEstimatedCost())
                .remindMinutesBefore(activity.getRemindMinutesBefore())
                .build();
    }

    private Activity mapToEntity(ActivityDTO dto, ItineraryDay itineraryDay) {
        return Activity.builder()
                .itineraryDay(itineraryDay)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .type(dto.getType())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .location(dto.getLocation())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .imageUrl(dto.getImageUrl())
                .estimatedCost(dto.getEstimatedCost())
                .remindMinutesBefore(dto.getRemindMinutesBefore())
                .build();
    }
}
