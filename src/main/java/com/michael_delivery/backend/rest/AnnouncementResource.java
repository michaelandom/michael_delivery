package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.model.AnnouncementDTO;
import com.michael_delivery.backend.service.AnnouncementService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/announcements", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnnouncementResource {

    private final AnnouncementService announcementService;

    public AnnouncementResource(final AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @GetMapping
    public ResponseEntity<List<AnnouncementDTO>> getAllAnnouncements() {
        return ResponseEntity.ok(announcementService.findAll());
    }

    @GetMapping("/{announcementId}")
    public ResponseEntity<AnnouncementDTO> getAnnouncement(
            @PathVariable(name = "announcementId") final Long announcementId) {
        return ResponseEntity.ok(announcementService.get(announcementId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createAnnouncement(
            @RequestBody @Valid final AnnouncementDTO announcementDTO) {
        final Long createdAnnouncementId = announcementService.create(announcementDTO);
        return new ResponseEntity<>(createdAnnouncementId, HttpStatus.CREATED);
    }

    @PutMapping("/{announcementId}")
    public ResponseEntity<Long> updateAnnouncement(
            @PathVariable(name = "announcementId") final Long announcementId,
            @RequestBody @Valid final AnnouncementDTO announcementDTO) {
        announcementService.update(announcementId, announcementDTO);
        return ResponseEntity.ok(announcementId);
    }

    @DeleteMapping("/{announcementId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAnnouncement(
            @PathVariable(name = "announcementId") final Long announcementId) {
        announcementService.delete(announcementId);
        return ResponseEntity.noContent().build();
    }

}
