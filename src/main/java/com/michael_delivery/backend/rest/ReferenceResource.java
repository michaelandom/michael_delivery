package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.domain.Reference;
import com.michael_delivery.backend.model.PageableBodyDTO;
import com.michael_delivery.backend.model.ReferenceDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.ReferenceDTO;
import com.michael_delivery.backend.service.ReferenceService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
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

    @GetMapping("/all")
    public ResponseEntity<List<ReferenceDTO>> getAllReference(
    ) {
        return ResponseEntity.ok(referenceService.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ReferenceDTO>> searchReference(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "referenceId:asc") String[] sortBy,
            @RequestParam(required = false) String pspReference,
            @RequestParam(required = false) String paymentMethod

    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<Reference> spec = new GenericSpecification<>();
        Specification<Reference> pspReferenceSpec = spec.contains("pspReference", pspReference);
        Specification<Reference> paymentMethodSpec = spec.equals("paymentMethod", paymentMethod);
        Specification<Reference> finalSpec = Specification.where(pspReferenceSpec)
                .and(paymentMethodSpec);
        return ResponseEntity.ok(referenceService.search(finalSpec,pageable.getPageable()));
    }
    @GetMapping
    public ResponseEntity<Page<ReferenceDTO>> getAllReference(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "referenceId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(referenceService.findAll(pageable.getPageable()));
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
