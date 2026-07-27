package com.tripnest.itinerary.dto;

import com.tripnest.itinerary.entity.ActivityType;
import lombok.*;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDTO {
    private Long id;
    private Long itineraryDayId;
    private String title;
    private String description;
    private ActivityType type;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;
    private Double latitude;
    private Double longitude;
    private String imageUrl;
    private Double estimatedCost;
    private Integer remindMinutesBefore;
}
