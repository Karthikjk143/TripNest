package com.tripnest.media.service;

import com.tripnest.media.dto.DocumentDTO;
import java.util.List;

public interface DocumentService {
    DocumentDTO uploadDocument(DocumentDTO documentDTO);
    DocumentDTO updateDocument(Long id, DocumentDTO documentDTO);
    void deleteDocument(Long id);
    DocumentDTO getDocumentById(Long id);
    List<DocumentDTO> getDocumentsByTrip(Long tripId);
    List<DocumentDTO> getDocumentsByType(Long tripId, String documentType);
    List<DocumentDTO> getDocumentsByUser(Long tripId, Long userId);
}
