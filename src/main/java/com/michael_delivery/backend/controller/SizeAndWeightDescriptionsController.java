package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.model.SizeAndWeightDescriptions;
import com.michael_delivery.backend.enums.SizeAndWeightType;
import com.michael_delivery.backend.dto.PageableBodyDTO;
import com.michael_delivery.backend.dto.SizeAndWeightDescriptionsDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.repository.SizeAndWeightDescriptionsRepository;
import com.michael_delivery.backend.service.SizeAndWeightDescriptionsService;
import com.michael_delivery.backend.util.CustomCollectors;
import com.michael_delivery.backend.util.ReferencedException;
import com.michael_delivery.backend.util.ReferencedWarning;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/sizeAndWeightDescriptions", produces = MediaType.APPLICATION_JSON_VALUE)
public class SizeAndWeightDescriptionsController {

    private final SizeAndWeightDescriptionsService sizeAndWeightDescriptionsService;
    private final SizeAndWeightDescriptionsRepository sizeAndWeightDescriptionsRepository;

    public SizeAndWeightDescriptionsController(
            final SizeAndWeightDescriptionsService sizeAndWeightDescriptionsService,
            final SizeAndWeightDescriptionsRepository sizeAndWeightDescriptionsRepository) {
        this.sizeAndWeightDescriptionsService = sizeAndWeightDescriptionsService;
        this.sizeAndWeightDescriptionsRepository = sizeAndWeightDescriptionsRepository;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGE_SIZE_AND_WEIGHT_DESCRIPTIONS','VIEW_SIZE_AND_WEIGHT_DESCRIPTION_MANY')")
    public ResponseEntity<List<SizeAndWeightDescriptionsDTO>> getAllSizeAndWeightDescriptions(
    ) {
        return ResponseEntity.ok(sizeAndWeightDescriptionsService.findAll());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('MANAGE_SIZE_AND_WEIGHT_DESCRIPTIONS','VIEW_SIZE_AND_WEIGHT_DESCRIPTION_MANY')")
    public ResponseEntity<Page<SizeAndWeightDescriptionsDTO>> searchSizeAndWeightDescriptions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "sizeWeightDescriptionId:asc") String[] sortBy,
            @RequestParam(required = false) SizeAndWeightType sizeAndWeight,
            @RequestParam(required = false) String sizeDescription,
            @RequestParam(required = false) String weight,
            @RequestParam(required = false) String isLatest
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<SizeAndWeightDescriptions> spec = new GenericSpecification<>();
        Specification<SizeAndWeightDescriptions> nameSpec = spec.equals("sizeAndWeight", sizeAndWeight);
        Specification<SizeAndWeightDescriptions> codeSpec = spec.contains("sizeDescription", sizeDescription);
        Specification<SizeAndWeightDescriptions> weightSpec = spec.equals("weight", weight);
        Specification<SizeAndWeightDescriptions> isLatestSpec = spec.equals("isLatest", isLatest);
        Specification<SizeAndWeightDescriptions> finalSpec = Specification.where(nameSpec)
                .and(codeSpec)
                .and(isLatestSpec)
                .and(weightSpec);
        return ResponseEntity.ok(sizeAndWeightDescriptionsService.search(finalSpec,pageable.getPageable()));
    }
    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_SIZE_AND_WEIGHT_DESCRIPTIONS','VIEW_SIZE_AND_WEIGHT_DESCRIPTION_MANY')")
    public ResponseEntity<Page<SizeAndWeightDescriptionsDTO>> getAllSizeAndWeightDescriptions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "sizeWeightDescriptionId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(sizeAndWeightDescriptionsService.findAll(pageable.getPageable()));
    }


    @GetMapping("/{sizeWeightDescriptionId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_SIZE_AND_WEIGHT_DESCRIPTIONS','VIEW_SIZE_AND_WEIGHT_DESCRIPTION')")
    public ResponseEntity<SizeAndWeightDescriptionsDTO> getSizeAndWeightDescriptions(
            @PathVariable(name = "sizeWeightDescriptionId") final Long sizeWeightDescriptionId) {
        return ResponseEntity.ok(sizeAndWeightDescriptionsService.get(sizeWeightDescriptionId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @PreAuthorize("hasAnyAuthority('MANAGE_SIZE_AND_WEIGHT_DESCRIPTIONS','UPDATE_SIZE_AND_WEIGHT_DESCRIPTION_ONE')")
    public ResponseEntity<Long> createSizeAndWeightDescriptions(
            @RequestBody @Valid final SizeAndWeightDescriptionsDTO sizeAndWeightDescriptionsDTO) {
        final Long createdSizeWeightDescriptionId = sizeAndWeightDescriptionsService.create(sizeAndWeightDescriptionsDTO);
        return new ResponseEntity<>(createdSizeWeightDescriptionId, HttpStatus.CREATED);
    }

    @PutMapping("/{sizeWeightDescriptionId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_SIZE_AND_WEIGHT_DESCRIPTIONS','UPDATE_SIZE_AND_WEIGHT_DESCRIPTION_ONE')")
    public ResponseEntity<Long> updateSizeAndWeightDescriptions(
            @PathVariable(name = "sizeWeightDescriptionId") final Long sizeWeightDescriptionId,
            @RequestBody @Valid final SizeAndWeightDescriptionsDTO sizeAndWeightDescriptionsDTO) {
        sizeAndWeightDescriptionsService.update(sizeWeightDescriptionId, sizeAndWeightDescriptionsDTO);
        return ResponseEntity.ok(sizeWeightDescriptionId);
    }

    @DeleteMapping("/{sizeWeightDescriptionId}")
    @ApiResponse(responseCode = "204")
    @PreAuthorize("hasAnyAuthority('MANAGE_SIZE_AND_WEIGHT_DESCRIPTIONS','DELETE_SIZE_AND_WEIGHT_DESCRIPTION_ONE')")
    public ResponseEntity<Void> deleteSizeAndWeightDescriptions(
            @PathVariable(name = "sizeWeightDescriptionId") final Long sizeWeightDescriptionId) {
        final ReferencedWarning referencedWarning = sizeAndWeightDescriptionsService.getReferencedWarning(sizeWeightDescriptionId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        sizeAndWeightDescriptionsService.delete(sizeWeightDescriptionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/previousValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_SIZE_AND_WEIGHT_DESCRIPTIONS','VIEW_SIZE_AND_WEIGHT_DESCRIPTION_MANY')")
    public ResponseEntity<Map<Long, SizeAndWeightType>> getPreviousValues() {
        return ResponseEntity.ok(sizeAndWeightDescriptionsRepository.findAll(Sort.by("sizeWeightDescriptionId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(SizeAndWeightDescriptions::getSizeWeightDescriptionId, SizeAndWeightDescriptions::getSize)));
    }

}
