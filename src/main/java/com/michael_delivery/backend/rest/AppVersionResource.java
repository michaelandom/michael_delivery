package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.domain.Announcement;
import com.michael_delivery.backend.domain.AppVersion;
import com.michael_delivery.backend.model.AnnouncementDTO;
import com.michael_delivery.backend.model.PageableBodyDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.AppVersionDTO;
import com.michael_delivery.backend.service.AppVersionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/appVersions", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppVersionResource {

    private final AppVersionService appVersionService;

    public AppVersionResource(final AppVersionService appVersionService) {
        this.appVersionService = appVersionService;
    }

    @GetMapping
    public ResponseEntity<Page<AppVersionDTO>> getAllAppVersion(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "appVersionId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);

        return ResponseEntity.ok(appVersionService.findAll(pageable.getPageable()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AppVersionDTO>> getAllAppVersion(
    ) {
        return ResponseEntity.ok(appVersionService.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<AppVersionDTO>> searchAppVersion(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "appVersionId:asc") String[] sortBy,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<AppVersion> spec = new GenericSpecification<>();
        Specification<AppVersion> titleSpec = spec.contains("title", title);
        Specification<AppVersion> contentSpec = spec.contains("content", content);
        Specification<AppVersion> finalSpec = Specification.where(titleSpec).and(contentSpec);
        return ResponseEntity.ok(appVersionService.search(finalSpec,pageable.getPageable()));
    }
    @GetMapping("/{appVersionId}")
    public ResponseEntity<AppVersionDTO> getAppVersion(
            @PathVariable(name = "appVersionId") final Integer appVersionId) {
        return ResponseEntity.ok(appVersionService.get(appVersionId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createAppVersion(
            @Valid @RequestBody final AppVersionDTO appVersionDTO) {
        final Integer createdAppVersionId = appVersionService.create(appVersionDTO);
        return new ResponseEntity<>(createdAppVersionId, HttpStatus.CREATED);
    }

    @PutMapping("/{appVersionId}")
    public ResponseEntity<Integer> updateAppVersion(
            @PathVariable(name = "appVersionId") final Integer appVersionId,
            @RequestBody @Valid final AppVersionDTO appVersionDTO) {
        appVersionService.update(appVersionId, appVersionDTO);
        return ResponseEntity.ok(appVersionId);
    }

    @DeleteMapping("/{appVersionId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAppVersion(
            @PathVariable(name = "appVersionId") final Integer appVersionId) {
        appVersionService.delete(appVersionId);
        return ResponseEntity.noContent().build();
    }

}
