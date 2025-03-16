package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.model.AppVersion;
import com.michael_delivery.backend.dto.PageableBodyDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.dto.AppVersionDTO;
import com.michael_delivery.backend.service.AppVersionService;
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
@RequestMapping(value = "/api/appVersions", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppVersionController {

    private final AppVersionService appVersionService;

    public AppVersionController(final AppVersionService appVersionService) {
        this.appVersionService = appVersionService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_APP_VERSIONS','VIEW_APP_VERSION_MANY')")
    public ResponseEntity<Page<AppVersionDTO>> getAllAppVersion(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "appVersionId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);

        return ResponseEntity.ok(appVersionService.findAll(pageable.getPageable()));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGE_APP_VERSIONS','VIEW_APP_VERSION_MANY')")
    public ResponseEntity<List<AppVersionDTO>> getAllAppVersion(
    ) {
        return ResponseEntity.ok(appVersionService.findAll());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('MANAGE_APP_VERSIONS','VIEW_APP_VERSION_MANY')")
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
    @PreAuthorize("hasAnyAuthority('MANAGE_APP_VERSIONS','VIEW_APP_VERSION')")
    public ResponseEntity<AppVersionDTO> getAppVersion(
            @PathVariable(name = "appVersionId") final Integer appVersionId) {
        return ResponseEntity.ok(appVersionService.get(appVersionId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @PreAuthorize("hasAnyAuthority('MANAGE_APP_VERSIONS','UPDATE_APP_VERSION_ONE')")
    public ResponseEntity<Integer> createAppVersion(
            @Valid @RequestBody final AppVersionDTO appVersionDTO) {
        final Integer createdAppVersionId = appVersionService.create(appVersionDTO);
        return new ResponseEntity<>(createdAppVersionId, HttpStatus.CREATED);
    }

    @PutMapping("/{appVersionId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_APP_VERSIONS','UPDATE_APP_VERSION_ONE')")
    public ResponseEntity<Integer> updateAppVersion(
            @PathVariable(name = "appVersionId") final Integer appVersionId,
            @RequestBody @Valid final AppVersionDTO appVersionDTO) {
        appVersionService.update(appVersionId, appVersionDTO);
        return ResponseEntity.ok(appVersionId);
    }

    @DeleteMapping("/{appVersionId}")
    @ApiResponse(responseCode = "204")
    @PreAuthorize("hasAnyAuthority('MANAGE_APP_VERSIONS','DELETE_APP_VERSION_ONE')")
    public ResponseEntity<Void> deleteAppVersion(
            @PathVariable(name = "appVersionId") final Integer appVersionId) {
        appVersionService.delete(appVersionId);
        return ResponseEntity.noContent().build();
    }

}
