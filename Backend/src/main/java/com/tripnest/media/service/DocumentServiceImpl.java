package com.tripnest.media.service;

import com.tripnest.media.dto.DocumentDTO;
import com.tripnest.media.entity.Document;
import com.tripnest.media.entity.DocumentType;
import com.tripnest.media.repository.DocumentRepository;
import com.tripnest.trip.entity.Trip;
import com.tripnest.trip.repository.TripRepository;
import com.tripnest.user.entity.User;
import com.tripnest.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;

    public DocumentServiceImpl(DocumentRepository documentRepository, TripRepository tripRepository, UserRepository userRepository) {
        this.documentRepository = documentRepository;
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
    }

    @Override
    public DocumentDTO uploadDocument(DocumentDTO documentDTO) {
        Trip trip = tripRepository.findById(documentDTO.getTripId())
                .orElseThrow(() -> new RuntimeException("Trip not found"));
        User user = userRepository.findById(documentDTO.getUploadedByUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Document document = Document.builder()
                .trip(trip)
                .uploadedBy(user)
                .documentName(documentDTO.getDocumentName())
                .documentType(documentDTO.getDocumentType())
                .fileUrl(documentDTO.getFileUrl())
                .fileSize(documentDTO.getFileSize())
                .mimeType(documentDTO.getMimeType())
                .notes(documentDTO.getNotes())
                .build();
        
        Document savedDocument = documentRepository.save(document);
        return mapToDTO(savedDocument);
    }

    @Override
    public DocumentDTO updateDocument(Long id, DocumentDTO documentDTO) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        
        document.setDocumentName(documentDTO.getDocumentName());
        document.setNotes(documentDTO.getNotes());
        
        Document updatedDocument = documentRepository.save(document);
        return mapToDTO(updatedDocument);
    }

    @Override
    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }

    @Override
    public DocumentDTO getDocumentById(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        return mapToDTO(document);
    }

    @Override
    public List<DocumentDTO> getDocumentsByTrip(Long tripId) {
        return documentRepository.findByTripIdOrderByUploadedAtDesc(tripId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DocumentDTO> getDocumentsByType(Long tripId, String documentType) {
        return documentRepository.findByTripIdAndDocumentType(tripId, DocumentType.valueOf(documentType)).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DocumentDTO> getDocumentsByUser(Long tripId, Long userId) {
        return documentRepository.findByUploadedByIdAndTripId(userId, tripId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private DocumentDTO mapToDTO(Document document) {
        return DocumentDTO.builder()
                .id(document.getId())
                .tripId(document.getTrip().getId())
                .uploadedByUserId(document.getUploadedBy().getId())
                .uploadedByUserName(document.getUploadedBy().getUsername())
                .documentName(document.getDocumentName())
                .documentType(document.getDocumentType())
                .fileUrl(document.getFileUrl())
                .fileSize(document.getFileSize())
                .mimeType(document.getMimeType())
                .notes(document.getNotes())
                .uploadedAt(document.getUploadedAt())
                .build();
    }
}
