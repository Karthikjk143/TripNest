package com.tripnest.itinerary.service;

import com.tripnest.itinerary.dto.ItineraryDayDTO;
import java.time.LocalDate;
import java.util.List;

public interface ItineraryService {
    ItineraryDayDTO createItineraryDay(ItineraryDayDTO itineraryDayDTO);
    ItineraryDayDTO updateItineraryDay(Long id, ItineraryDayDTO itineraryDayDTO);
    void deleteItineraryDay(Long id);
    ItineraryDayDTO getItineraryDayById(Long id);
    List<ItineraryDayDTO> getItineraryByTrip(Long tripId);
    ItineraryDayDTO getItineraryByTripAndDate(Long tripId, LocalDate date);
}
