package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.domain.Advertisement;
import com.michael_delivery.backend.model.AdvertisementDTO;
import com.michael_delivery.backend.model.PageableBodyDTO;
import com.michael_delivery.backend.service.AdvertisementService;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/advertisements",produces = MediaType.APPLICATION_JSON_VALUE)
public class AdvertisementResource {

    private final AdvertisementService advertisementService;

    public AdvertisementResource(final AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @GetMapping
    public ResponseEntity<Page<AdvertisementDTO>> getAllAdvertisements(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "advertisementId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(advertisementService.findAll(pageable.getPageable()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AdvertisementDTO>> getAllAdvertisements(
    ) {
        return ResponseEntity.ok(advertisementService.findAll());
    }

    @GetMapping("/search")
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
    public ResponseEntity<AdvertisementDTO> getAdvertisement(
            @PathVariable(name = "advertisementId") final Long advertisementId) {
        return ResponseEntity.ok(advertisementService.get(advertisementId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createAdvertisement(
            @RequestBody @Valid final AdvertisementDTO advertisementDTO) {
        final Long createdAdvertisementId = advertisementService.create(advertisementDTO);
        return new ResponseEntity<>(createdAdvertisementId, HttpStatus.CREATED);
    }

    @PutMapping("/{advertisementId}")
    public ResponseEntity<Long> updateAdvertisement(
            @PathVariable(name = "advertisementId") final Long advertisementId,
            @RequestBody @Valid final AdvertisementDTO advertisementDTO) {
        advertisementService.update(advertisementId, advertisementDTO);
        return ResponseEntity.ok(advertisementId);
    }

    @DeleteMapping("/{advertisementId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAdvertisement(
            @PathVariable(name = "advertisementId") final Long advertisementId) {
        advertisementService.delete(advertisementId);
        return ResponseEntity.noContent().build();
    }

}
