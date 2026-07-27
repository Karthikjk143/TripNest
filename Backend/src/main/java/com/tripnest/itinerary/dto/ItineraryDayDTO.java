package com.tripnest.itinerary.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItineraryDayDTO {
    private Long id;
    private Long tripId;
    private LocalDate date;
    private Integer dayNumber;
    private String notes;
    private List<ActivityDTO> activities;
}
