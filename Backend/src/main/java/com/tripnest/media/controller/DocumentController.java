package com.tripnest.media.controller;

import com.tripnest.media.dto.DocumentDTO;
import com.tripnest.media.service.DocumentService;
import com.tripnest.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/documents")
@CrossOrigin(origins = "*")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DocumentDTO>> uploadDocument(@RequestBody DocumentDTO documentDTO) {
        DocumentDTO created = documentService.uploadDocument(documentDTO);
        return ResponseEntity.ok(ApiResponse.success(created, "Document uploaded successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DocumentDTO>> getDocumentById(@PathVariable Long id) {
        DocumentDTO document = documentService.getDocumentById(id);
        return ResponseEntity.ok(ApiResponse.success(document, "Document retrieved successfully"));
    }

    @GetMapping("/trip/{tripId}")
    public ResponseEntity<ApiResponse<List<DocumentDTO>>> getDocumentsByTrip(@PathVariable Long tripId) {
        List<DocumentDTO> documents = documentService.getDocumentsByTrip(tripId);
        return ResponseEntity.ok(ApiResponse.success(documents, "Documents retrieved successfully"));
    }

    @GetMapping("/trip/{tripId}/type/{documentType}")
    public ResponseEntity<ApiResponse<List<DocumentDTO>>> getDocumentsByType(@PathVariable Long tripId, @PathVariable String documentType) {
        List<DocumentDTO> documents = documentService.getDocumentsByType(tripId, documentType);
        return ResponseEntity.ok(ApiResponse.success(documents, "Documents retrieved successfully"));
    }

    @GetMapping("/trip/{tripId}/user/{userId}")
    public ResponseEntity<ApiResponse<List<DocumentDTO>>> getDocumentsByUser(@PathVariable Long tripId, @PathVariable Long userId) {
        List<DocumentDTO> documents = documentService.getDocumentsByUser(tripId, userId);
        return ResponseEntity.ok(ApiResponse.success(documents, "Documents retrieved successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DocumentDTO>> updateDocument(@PathVariable Long id, @RequestBody DocumentDTO documentDTO) {
        DocumentDTO updated = documentService.updateDocument(id, documentDTO);
        return ResponseEntity.ok(ApiResponse.success(updated, "Document updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Document deleted successfully"));
    }
}
