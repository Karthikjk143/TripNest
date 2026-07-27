package com.tripnest.user.service;

import com.tripnest.user.dto.UserProfileDTO;
import com.tripnest.user.entity.User;
import com.tripnest.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserProfileDTO getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToDTO(user);
    }

    @Override
    public UserProfileDTO updateUserProfile(Long userId, UserProfileDTO profileDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (profileDTO.getFirstName() != null) user.setFirstName(profileDTO.getFirstName());
        if (profileDTO.getLastName() != null) user.setLastName(profileDTO.getLastName());
        if (profileDTO.getPhone() != null) user.setPhone(profileDTO.getPhone());
        if (profileDTO.getProfilePictureUrl() != null) user.setProfilePictureUrl(profileDTO.getProfilePictureUrl());
        if (profileDTO.getBio() != null) user.setBio(profileDTO.getBio());
        if (profileDTO.getCountry() != null) user.setCountry(profileDTO.getCountry());
        if (profileDTO.getCity() != null) user.setCity(profileDTO.getCity());
        if (profileDTO.getPreferredLanguage() != null) user.setPreferredLanguage(profileDTO.getPreferredLanguage());
        if (profileDTO.getNotificationsEnabled() != null) user.setNotificationsEnabled(profileDTO.getNotificationsEnabled());
        if (profileDTO.getTimezone() != null) user.setTimezone(profileDTO.getTimezone());
        
        User updatedUser = userRepository.save(user);
        return mapToDTO(updatedUser);
    }

    @Override
    public UserProfileDTO getProfileByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToDTO(user);
    }

    @Override
    public UserProfileDTO getProfileByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToDTO(user);
    }

    private UserProfileDTO mapToDTO(User user) {
        return UserProfileDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .profilePictureUrl(user.getProfilePictureUrl())
                .bio(user.getBio())
                .country(user.getCountry())
                .city(user.getCity())
                .preferredLanguage(user.getPreferredLanguage())
                .notificationsEnabled(user.getNotificationsEnabled())
                .timezone(user.getTimezone())
                .build();
    }
}
