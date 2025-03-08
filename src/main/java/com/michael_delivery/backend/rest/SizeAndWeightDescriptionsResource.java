package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.domain.SizeAndWeightDescriptions;
import com.michael_delivery.backend.enums.SizeAndWeightType;
import com.michael_delivery.backend.model.PageableBodyDTO;
import com.michael_delivery.backend.model.SizeAndWeightDescriptionsDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.SizeAndWeightDescriptions;
import com.michael_delivery.backend.model.SizeAndWeightDescriptionsDTO;
import com.michael_delivery.backend.repos.SizeAndWeightDescriptionsRepository;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/sizeAndWeightDescriptions", produces = MediaType.APPLICATION_JSON_VALUE)
public class SizeAndWeightDescriptionsResource {

    private final SizeAndWeightDescriptionsService sizeAndWeightDescriptionsService;
    private final SizeAndWeightDescriptionsRepository sizeAndWeightDescriptionsRepository;

    public SizeAndWeightDescriptionsResource(
            final SizeAndWeightDescriptionsService sizeAndWeightDescriptionsService,
            final SizeAndWeightDescriptionsRepository sizeAndWeightDescriptionsRepository) {
        this.sizeAndWeightDescriptionsService = sizeAndWeightDescriptionsService;
        this.sizeAndWeightDescriptionsRepository = sizeAndWeightDescriptionsRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<SizeAndWeightDescriptionsDTO>> getAllSizeAndWeightDescriptions(
    ) {
        return ResponseEntity.ok(sizeAndWeightDescriptionsService.findAll());
    }

    @GetMapping("/search")
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
    public ResponseEntity<Page<SizeAndWeightDescriptionsDTO>> getAllSizeAndWeightDescriptions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "sizeWeightDescriptionId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(sizeAndWeightDescriptionsService.findAll(pageable.getPageable()));
    }


    @GetMapping("/{sizeWeightDescriptionId}")
    public ResponseEntity<SizeAndWeightDescriptionsDTO> getSizeAndWeightDescriptions(
            @PathVariable(name = "sizeWeightDescriptionId") final Long sizeWeightDescriptionId) {
        return ResponseEntity.ok(sizeAndWeightDescriptionsService.get(sizeWeightDescriptionId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createSizeAndWeightDescriptions(
            @RequestBody @Valid final SizeAndWeightDescriptionsDTO sizeAndWeightDescriptionsDTO) {
        final Long createdSizeWeightDescriptionId = sizeAndWeightDescriptionsService.create(sizeAndWeightDescriptionsDTO);
        return new ResponseEntity<>(createdSizeWeightDescriptionId, HttpStatus.CREATED);
    }

    @PutMapping("/{sizeWeightDescriptionId}")
    public ResponseEntity<Long> updateSizeAndWeightDescriptions(
            @PathVariable(name = "sizeWeightDescriptionId") final Long sizeWeightDescriptionId,
            @RequestBody @Valid final SizeAndWeightDescriptionsDTO sizeAndWeightDescriptionsDTO) {
        sizeAndWeightDescriptionsService.update(sizeWeightDescriptionId, sizeAndWeightDescriptionsDTO);
        return ResponseEntity.ok(sizeWeightDescriptionId);
    }

    @DeleteMapping("/{sizeWeightDescriptionId}")
    @ApiResponse(responseCode = "204")
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
    public ResponseEntity<Map<Long, SizeAndWeightType>> getPreviousValues() {
        return ResponseEntity.ok(sizeAndWeightDescriptionsRepository.findAll(Sort.by("sizeWeightDescriptionId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(SizeAndWeightDescriptions::getSizeWeightDescriptionId, SizeAndWeightDescriptions::getSize)));
    }

}
