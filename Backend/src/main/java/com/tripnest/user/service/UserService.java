package com.tripnest.user.service;

import com.tripnest.user.dto.UserProfileDTO;
import java.util.Optional;

public interface UserService {
    UserProfileDTO getUserProfile(Long userId);
    UserProfileDTO updateUserProfile(Long userId, UserProfileDTO profileDTO);
    UserProfileDTO getProfileByEmail(String email);
    UserProfileDTO getProfileByUsername(String username);
}
