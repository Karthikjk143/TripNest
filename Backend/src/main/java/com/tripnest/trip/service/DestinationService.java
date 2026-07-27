package com.tripnest.trip.service;

import com.tripnest.trip.dto.DestinationDTO;
import java.util.List;
import java.util.Optional;

public interface DestinationService {
    DestinationDTO createDestination(DestinationDTO destinationDTO);
    DestinationDTO updateDestination(Long id, DestinationDTO destinationDTO);
    void deleteDestination(Long id);
    DestinationDTO getDestinationById(Long id);
    List<DestinationDTO> getAllDestinations();
    List<DestinationDTO> searchDestinations(String query);
    List<DestinationDTO> getDestinationsByCountry(String country);
    List<DestinationDTO> getTopDestinations();
}
