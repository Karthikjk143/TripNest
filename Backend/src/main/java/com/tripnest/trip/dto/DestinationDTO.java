package com.tripnest.trip.dto;

import com.tripnest.trip.entity.TripStatus;
import lombok.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DestinationDTO {
    private Long id;
    private String name;
    private String country;
    private String region;
    private String description;
    private String imageUrl;
    private Double latitude;
    private Double longitude;
    private String attractions;
    private String bestTimeToVisit;
    private Integer popularityScore;
}
