package com.tripnest.user.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String profilePictureUrl;
    private String bio;
    private String country;
    private String city;
    private String preferredLanguage;
    private Boolean notificationsEnabled;
    private String timezone;
}
