package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.model.Announcement;
import com.michael_delivery.backend.dto.AnnouncementDTO;
import com.michael_delivery.backend.dto.PageableBodyDTO;
import com.michael_delivery.backend.service.AnnouncementService;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/announcements", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnnouncementController {

    private final AnnouncementService announcementService;

    public AnnouncementController(final AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_ANNOUNCEMENTS','VIEW_ANNOUNCEMENT_MANY')")
    public ResponseEntity<Page<AnnouncementDTO>> getAllAnnouncement(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "announcementId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);

        return ResponseEntity.ok(announcementService.findAll(pageable.getPageable()));
    }
    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGE_ANNOUNCEMENTS','VIEW_ANNOUNCEMENT_MANY')")
    public ResponseEntity<List<AnnouncementDTO>> getAllAnnouncement(
    ) {
        return ResponseEntity.ok(announcementService.findAll());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('MANAGE_ANNOUNCEMENTS','VIEW_ANNOUNCEMENT_MANY')")
    public ResponseEntity<Page<AnnouncementDTO>> searchAnnouncement(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "announcementId:asc") String[] sortBy,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<Announcement> spec = new GenericSpecification<>();
        Specification<Announcement> titleSpec = spec.contains("title", title);
        Specification<Announcement> contentSpec = spec.contains("content", content);
        Specification<Announcement> finalSpec = Specification.where(titleSpec).and(contentSpec);
        return ResponseEntity.ok(announcementService.search(finalSpec,pageable.getPageable()));
    }

    @GetMapping("/{announcementId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_ANNOUNCEMENTS','UPDATE_ANNOUNCEMENT')")
    public ResponseEntity<AnnouncementDTO> getAnnouncement(
            @PathVariable(name = "announcementId") final Long announcementId) {
        return ResponseEntity.ok(announcementService.get(announcementId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @PreAuthorize("hasAnyAuthority('MANAGE_ANNOUNCEMENTS','UPDATE_ANNOUNCEMENT_ONE')")
    public ResponseEntity<Long> createAnnouncement(
            @RequestBody @Valid final AnnouncementDTO announcementDTO) {
        final Long createdAnnouncementId = announcementService.create(announcementDTO);
        return new ResponseEntity<>(createdAnnouncementId, HttpStatus.CREATED);
    }

    @PutMapping("/{announcementId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_ANNOUNCEMENTS','VIEW_ANNOUNCEMENT_MANY')")
    public ResponseEntity<Long> updateAnnouncement(
            @PathVariable(name = "announcementId") final Long announcementId,
            @RequestBody @Valid final AnnouncementDTO announcementDTO) {
        announcementService.update(announcementId, announcementDTO);
        return ResponseEntity.ok(announcementId);
    }

    @DeleteMapping("/{announcementId}")
    @ApiResponse(responseCode = "204")
    @PreAuthorize("hasAnyAuthority('MANAGE_ANNOUNCEMENTS','DELETE_ANNOUNCEMENT_ONE')")
    public ResponseEntity<Void> deleteAnnouncement(
            @PathVariable(name = "announcementId") final Long announcementId) {
        announcementService.delete(announcementId);
        return ResponseEntity.noContent().build();
    }

}
