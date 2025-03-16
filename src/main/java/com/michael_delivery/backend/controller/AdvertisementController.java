package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.model.Advertisement;
import com.michael_delivery.backend.dto.AdvertisementDTO;
import com.michael_delivery.backend.dto.PageableBodyDTO;
import com.michael_delivery.backend.service.AdvertisementService;
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
@RequestMapping(value = "/api/advertisements",produces = MediaType.APPLICATION_JSON_VALUE)
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    public AdvertisementController(final AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_ADVERTISEMENTS','VIEW_ADVERTISEMENT_MANY')")
    public ResponseEntity<Page<AdvertisementDTO>> getAllAdvertisements(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "advertisementId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(advertisementService.findAll(pageable.getPageable()));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGE_ADVERTISEMENTS','VIEW_ADVERTISEMENT_MANY')")
    public ResponseEntity<List<AdvertisementDTO>> getAllAdvertisements(
    ) {
        return ResponseEntity.ok(advertisementService.findAll());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('MANAGE_ADVERTISEMENTS','VIEW_ADVERTISEMENT_MANY')")
    public ResponseEntity<Page<AdvertisementDTO>> searchAdvertisements(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "advertisementId:asc") String[] sortBy,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<Advertisement> spec = new GenericSpecification<>();
        Specification<Advertisement> titleSpec = spec.contains("title", title);
        Specification<Advertisement> contentSpec = spec.contains("content", content);
        Specification<Advertisement> finalSpec = Specification.where(titleSpec).and(contentSpec);
        return ResponseEntity.ok(advertisementService.search(finalSpec,pageable.getPageable()));
    }

    @GetMapping("/{advertisementId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_ADVERTISEMENTS','VIEW_ADVERTISEMENT')")
    public ResponseEntity<AdvertisementDTO> getAdvertisement(
            @PathVariable(name = "advertisementId") final Long advertisementId) {
        return ResponseEntity.ok(advertisementService.get(advertisementId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @PreAuthorize("hasAnyAuthority('MANAGE_ADVERTISEMENTS','UPDATE_ADVERTISEMENT_ONE')")
    public ResponseEntity<Long> createAdvertisement(
            @RequestBody @Valid final AdvertisementDTO advertisementDTO) {
        final Long createdAdvertisementId = advertisementService.create(advertisementDTO);
        return new ResponseEntity<>(createdAdvertisementId, HttpStatus.CREATED);
    }

    @PutMapping("/{advertisementId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_ADVERTISEMENTS','UPDATE_ADVERTISEMENT_ONE')")
    public ResponseEntity<Long> updateAdvertisement(
            @PathVariable(name = "advertisementId") final Long advertisementId,
            @RequestBody @Valid final AdvertisementDTO advertisementDTO) {
        advertisementService.update(advertisementId, advertisementDTO);
        return ResponseEntity.ok(advertisementId);
    }

    @DeleteMapping("/{advertisementId}")
    @ApiResponse(responseCode = "204")
    @PreAuthorize("hasAnyAuthority('MANAGE_ADVERTISEMENTS','DELETE_ADVERTISEMENT_ONE')")
    public ResponseEntity<Void> deleteAdvertisement(
            @PathVariable(name = "advertisementId") final Long advertisementId) {
        advertisementService.delete(advertisementId);
        return ResponseEntity.noContent().build();
    }

}
