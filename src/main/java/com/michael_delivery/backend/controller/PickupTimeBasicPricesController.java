package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.model.PickupTimeBasicPrices;
import com.michael_delivery.backend.enums.PickupTimeType;
import com.michael_delivery.backend.enums.VehicleType;
import com.michael_delivery.backend.dto.PageableBodyDTO;
import com.michael_delivery.backend.dto.PickupTimeBasicPricesDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.repository.PickupTimeBasicPricesRepository;
import com.michael_delivery.backend.service.PickupTimeBasicPricesService;
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
@RequestMapping(value = "/api/pickupTimeBasicPrices", produces = MediaType.APPLICATION_JSON_VALUE)
public class PickupTimeBasicPricesController {

    private final PickupTimeBasicPricesService pickupTimeBasicPricesService;
    private final PickupTimeBasicPricesRepository pickupTimeBasicPricesRepository;

    public PickupTimeBasicPricesController(
            final PickupTimeBasicPricesService pickupTimeBasicPricesService,
            final PickupTimeBasicPricesRepository pickupTimeBasicPricesRepository) {
        this.pickupTimeBasicPricesService = pickupTimeBasicPricesService;
        this.pickupTimeBasicPricesRepository = pickupTimeBasicPricesRepository;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGE_PICKUP_TIME_BASIC_PRICES','VIEW_PICKUP_TIME_BASIC_PRICE_MANY')")
    public ResponseEntity<List<PickupTimeBasicPricesDTO>> getAllPickupTimeBasicPrices(
    ) {
        return ResponseEntity.ok(pickupTimeBasicPricesService.findAll());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('MANAGE_PICKUP_TIME_BASIC_PRICES','VIEW_PICKUP_TIME_BASIC_PRICE_MANY')")
    public ResponseEntity<Page<PickupTimeBasicPricesDTO>> searchPickupTimeBasicPrices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "pickupTimeBasicPriceId:asc") String[] sortBy,
            @RequestParam(required = false) PickupTimeType pickupTime,
            @RequestParam(required = false) VehicleType vehicleType,
            @RequestParam(required = false) Boolean isLatest


    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<PickupTimeBasicPrices> spec = new GenericSpecification<>();
        Specification<PickupTimeBasicPrices> pickupTimeSpec = spec.equals("pickupTime", pickupTime);
        Specification<PickupTimeBasicPrices> vehicleTypeSpec = spec.equals("vehicleType", vehicleType);
        Specification<PickupTimeBasicPrices> isLatestSpec = spec.equals("isLatest", isLatest);
        Specification<PickupTimeBasicPrices> finalSpec = Specification.where(pickupTimeSpec)
                .and(vehicleTypeSpec)
                .and(isLatestSpec);
        return ResponseEntity.ok(pickupTimeBasicPricesService.search(finalSpec,pageable.getPageable()));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_PICKUP_TIME_BASIC_PRICES','VIEW_PICKUP_TIME_BASIC_PRICE_MANY')")
    public ResponseEntity<Page<PickupTimeBasicPricesDTO>> getAllPickupTimeBasicPrices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "pickupTimeBasicPriceId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(pickupTimeBasicPricesService.findAll(pageable.getPageable()));
    }
    @GetMapping("/{pickupTimeBasicPriceId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_PICKUP_TIME_BASIC_PRICES','VIEW_PICKUP_TIME_BASIC_PRICE')")
    public ResponseEntity<PickupTimeBasicPricesDTO> getPickupTimeBasicPrices(
            @PathVariable(name = "pickupTimeBasicPriceId") final Long pickupTimeBasicPriceId) {
        return ResponseEntity.ok(pickupTimeBasicPricesService.get(pickupTimeBasicPriceId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @PreAuthorize("hasAnyAuthority('MANAGE_PICKUP_TIME_BASIC_PRICES','UPDATE_PICKUP_TIME_BASIC_PRICE_ONE')")
    public ResponseEntity<Long> createPickupTimeBasicPrices(
            @RequestBody @Valid final PickupTimeBasicPricesDTO pickupTimeBasicPricesDTO) {
        final Long createdPickupTimeBasicPriceId = pickupTimeBasicPricesService.create(pickupTimeBasicPricesDTO);
        return new ResponseEntity<>(createdPickupTimeBasicPriceId, HttpStatus.CREATED);
    }

    @PutMapping("/{pickupTimeBasicPriceId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_PICKUP_TIME_BASIC_PRICES','UPDATE_PICKUP_TIME_BASIC_PRICE_ONE')")
    public ResponseEntity<Long> updatePickupTimeBasicPrices(
            @PathVariable(name = "pickupTimeBasicPriceId") final Long pickupTimeBasicPriceId,
            @RequestBody @Valid final PickupTimeBasicPricesDTO pickupTimeBasicPricesDTO) {
        pickupTimeBasicPricesService.update(pickupTimeBasicPriceId, pickupTimeBasicPricesDTO);
        return ResponseEntity.ok(pickupTimeBasicPriceId);
    }

    @DeleteMapping("/{pickupTimeBasicPriceId}")
    @ApiResponse(responseCode = "204")
    @PreAuthorize("hasAnyAuthority('MANAGE_PICKUP_TIME_BASIC_PRICES','DELETE_PICKUP_TIME_BASIC_PRICE_ONE')")
    public ResponseEntity<Void> deletePickupTimeBasicPrices(
            @PathVariable(name = "pickupTimeBasicPriceId") final Long pickupTimeBasicPriceId) {
        final ReferencedWarning referencedWarning = pickupTimeBasicPricesService.getReferencedWarning(pickupTimeBasicPriceId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        pickupTimeBasicPricesService.delete(pickupTimeBasicPriceId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/previousValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_PICKUP_TIME_BASIC_PRICES','VIEW_PICKUP_TIME_BASIC_PRICE_MANY')")
    public ResponseEntity<Map<Long, PickupTimeType>> getPreviousValues() {
        return ResponseEntity.ok(pickupTimeBasicPricesRepository.findAll(Sort.by("pickupTimeBasicPriceId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(PickupTimeBasicPrices::getPickupTimeBasicPriceId, PickupTimeBasicPrices::getPickupTime)));
    }

}
