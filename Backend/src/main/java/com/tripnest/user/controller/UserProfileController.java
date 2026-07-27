package com.tripnest.user.controller;

import com.tripnest.user.dto.UserProfileDTO;
import com.tripnest.user.service.UserService;
import com.tripnest.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
@CrossOrigin(origins = "*")
public class UserProfileController {

    private final UserService userService;

    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserProfileDTO>> getUserProfile(@PathVariable Long userId) {
        UserProfileDTO profile = userService.getUserProfile(userId);
        return ResponseEntity.ok(ApiResponse.success(profile, "Profile retrieved successfully"));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<UserProfileDTO>> getProfileByEmail(@PathVariable String email) {
        UserProfileDTO profile = userService.getProfileByEmail(email);
        return ResponseEntity.ok(ApiResponse.success(profile, "Profile retrieved successfully"));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<ApiResponse<UserProfileDTO>> getProfileByUsername(@PathVariable String username) {
        UserProfileDTO profile = userService.getProfileByUsername(username);
        return ResponseEntity.ok(ApiResponse.success(profile, "Profile retrieved successfully"));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserProfileDTO>> updateUserProfile(@PathVariable Long userId, @RequestBody UserProfileDTO profileDTO) {
        UserProfileDTO updated = userService.updateUserProfile(userId, profileDTO);
        return ResponseEntity.ok(ApiResponse.success(updated, "Profile updated successfully"));
    }
}
