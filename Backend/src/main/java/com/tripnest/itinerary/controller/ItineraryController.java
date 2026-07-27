package com.tripnest.itinerary.controller;

import com.tripnest.itinerary.dto.ItineraryDayDTO;
import com.tripnest.itinerary.service.ItineraryService;
import com.tripnest.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/itinerary")
@CrossOrigin(origins = "*")
public class ItineraryController {

    private final ItineraryService itineraryService;

    public ItineraryController(ItineraryService itineraryService) {
        this.itineraryService = itineraryService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ItineraryDayDTO>> createItineraryDay(@RequestBody ItineraryDayDTO itineraryDayDTO) {
        ItineraryDayDTO created = itineraryService.createItineraryDay(itineraryDayDTO);
        return ResponseEntity.ok(ApiResponse.success(created, "Itinerary day created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ItineraryDayDTO>> getItineraryDayById(@PathVariable Long id) {
        ItineraryDayDTO itineraryDay = itineraryService.getItineraryDayById(id);
        return ResponseEntity.ok(ApiResponse.success(itineraryDay, "Itinerary day retrieved successfully"));
    }

    @GetMapping("/trip/{tripId}")
    public ResponseEntity<ApiResponse<List<ItineraryDayDTO>>> getItineraryByTrip(@PathVariable Long tripId) {
        List<ItineraryDayDTO> itinerary = itineraryService.getItineraryByTrip(tripId);
        return ResponseEntity.ok(ApiResponse.success(itinerary, "Itinerary retrieved successfully"));
    }

    @GetMapping("/trip/{tripId}/date")
    public ResponseEntity<ApiResponse<ItineraryDayDTO>> getItineraryByTripAndDate(@PathVariable Long tripId, @RequestParam LocalDate date) {
        ItineraryDayDTO itineraryDay = itineraryService.getItineraryByTripAndDate(tripId, date);
        return ResponseEntity.ok(ApiResponse.success(itineraryDay, "Itinerary day retrieved successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ItineraryDayDTO>> updateItineraryDay(@PathVariable Long id, @RequestBody ItineraryDayDTO itineraryDayDTO) {
        ItineraryDayDTO updated = itineraryService.updateItineraryDay(id, itineraryDayDTO);
        return ResponseEntity.ok(ApiResponse.success(updated, "Itinerary day updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteItineraryDay(@PathVariable Long id) {
        itineraryService.deleteItineraryDay(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Itinerary day deleted successfully"));
    }
}
