package com.tripnest.group.controller;

import com.tripnest.group.dto.TravelGroupDTO;
import com.tripnest.group.service.TravelGroupService;
import com.tripnest.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/groups")
@CrossOrigin(origins = "*")
public class TravelGroupController {

    private final TravelGroupService travelGroupService;

    public TravelGroupController(TravelGroupService travelGroupService) {
        this.travelGroupService = travelGroupService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TravelGroupDTO>> createGroup(@RequestBody TravelGroupDTO groupDTO) {
        TravelGroupDTO created = travelGroupService.createGroup(groupDTO);
        return ResponseEntity.ok(ApiResponse.success(created, "Group created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TravelGroupDTO>> getGroupById(@PathVariable Long id) {
        TravelGroupDTO group = travelGroupService.getGroupById(id);
        return ResponseEntity.ok(ApiResponse.success(group, "Group retrieved successfully"));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<TravelGroupDTO>>> getGroupsByUser(@PathVariable Long userId) {
        List<TravelGroupDTO> groups = travelGroupService.getGroupsByUser(userId);
        return ResponseEntity.ok(ApiResponse.success(groups, "Groups retrieved successfully"));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<TravelGroupDTO>>> searchGroups(@RequestParam String query) {
        List<TravelGroupDTO> groups = travelGroupService.searchGroups(query);
        return ResponseEntity.ok(ApiResponse.success(groups, "Search results retrieved successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TravelGroupDTO>> updateGroup(@PathVariable Long id, @RequestBody TravelGroupDTO groupDTO) {
        TravelGroupDTO updated = travelGroupService.updateGroup(id, groupDTO);
        return ResponseEntity.ok(ApiResponse.success(updated, "Group updated successfully"));
    }

    @PostMapping("/{groupId}/members/{userId}")
    public ResponseEntity<ApiResponse<TravelGroupDTO>> addMemberToGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        TravelGroupDTO updated = travelGroupService.addMemberToGroup(groupId, userId);
        return ResponseEntity.ok(ApiResponse.success(updated, "Member added successfully"));
    }

    @DeleteMapping("/{groupId}/members/{userId}")
    public ResponseEntity<ApiResponse<TravelGroupDTO>> removeMemberFromGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        TravelGroupDTO updated = travelGroupService.removeMemberFromGroup(groupId, userId);
        return ResponseEntity.ok(ApiResponse.success(updated, "Member removed successfully"));
    }

    @PostMapping("/{groupId}/trips/{tripId}")
    public ResponseEntity<ApiResponse<TravelGroupDTO>> addTripToGroup(@PathVariable Long groupId, @PathVariable Long tripId) {
        TravelGroupDTO updated = travelGroupService.addTripToGroup(groupId, tripId);
        return ResponseEntity.ok(ApiResponse.success(updated, "Trip added successfully"));
    }

    @DeleteMapping("/{groupId}/trips/{tripId}")
    public ResponseEntity<ApiResponse<TravelGroupDTO>> removeTripFromGroup(@PathVariable Long groupId, @PathVariable Long tripId) {
        TravelGroupDTO updated = travelGroupService.removeTripFromGroup(groupId, tripId);
        return ResponseEntity.ok(ApiResponse.success(updated, "Trip removed successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteGroup(@PathVariable Long id) {
        travelGroupService.deleteGroup(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Group deleted successfully"));
    }
}
