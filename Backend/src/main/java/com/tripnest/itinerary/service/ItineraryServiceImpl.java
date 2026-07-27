package com.tripnest.itinerary.service;

import com.tripnest.itinerary.dto.ItineraryDayDTO;
import com.tripnest.itinerary.dto.ActivityDTO;
import com.tripnest.itinerary.entity.ItineraryDay;
import com.tripnest.itinerary.repository.ItineraryDayRepository;
import com.tripnest.trip.entity.Trip;
import com.tripnest.trip.repository.TripRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItineraryServiceImpl implements ItineraryService {

    private final ItineraryDayRepository itineraryDayRepository;
    private final TripRepository tripRepository;
    private final ActivityService activityService;

    public ItineraryServiceImpl(ItineraryDayRepository itineraryDayRepository, TripRepository tripRepository, ActivityService activityService) {
        this.itineraryDayRepository = itineraryDayRepository;
        this.tripRepository = tripRepository;
        this.activityService = activityService;
    }

    @Override
    public ItineraryDayDTO createItineraryDay(ItineraryDayDTO itineraryDayDTO) {
        Trip trip = tripRepository.findById(itineraryDayDTO.getTripId())
                .orElseThrow(() -> new RuntimeException("Trip not found"));
        
        ItineraryDay itineraryDay = mapToEntity(itineraryDayDTO, trip);
        ItineraryDay savedItineraryDay = itineraryDayRepository.save(itineraryDay);
        return mapToDTO(savedItineraryDay);
    }

    @Override
    public ItineraryDayDTO updateItineraryDay(Long id, ItineraryDayDTO itineraryDayDTO) {
        ItineraryDay itineraryDay = itineraryDayRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Itinerary Day not found"));
        
        itineraryDay.setDate(itineraryDayDTO.getDate());
        itineraryDay.setDayNumber(itineraryDayDTO.getDayNumber());
        itineraryDay.setNotes(itineraryDayDTO.getNotes());
        
        ItineraryDay updatedItineraryDay = itineraryDayRepository.save(itineraryDay);
        return mapToDTO(updatedItineraryDay);
    }

    @Override
    public void deleteItineraryDay(Long id) {
        itineraryDayRepository.deleteById(id);
    }

    @Override
    public ItineraryDayDTO getItineraryDayById(Long id) {
        ItineraryDay itineraryDay = itineraryDayRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Itinerary Day not found"));
        return mapToDTO(itineraryDay);
    }

    @Override
    public List<ItineraryDayDTO> getItineraryByTrip(Long tripId) {
        return itineraryDayRepository.findByTripIdOrderByDateAsc(tripId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ItineraryDayDTO getItineraryByTripAndDate(Long tripId, LocalDate date) {
        ItineraryDay itineraryDay = itineraryDayRepository.findByTripIdAndDate(tripId, date)
                .orElseThrow(() -> new RuntimeException("Itinerary Day not found"));
        return mapToDTO(itineraryDay);
    }

    private ItineraryDayDTO mapToDTO(ItineraryDay itineraryDay) {
        List<ActivityDTO> activities = itineraryDay.getActivities().stream()
                .map(activity -> ActivityDTO.builder()
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
                        .build())
                .collect(Collectors.toList());
        
        return ItineraryDayDTO.builder()
                .id(itineraryDay.getId())
                .tripId(itineraryDay.getTrip().getId())
                .date(itineraryDay.getDate())
                .dayNumber(itineraryDay.getDayNumber())
                .notes(itineraryDay.getNotes())
                .activities(activities)
                .build();
    }

    private ItineraryDay mapToEntity(ItineraryDayDTO dto, Trip trip) {
        return ItineraryDay.builder()
                .trip(trip)
                .date(dto.getDate())
                .dayNumber(dto.getDayNumber())
                .notes(dto.getNotes())
                .build();
    }
}
