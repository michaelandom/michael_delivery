package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.model.BussinessAccount;
import com.michael_delivery.backend.dto.PageableBodyDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.BillingAddress;
import com.michael_delivery.backend.dto.BussinessAccountDTO;
import com.michael_delivery.backend.repository.BillingAddressRepository;
import com.michael_delivery.backend.service.BussinessAccountService;
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
@RequestMapping(value = "/api/bussinessAccounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class BussinessAccountController {

    private final BussinessAccountService bussinessAccountService;
    private final BillingAddressRepository billingAddressRepository;

    public BussinessAccountController(final BussinessAccountService bussinessAccountService,
            final BillingAddressRepository billingAddressRepository) {
        this.bussinessAccountService = bussinessAccountService;
        this.billingAddressRepository = billingAddressRepository;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGE_BUSINESS_ACCOUNTS','VIEW_BUSINESS_ACCOUNT_MANY')")
    public ResponseEntity<List<BussinessAccountDTO>> getAllBussinessAccount(
    ) {
        return ResponseEntity.ok(bussinessAccountService.findAll());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('MANAGE_BUSINESS_ACCOUNTS','VIEW_BUSINESS_ACCOUNT_MANY')")
    public ResponseEntity<Page<BussinessAccountDTO>> searchBussinessAccount(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "bussinessAccountId:asc") String[] sortBy,
            @RequestParam(required = false) String companyAbn,
            @RequestParam(required = false) String companyName
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<BussinessAccount> spec = new GenericSpecification<>();
        Specification<BussinessAccount> companyAbnSpec = spec.contains("companyAbn", companyAbn);
        Specification<BussinessAccount> companyNameSpec = spec.contains("companyName", companyName);
        Specification<BussinessAccount> finalSpec = Specification.where(companyAbnSpec).and(companyNameSpec);
        return ResponseEntity.ok(bussinessAccountService.search(finalSpec,pageable.getPageable()));
    }
    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_BUSINESS_ACCOUNTS','VIEW_BUSINESS_ACCOUNT_MANY')")
    public ResponseEntity<Page<BussinessAccountDTO>> getAllBussinessAccount(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "bussinessAccountId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(bussinessAccountService.findAll(pageable.getPageable()));
    }

    @GetMapping("/{bussinessAccountId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_BUSINESS_ACCOUNTS','VIEW_BUSINESS_ACCOUNT')")
    public ResponseEntity<BussinessAccountDTO> getBussinessAccount(
            @PathVariable(name = "bussinessAccountId") final Long bussinessAccountId) {
        return ResponseEntity.ok(bussinessAccountService.get(bussinessAccountId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @PreAuthorize("hasAnyAuthority('MANAGE_BUSINESS_ACCOUNTS','UPDATE_BUSINESS_ACCOUNT_ONE')")
    public ResponseEntity<Long> createBussinessAccount(
            @RequestBody @Valid final BussinessAccountDTO bussinessAccountDTO) {
        final Long createdBussinessAccountId = bussinessAccountService.create(bussinessAccountDTO);
        return new ResponseEntity<>(createdBussinessAccountId, HttpStatus.CREATED);
    }

    @PutMapping("/{bussinessAccountId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_BUSINESS_ACCOUNTS','UPDATE_BUSINESS_ACCOUNT_ONE')")
    public ResponseEntity<Long> updateBussinessAccount(
            @PathVariable(name = "bussinessAccountId") final Long bussinessAccountId,
            @RequestBody @Valid final BussinessAccountDTO bussinessAccountDTO) {
        bussinessAccountService.update(bussinessAccountId, bussinessAccountDTO);
        return ResponseEntity.ok(bussinessAccountId);
    }

    @DeleteMapping("/{bussinessAccountId}")
    @ApiResponse(responseCode = "204")
    @PreAuthorize("hasAnyAuthority('MANAGE_BUSINESS_ACCOUNTS','DELETE_BUSINESS_ACCOUNT_ONE')")
    public ResponseEntity<Void> deleteBussinessAccount(
            @PathVariable(name = "bussinessAccountId") final Long bussinessAccountId) {
        final ReferencedWarning referencedWarning = bussinessAccountService.getReferencedWarning(bussinessAccountId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        bussinessAccountService.delete(bussinessAccountId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/billingAddressValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_BUSINESS_ACCOUNTS','VIEW_BUSINESS_ACCOUNT_MANY')")
    public ResponseEntity<Map<Long, Long>> getBillingAddressValues() {
        return ResponseEntity.ok(billingAddressRepository.findAll(Sort.by("billingAddressId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(BillingAddress::getBillingAddressId, BillingAddress::getBillingAddressId)));
    }

}
