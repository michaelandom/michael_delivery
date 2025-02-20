package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.ReferenceDTO;
import com.michael_delivery.backend.service.ReferenceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/references", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReferenceResource {

    private final ReferenceService referenceService;

    public ReferenceResource(final ReferenceService referenceService) {
        this.referenceService = referenceService;
    }

    @GetMapping
    public ResponseEntity<List<ReferenceDTO>> getAllReferences() {
        return ResponseEntity.ok(referenceService.findAll());
    }

    @GetMapping("/{referenceId}")
    public ResponseEntity<ReferenceDTO> getReference(
            @PathVariable(name = "referenceId") final Long referenceId) {
        return ResponseEntity.ok(referenceService.get(referenceId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createReference(
            @RequestBody @Valid final ReferenceDTO referenceDTO) {
        final Long createdReferenceId = referenceService.create(referenceDTO);
        return new ResponseEntity<>(createdReferenceId, HttpStatus.CREATED);
    }

    @PutMapping("/{referenceId}")
    public ResponseEntity<Long> updateReference(
            @PathVariable(name = "referenceId") final Long referenceId,
            @RequestBody @Valid final ReferenceDTO referenceDTO) {
        referenceService.update(referenceId, referenceDTO);
        return ResponseEntity.ok(referenceId);
    }

    @DeleteMapping("/{referenceId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteReference(
            @PathVariable(name = "referenceId") final Long referenceId) {
        referenceService.delete(referenceId);
        return ResponseEntity.noContent().build();
    }

}
