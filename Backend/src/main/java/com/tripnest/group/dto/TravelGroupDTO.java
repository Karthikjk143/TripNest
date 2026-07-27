package com.tripnest.group.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TravelGroupDTO {
    private Long id;
    private String groupName;
    private String description;
    private Long createdByUserId;
    private String createdByUserName;
    private Set<Long> memberIds;
    private Integer memberCount;
    private Set<Long> sharedTripIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
