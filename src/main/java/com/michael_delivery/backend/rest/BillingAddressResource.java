package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.domain.BillingAddress;
import com.michael_delivery.backend.model.PageableBodyDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.BillingAddressDTO;
import com.michael_delivery.backend.service.BillingAddressService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
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

    @GetMapping("/all")
    public ResponseEntity<List<BillingAddressDTO>> getAllBillingAddress(
    ) {
        return ResponseEntity.ok(billingAddressService.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<BillingAddressDTO>> searchBillingAddress(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "billingAddressId:asc") String[] sortBy,
            @RequestParam(required = false) String billingEmail,
            @RequestParam(required = false) String billingStreetAddress,
            @RequestParam(required = false) String billingStreetAddress2,
            @RequestParam(required = false) String billingPostcode,
            @RequestParam(required = false) String billingSuburb
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<BillingAddress> spec = new GenericSpecification<>();
        Specification<BillingAddress> billingEmailSpec = spec.contains("billingEmail", billingEmail);
        Specification<BillingAddress> billingStreetAddressSpec = spec.contains("billingStreetAddress", billingStreetAddress);
        Specification<BillingAddress> billingStreetAddress2Spec = spec.contains("billingStreetAddress2", billingStreetAddress2);
        Specification<BillingAddress> billingPostcodeSpec = spec.contains("billingPostcode", billingPostcode);
        Specification<BillingAddress> billingSuburbSpec = spec.contains("billingSuburb", billingSuburb);
        Specification<BillingAddress> finalSpec = Specification.where(billingEmailSpec).and(billingStreetAddressSpec).and(billingStreetAddress2Spec).and(billingPostcodeSpec).and(billingSuburbSpec);
        return ResponseEntity.ok(billingAddressService.search(finalSpec,pageable.getPageable()));
    }
    @GetMapping
    public ResponseEntity<Page<BillingAddressDTO>> getAllBillingAddress(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "billingAddressId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);

        return ResponseEntity.ok(billingAddressService.findAll(pageable.getPageable()));
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
        billingAddressService.delete(billingAddressId);
        return ResponseEntity.noContent().build();
    }

}
