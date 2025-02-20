package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.SizeAndWeightDescriptions;
import com.michael_delivery.backend.model.SizeAndWeightDescriptionsDTO;
import com.michael_delivery.backend.repos.SizeAndWeightDescriptionsRepository;
import com.michael_delivery.backend.service.SizeAndWeightDescriptionsService;
import com.michael_delivery.backend.util.CustomCollectors;
import com.michael_delivery.backend.util.ReferencedException;
import com.michael_delivery.backend.util.ReferencedWarning;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
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

    @GetMapping
    public ResponseEntity<List<SizeAndWeightDescriptionsDTO>> getAllSizeAndWeightDescriptions() {
        return ResponseEntity.ok(sizeAndWeightDescriptionsService.findAll());
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
    public ResponseEntity<Map<Long, String>> getPreviousValues() {
        return ResponseEntity.ok(sizeAndWeightDescriptionsRepository.findAll(Sort.by("sizeWeightDescriptionId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(SizeAndWeightDescriptions::getSizeWeightDescriptionId, SizeAndWeightDescriptions::getSize)));
    }

}
