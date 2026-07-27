package com.tripnest.media.repository;

import com.tripnest.media.entity.Document;
import com.tripnest.media.entity.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByTripIdOrderByUploadedAtDesc(Long tripId);
    List<Document> findByTripIdAndDocumentType(Long tripId, DocumentType documentType);
    List<Document> findByUploadedByIdAndTripId(Long userId, Long tripId);
}
