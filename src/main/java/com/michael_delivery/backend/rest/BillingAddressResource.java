package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.BillingAddressDTO;
import com.michael_delivery.backend.service.BillingAddressService;
import com.michael_delivery.backend.util.ReferencedException;
import com.michael_delivery.backend.util.ReferencedWarning;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/billingAddresses", produces = MediaType.APPLICATION_JSON_VALUE)
public class BillingAddressResource {

    private final BillingAddressService billingAddressService;

    public BillingAddressResource(final BillingAddressService billingAddressService) {
        this.billingAddressService = billingAddressService;
    }

    @GetMapping
    public ResponseEntity<List<BillingAddressDTO>> getAllBillingAddresses() {
        return ResponseEntity.ok(billingAddressService.findAll());
    }

    @GetMapping("/{billingAddressId}")
    public ResponseEntity<BillingAddressDTO> getBillingAddress(
            @PathVariable(name = "billingAddressId") final Long billingAddressId) {
        return ResponseEntity.ok(billingAddressService.get(billingAddressId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createBillingAddress(
            @RequestBody @Valid final BillingAddressDTO billingAddressDTO) {
        final Long createdBillingAddressId = billingAddressService.create(billingAddressDTO);
        return new ResponseEntity<>(createdBillingAddressId, HttpStatus.CREATED);
    }

    @PutMapping("/{billingAddressId}")
    public ResponseEntity<Long> updateBillingAddress(
            @PathVariable(name = "billingAddressId") final Long billingAddressId,
            @RequestBody @Valid final BillingAddressDTO billingAddressDTO) {
        billingAddressService.update(billingAddressId, billingAddressDTO);
        return ResponseEntity.ok(billingAddressId);
    }

    @DeleteMapping("/{billingAddressId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteBillingAddress(
            @PathVariable(name = "billingAddressId") final Long billingAddressId) {
        final ReferencedWarning referencedWarning = billingAddressService.getReferencedWarning(billingAddressId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        billingAddressService.delete(billingAddressId);
        return ResponseEntity.noContent().build();
    }

}
