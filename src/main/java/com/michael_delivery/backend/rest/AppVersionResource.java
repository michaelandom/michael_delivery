package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.AppVersionDTO;
import com.michael_delivery.backend.service.AppVersionService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<AppVersionDTO>> getAllAppVersions() {
        return ResponseEntity.ok(appVersionService.findAll());
    }

    @GetMapping("/{appVersionId}")
    public ResponseEntity<AppVersionDTO> getAppVersion(
            @PathVariable(name = "appVersionId") final Integer appVersionId) {
        return ResponseEntity.ok(appVersionService.get(appVersionId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createAppVersion(
            @RequestBody @Valid final AppVersionDTO appVersionDTO) {
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
