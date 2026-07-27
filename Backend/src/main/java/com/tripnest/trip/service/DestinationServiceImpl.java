package com.tripnest.trip.service;

import com.tripnest.trip.dto.DestinationDTO;
import com.tripnest.trip.entity.Destination;
import com.tripnest.trip.repository.DestinationRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DestinationServiceImpl implements DestinationService {

    private final DestinationRepository destinationRepository;

    public DestinationServiceImpl(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    @Override
    public DestinationDTO createDestination(DestinationDTO destinationDTO) {
        Destination destination = mapToEntity(destinationDTO);
        Destination savedDestination = destinationRepository.save(destination);
        return mapToDTO(savedDestination);
    }

    @Override
    public DestinationDTO updateDestination(Long id, DestinationDTO destinationDTO) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found"));
        
        destination.setName(destinationDTO.getName());
        destination.setCountry(destinationDTO.getCountry());
        destination.setRegion(destinationDTO.getRegion());
        destination.setDescription(destinationDTO.getDescription());
        destination.setImageUrl(destinationDTO.getImageUrl());
        destination.setLatitude(destinationDTO.getLatitude());
        destination.setLongitude(destinationDTO.getLongitude());
        destination.setAttractions(destinationDTO.getAttractions());
        destination.setBestTimeToVisit(destinationDTO.getBestTimeToVisit());
        
        Destination updatedDestination = destinationRepository.save(destination);
        return mapToDTO(updatedDestination);
    }

    @Override
    public void deleteDestination(Long id) {
        destinationRepository.deleteById(id);
    }

    @Override
    public DestinationDTO getDestinationById(Long id) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found"));
        return mapToDTO(destination);
    }

    @Override
    public List<DestinationDTO> getAllDestinations() {
        return destinationRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DestinationDTO> searchDestinations(String query) {
        return destinationRepository.searchDestinations(query).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DestinationDTO> getDestinationsByCountry(String country) {
        return destinationRepository.findByCountry(country).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DestinationDTO> getTopDestinations() {
        return destinationRepository.getTopDestinations().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private DestinationDTO mapToDTO(Destination destination) {
        return DestinationDTO.builder()
                .id(destination.getId())
                .name(destination.getName())
                .country(destination.getCountry())
                .region(destination.getRegion())
                .description(destination.getDescription())
                .imageUrl(destination.getImageUrl())
                .latitude(destination.getLatitude())
                .longitude(destination.getLongitude())
                .attractions(destination.getAttractions())
                .bestTimeToVisit(destination.getBestTimeToVisit())
                .popularityScore(destination.getPopularityScore())
                .build();
    }

    private Destination mapToEntity(DestinationDTO dto) {
        return Destination.builder()
                .name(dto.getName())
                .country(dto.getCountry())
                .region(dto.getRegion())
                .description(dto.getDescription())
                .imageUrl(dto.getImageUrl())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .attractions(dto.getAttractions())
                .bestTimeToVisit(dto.getBestTimeToVisit())
                .popularityScore(0)
                .build();
    }
}
