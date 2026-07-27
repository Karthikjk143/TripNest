package com.tripnest.group.service;

import com.tripnest.group.dto.TravelGroupDTO;
import java.util.List;

public interface TravelGroupService {
    TravelGroupDTO createGroup(TravelGroupDTO groupDTO);
    TravelGroupDTO updateGroup(Long id, TravelGroupDTO groupDTO);
    void deleteGroup(Long id);
    TravelGroupDTO getGroupById(Long id);
    List<TravelGroupDTO> getGroupsByUser(Long userId);
    List<TravelGroupDTO> searchGroups(String query);
    TravelGroupDTO addMemberToGroup(Long groupId, Long userId);
    TravelGroupDTO removeMemberFromGroup(Long groupId, Long userId);
    TravelGroupDTO addTripToGroup(Long groupId, Long tripId);
    TravelGroupDTO removeTripFromGroup(Long groupId, Long tripId);
}
