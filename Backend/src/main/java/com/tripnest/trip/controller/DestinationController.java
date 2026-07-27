package com.tripnest.trip.controller;

import com.tripnest.trip.dto.DestinationDTO;
import com.tripnest.trip.service.DestinationService;
import com.tripnest.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/destinations")
@CrossOrigin(origins = "*")
public class DestinationController {

    private final DestinationService destinationService;

    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DestinationDTO>>> getAllDestinations() {
        List<DestinationDTO> destinations = destinationService.getAllDestinations();
        return ResponseEntity.ok(ApiResponse.success(destinations, "Destinations retrieved successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DestinationDTO>> getDestinationById(@PathVariable Long id) {
        DestinationDTO destination = destinationService.getDestinationById(id);
        return ResponseEntity.ok(ApiResponse.success(destination, "Destination retrieved successfully"));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<DestinationDTO>>> searchDestinations(@RequestParam String query) {
        List<DestinationDTO> destinations = destinationService.searchDestinations(query);
        return ResponseEntity.ok(ApiResponse.success(destinations, "Search results retrieved successfully"));
    }

    @GetMapping("/country/{country}")
    public ResponseEntity<ApiResponse<List<DestinationDTO>>> getDestinationsByCountry(@PathVariable String country) {
        List<DestinationDTO> destinations = destinationService.getDestinationsByCountry(country);
        return ResponseEntity.ok(ApiResponse.success(destinations, "Destinations retrieved successfully"));
    }

    @GetMapping("/top")
    public ResponseEntity<ApiResponse<List<DestinationDTO>>> getTopDestinations() {
        List<DestinationDTO> destinations = destinationService.getTopDestinations();
        return ResponseEntity.ok(ApiResponse.success(destinations, "Top destinations retrieved successfully"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DestinationDTO>> createDestination(@RequestBody DestinationDTO destinationDTO) {
        DestinationDTO created = destinationService.createDestination(destinationDTO);
        return ResponseEntity.ok(ApiResponse.success(created, "Destination created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DestinationDTO>> updateDestination(@PathVariable Long id, @RequestBody DestinationDTO destinationDTO) {
        DestinationDTO updated = destinationService.updateDestination(id, destinationDTO);
        return ResponseEntity.ok(ApiResponse.success(updated, "Destination updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteDestination(@PathVariable Long id) {
        destinationService.deleteDestination(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Destination deleted successfully"));
    }
}
