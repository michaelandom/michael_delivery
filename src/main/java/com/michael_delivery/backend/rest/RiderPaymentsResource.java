package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.Riders;
import com.michael_delivery.backend.model.RiderPaymentsDTO;
import com.michael_delivery.backend.repos.RidersRepository;
import com.michael_delivery.backend.service.RiderPaymentsService;
import com.michael_delivery.backend.util.CustomCollectors;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/riderPayments", produces = MediaType.APPLICATION_JSON_VALUE)
public class RiderPaymentsResource {

    private final RiderPaymentsService riderPaymentsService;
    private final RidersRepository ridersRepository;

    public RiderPaymentsResource(final RiderPaymentsService riderPaymentsService,
            final RidersRepository ridersRepository) {
        this.riderPaymentsService = riderPaymentsService;
        this.ridersRepository = ridersRepository;
    }

    @GetMapping
    public ResponseEntity<List<RiderPaymentsDTO>> getAllRiderPayments() {
        return ResponseEntity.ok(riderPaymentsService.findAll());
    }

    @GetMapping("/{riderPaymentId}")
    public ResponseEntity<RiderPaymentsDTO> getRiderPayments(
            @PathVariable(name = "riderPaymentId") final Long riderPaymentId) {
        return ResponseEntity.ok(riderPaymentsService.get(riderPaymentId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createRiderPayments(
            @RequestBody @Valid final RiderPaymentsDTO riderPaymentsDTO) {
        final Long createdRiderPaymentId = riderPaymentsService.create(riderPaymentsDTO);
        return new ResponseEntity<>(createdRiderPaymentId, HttpStatus.CREATED);
    }

    @PutMapping("/{riderPaymentId}")
    public ResponseEntity<Long> updateRiderPayments(
            @PathVariable(name = "riderPaymentId") final Long riderPaymentId,
            @RequestBody @Valid final RiderPaymentsDTO riderPaymentsDTO) {
        riderPaymentsService.update(riderPaymentId, riderPaymentsDTO);
        return ResponseEntity.ok(riderPaymentId);
    }

    @DeleteMapping("/{riderPaymentId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRiderPayments(
            @PathVariable(name = "riderPaymentId") final Long riderPaymentId) {
        riderPaymentsService.delete(riderPaymentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/riderValues")
    public ResponseEntity<Map<Long, String>> getRiderValues() {
        return ResponseEntity.ok(ridersRepository.findAll(Sort.by("riderId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Riders::getRiderId, Riders::getEmergencyContactFirstName)));
    }

}
