package com.tripnest.media.dto;

import com.tripnest.media.entity.DocumentType;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDTO {
    private Long id;
    private Long tripId;
    private Long uploadedByUserId;
    private String uploadedByUserName;
    private String documentName;
    private DocumentType documentType;
    private String fileUrl;
    private Long fileSize;
    private String mimeType;
    private String notes;
    private LocalDateTime uploadedAt;
}
