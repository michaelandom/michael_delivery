package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.model.RiderCommission;
import com.michael_delivery.backend.dto.PageableBodyDTO;
import com.michael_delivery.backend.dto.RiderCommissionDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.repository.RiderCommissionRepository;
import com.michael_delivery.backend.service.RiderCommissionService;
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
@RequestMapping(value = "/api/riderCommissions", produces = MediaType.APPLICATION_JSON_VALUE)
public class RiderCommissionController {

    private final RiderCommissionService riderCommissionService;
    private final RiderCommissionRepository riderCommissionRepository;

    public RiderCommissionController(final RiderCommissionService riderCommissionService,
            final RiderCommissionRepository riderCommissionRepository) {
        this.riderCommissionService = riderCommissionService;
        this.riderCommissionRepository = riderCommissionRepository;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGE_RIDER_COMMISSIONS','VIEW_RIDER_COMMISSION_MANY')")
    public ResponseEntity<List<RiderCommissionDTO>> getAllRiderCommission(
    ) {
        return ResponseEntity.ok(riderCommissionService.findAll());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('MANAGE_RIDER_COMMISSIONS','VIEW_RIDER_COMMISSION_MANY')")
    public ResponseEntity<Page<RiderCommissionDTO>> searchRiderCommission(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "riderCommissionId:asc") String[] sortBy,
            @RequestParam(required = false) Boolean isLatest,
            @RequestParam(required = false) String rate

    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<RiderCommission> spec = new GenericSpecification<>();
        Specification<RiderCommission> isLatestSpec = spec.equals("isLatest", isLatest);
        Specification<RiderCommission> finalSpec = Specification.where(isLatestSpec);
        return ResponseEntity.ok(riderCommissionService.search(finalSpec,pageable.getPageable()));
    }
    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_RIDER_COMMISSIONS','VIEW_RIDER_COMMISSION_MANY')")
    public ResponseEntity<Page<RiderCommissionDTO>> getAllRiderCommission(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "riderCommissionId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(riderCommissionService.findAll(pageable.getPageable()));
    }
    
    @GetMapping("/{riderCommissionId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_RIDER_COMMISSIONS','VIEW_RIDER_COMMISSION')")
    public ResponseEntity<RiderCommissionDTO> getRiderCommission(
            @PathVariable(name = "riderCommissionId") final Long riderCommissionId) {
        return ResponseEntity.ok(riderCommissionService.get(riderCommissionId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @PreAuthorize("hasAnyAuthority('MANAGE_RIDER_COMMISSIONS','UPDATE_RIDER_COMMISSION_ONE')")
    public ResponseEntity<Long> createRiderCommission(
            @RequestBody @Valid final RiderCommissionDTO riderCommissionDTO) {
        final Long createdRiderCommissionId = riderCommissionService.create(riderCommissionDTO);
        return new ResponseEntity<>(createdRiderCommissionId, HttpStatus.CREATED);
    }

    @PutMapping("/{riderCommissionId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_RIDER_COMMISSIONS','UPDATE_RIDER_COMMISSION_ONE')")
    public ResponseEntity<Long> updateRiderCommission(
            @PathVariable(name = "riderCommissionId") final Long riderCommissionId,
            @RequestBody @Valid final RiderCommissionDTO riderCommissionDTO) {
        riderCommissionService.update(riderCommissionId, riderCommissionDTO);
        return ResponseEntity.ok(riderCommissionId);
    }

    @DeleteMapping("/{riderCommissionId}")
    @ApiResponse(responseCode = "204")
    @PreAuthorize("hasAnyAuthority('MANAGE_RIDER_COMMISSIONS','DELETE_RIDER_COMMISSION_ONE')")
    public ResponseEntity<Void> deleteRiderCommission(
            @PathVariable(name = "riderCommissionId") final Long riderCommissionId) {
        final ReferencedWarning referencedWarning = riderCommissionService.getReferencedWarning(riderCommissionId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        riderCommissionService.delete(riderCommissionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/previousValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_RIDER_COMMISSIONS','VIEW_RIDER_COMMISSION_MANY')")
    public ResponseEntity<Map<Long, Long>> getPreviousValues() {
        return ResponseEntity.ok(riderCommissionRepository.findAll(Sort.by("riderCommissionId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(RiderCommission::getRiderCommissionId, RiderCommission::getRiderCommissionId)));
    }

}
