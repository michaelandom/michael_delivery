package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.model.DriverGuide;
import com.michael_delivery.backend.dto.DriverGuideDTO;
import com.michael_delivery.backend.dto.PageableBodyDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.service.DriverGuideService;
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
@RequestMapping(value = "/api/driverGuides", produces = MediaType.APPLICATION_JSON_VALUE)
public class DriverGuideController {

    private final DriverGuideService driverGuideService;

    public DriverGuideController(final DriverGuideService driverGuideService) {
        this.driverGuideService = driverGuideService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGE_DRIVER_GUIDES','VIEW_DRIVER_GUIDE_MANY')")
    public ResponseEntity<List<DriverGuideDTO>> getAllDriverGuide(
    ) {
        return ResponseEntity.ok(driverGuideService.findAll());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('MANAGE_DRIVER_GUIDES','VIEW_DRIVER_GUIDE_MANY')")
    public ResponseEntity<Page<DriverGuideDTO>> searchDriverGuide(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "driverGuideId:asc") String[] sortBy,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) boolean isImportant
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<DriverGuide> spec = new GenericSpecification<>();
        Specification<DriverGuide> descriptionSpec = spec.equals("description", description);
        Specification<DriverGuide> isImportantSpec = spec.equals("isImportant", isImportant);
        Specification<DriverGuide> finalSpec = Specification.where(descriptionSpec).and(isImportantSpec);
        return ResponseEntity.ok(driverGuideService.search(finalSpec,pageable.getPageable()));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_DRIVER_GUIDES','VIEW_DRIVER_GUIDE_MANY')")
    public ResponseEntity<Page<DriverGuideDTO>> getAllDriverGuide(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "driverGuideId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(driverGuideService.findAll(pageable.getPageable()));
    }
    
    @GetMapping("/{driverGuideId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_DRIVER_GUIDES','VIEW_DRIVER_GUIDE')")
    public ResponseEntity<DriverGuideDTO> getDriverGuide(
            @PathVariable(name = "driverGuideId") final Long driverGuideId) {
        return ResponseEntity.ok(driverGuideService.get(driverGuideId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @PreAuthorize("hasAnyAuthority('MANAGE_DRIVER_GUIDES','UPDATE_EVENT_GROUP_ONE')")
    public ResponseEntity<Long> createDriverGuide(
            @RequestBody @Valid final DriverGuideDTO driverGuideDTO) {
        final Long createdDriverGuideId = driverGuideService.create(driverGuideDTO);
        return new ResponseEntity<>(createdDriverGuideId, HttpStatus.CREATED);
    }

    @PutMapping("/{driverGuideId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_DRIVER_GUIDES','UPDATE_EVENT_GROUP_ONE')")
    public ResponseEntity<Long> updateDriverGuide(
            @PathVariable(name = "driverGuideId") final Long driverGuideId,
            @RequestBody @Valid final DriverGuideDTO driverGuideDTO) {
        driverGuideService.update(driverGuideId, driverGuideDTO);
        return ResponseEntity.ok(driverGuideId);
    }

    @DeleteMapping("/{driverGuideId}")
    @ApiResponse(responseCode = "204")
    @PreAuthorize("hasAnyAuthority('MANAGE_DRIVER_GUIDES','DELETE_DRIVER_GUIDE_ONE')")
    public ResponseEntity<Void> deleteDriverGuide(
            @PathVariable(name = "driverGuideId") final Long driverGuideId) {
        driverGuideService.delete(driverGuideId);
        return ResponseEntity.noContent().build();
    }

}
