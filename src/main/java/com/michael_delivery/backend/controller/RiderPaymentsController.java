package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.model.RiderPayments;
import com.michael_delivery.backend.dto.PageableBodyDTO;
import com.michael_delivery.backend.dto.RiderPaymentsDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.Riders;
import com.michael_delivery.backend.repository.RidersRepository;
import com.michael_delivery.backend.service.RiderPaymentsService;
import com.michael_delivery.backend.util.CustomCollectors;
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
@RequestMapping(value = "/api/riderPayments", produces = MediaType.APPLICATION_JSON_VALUE)
public class RiderPaymentsController {

    private final RiderPaymentsService riderPaymentsService;
    private final RidersRepository ridersRepository;

    public RiderPaymentsController(final RiderPaymentsService riderPaymentsService,
            final RidersRepository ridersRepository) {
        this.riderPaymentsService = riderPaymentsService;
        this.ridersRepository = ridersRepository;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGE_RIDER_PAYMENTS','VIEW_RIDER_PAYMENT_MANY')")
    public ResponseEntity<List<RiderPaymentsDTO>> getAllRiderPayments(
    ) {
        return ResponseEntity.ok(riderPaymentsService.findAll());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('MANAGE_RIDER_PAYMENTS','VIEW_RIDER_PAYMENT_MANY')")
    public ResponseEntity<Page<RiderPaymentsDTO>> searchRiderPayments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "riderPaymentId:asc") String[] sortBy,
            @RequestParam(required = false) Boolean isPaid

    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<RiderPayments> spec = new GenericSpecification<>();
        Specification<RiderPayments> isPaidSpec = spec.equals("isPaid", isPaid);
        Specification<RiderPayments> finalSpec = Specification.where(isPaidSpec);
        return ResponseEntity.ok(riderPaymentsService.search(finalSpec,pageable.getPageable()));
    }
    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_RIDER_PAYMENTS','VIEW_RIDER_PAYMENT_MANY')")
    public ResponseEntity<Page<RiderPaymentsDTO>> getAllRiderPayments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "riderPaymentId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(riderPaymentsService.findAll(pageable.getPageable()));
    }

    @GetMapping("/{riderPaymentId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_RIDER_PAYMENTS','VIEW_RIDER_COMMISSION')")
    public ResponseEntity<RiderPaymentsDTO> getRiderPayments(
            @PathVariable(name = "riderPaymentId") final Long riderPaymentId) {
        return ResponseEntity.ok(riderPaymentsService.get(riderPaymentId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @PreAuthorize("hasAnyAuthority('MANAGE_RIDER_PAYMENTS','UPDATE_RIDER_PAYMENT_ONE')")
    public ResponseEntity<Long> createRiderPayments(
            @RequestBody @Valid final RiderPaymentsDTO riderPaymentsDTO) {
        final Long createdRiderPaymentId = riderPaymentsService.create(riderPaymentsDTO);
        return new ResponseEntity<>(createdRiderPaymentId, HttpStatus.CREATED);
    }

    @PutMapping("/{riderPaymentId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_RIDER_PAYMENTS','UPDATE_RIDER_PAYMENT_ONE')")
    public ResponseEntity<Long> updateRiderPayments(
            @PathVariable(name = "riderPaymentId") final Long riderPaymentId,
            @RequestBody @Valid final RiderPaymentsDTO riderPaymentsDTO) {
        riderPaymentsService.update(riderPaymentId, riderPaymentsDTO);
        return ResponseEntity.ok(riderPaymentId);
    }

    @DeleteMapping("/{riderPaymentId}")
    @ApiResponse(responseCode = "204")
    @PreAuthorize("hasAnyAuthority('MANAGE_RIDER_PAYMENTS','DELETE_RIDER_PAYMENT_ONE')")
    public ResponseEntity<Void> deleteRiderPayments(
            @PathVariable(name = "riderPaymentId") final Long riderPaymentId) {
        riderPaymentsService.delete(riderPaymentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/riderValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_RIDER_PAYMENTS','VIEW_RIDER_PAYMENT_MANY')")
    public ResponseEntity<Map<Long, String>> getRiderValues() {
        return ResponseEntity.ok(ridersRepository.findAll(Sort.by("riderId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Riders::getRiderId, Riders::getEmergencyContactFirstName)));
    }

}
