package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.DriverGuideDTO;
import com.michael_delivery.backend.service.DriverGuideService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/driverGuides", produces = MediaType.APPLICATION_JSON_VALUE)
public class DriverGuideResource {

    private final DriverGuideService driverGuideService;

    public DriverGuideResource(final DriverGuideService driverGuideService) {
        this.driverGuideService = driverGuideService;
    }

    @GetMapping
    public ResponseEntity<List<DriverGuideDTO>> getAllDriverGuides() {
        return ResponseEntity.ok(driverGuideService.findAll());
    }

    @GetMapping("/{driverGuideId}")
    public ResponseEntity<DriverGuideDTO> getDriverGuide(
            @PathVariable(name = "driverGuideId") final Long driverGuideId) {
        return ResponseEntity.ok(driverGuideService.get(driverGuideId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDriverGuide(
            @RequestBody @Valid final DriverGuideDTO driverGuideDTO) {
        final Long createdDriverGuideId = driverGuideService.create(driverGuideDTO);
        return new ResponseEntity<>(createdDriverGuideId, HttpStatus.CREATED);
    }

    @PutMapping("/{driverGuideId}")
    public ResponseEntity<Long> updateDriverGuide(
            @PathVariable(name = "driverGuideId") final Long driverGuideId,
            @RequestBody @Valid final DriverGuideDTO driverGuideDTO) {
        driverGuideService.update(driverGuideId, driverGuideDTO);
        return ResponseEntity.ok(driverGuideId);
    }

    @DeleteMapping("/{driverGuideId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDriverGuide(
            @PathVariable(name = "driverGuideId") final Long driverGuideId) {
        driverGuideService.delete(driverGuideId);
        return ResponseEntity.noContent().build();
    }

}
