package com.tripnest.group.service;

import com.tripnest.group.dto.TravelGroupDTO;
import com.tripnest.group.entity.TravelGroup;
import com.tripnest.group.repository.TravelGroupRepository;
import com.tripnest.trip.entity.Trip;
import com.tripnest.trip.repository.TripRepository;
import com.tripnest.user.entity.User;
import com.tripnest.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravelGroupServiceImpl implements TravelGroupService {

    private final TravelGroupRepository travelGroupRepository;
    private final UserRepository userRepository;
    private final TripRepository tripRepository;

    public TravelGroupServiceImpl(TravelGroupRepository travelGroupRepository, UserRepository userRepository, TripRepository tripRepository) {
        this.travelGroupRepository = travelGroupRepository;
        this.userRepository = userRepository;
        this.tripRepository = tripRepository;
    }

    @Override
    public TravelGroupDTO createGroup(TravelGroupDTO groupDTO) {
        User creator = userRepository.findById(groupDTO.getCreatedByUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        TravelGroup group = TravelGroup.builder()
                .groupName(groupDTO.getGroupName())
                .description(groupDTO.getDescription())
                .createdBy(creator)
                .build();
        
        group.getMembers().add(creator);
        TravelGroup savedGroup = travelGroupRepository.save(group);
        return mapToDTO(savedGroup);
    }

    @Override
    public TravelGroupDTO updateGroup(Long id, TravelGroupDTO groupDTO) {
        TravelGroup group = travelGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Travel Group not found"));
        
        group.setGroupName(groupDTO.getGroupName());
        group.setDescription(groupDTO.getDescription());
        
        TravelGroup updatedGroup = travelGroupRepository.save(group);
        return mapToDTO(updatedGroup);
    }

    @Override
    public void deleteGroup(Long id) {
        travelGroupRepository.deleteById(id);
    }

    @Override
    public TravelGroupDTO getGroupById(Long id) {
        TravelGroup group = travelGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Travel Group not found"));
        return mapToDTO(group);
    }

    @Override
    public List<TravelGroupDTO> getGroupsByUser(Long userId) {
        return travelGroupRepository.findGroupsByMemberId(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TravelGroupDTO> searchGroups(String query) {
        return travelGroupRepository.searchGroups(query).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TravelGroupDTO addMemberToGroup(Long groupId, Long userId) {
        TravelGroup group = travelGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Travel Group not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        group.getMembers().add(user);
        TravelGroup updatedGroup = travelGroupRepository.save(group);
        return mapToDTO(updatedGroup);
    }

    @Override
    public TravelGroupDTO removeMemberFromGroup(Long groupId, Long userId) {
        TravelGroup group = travelGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Travel Group not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        group.getMembers().remove(user);
        TravelGroup updatedGroup = travelGroupRepository.save(group);
        return mapToDTO(updatedGroup);
    }

    @Override
    public TravelGroupDTO addTripToGroup(Long groupId, Long tripId) {
        TravelGroup group = travelGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Travel Group not found"));
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));
        
        group.getSharedTrips().add(trip);
        TravelGroup updatedGroup = travelGroupRepository.save(group);
        return mapToDTO(updatedGroup);
    }

    @Override
    public TravelGroupDTO removeTripFromGroup(Long groupId, Long tripId) {
        TravelGroup group = travelGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Travel Group not found"));
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));
        
        group.getSharedTrips().remove(trip);
        TravelGroup updatedGroup = travelGroupRepository.save(group);
        return mapToDTO(updatedGroup);
    }

    private TravelGroupDTO mapToDTO(TravelGroup group) {
        return TravelGroupDTO.builder()
                .id(group.getId())
                .groupName(group.getGroupName())
                .description(group.getDescription())
                .createdByUserId(group.getCreatedBy().getId())
                .createdByUserName(group.getCreatedBy().getUsername())
                .memberIds(group.getMembers().stream().map(User::getId).collect(Collectors.toSet()))
                .memberCount(group.getMembers().size())
                .sharedTripIds(group.getSharedTrips().stream().map(Trip::getId).collect(Collectors.toSet()))
                .createdAt(group.getCreatedAt())
                .updatedAt(group.getUpdatedAt())
                .build();
    }
}
