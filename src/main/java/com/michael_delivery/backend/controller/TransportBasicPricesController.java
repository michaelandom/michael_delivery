package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.model.TransportBasicPrices;
import com.michael_delivery.backend.enums.VehicleType;
import com.michael_delivery.backend.dto.PageableBodyDTO;
import com.michael_delivery.backend.dto.TransportBasicPricesDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.repository.TransportBasicPricesRepository;
import com.michael_delivery.backend.service.TransportBasicPricesService;
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
@RequestMapping(value = "/api/transportBasicPrices", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransportBasicPricesController {

    private final TransportBasicPricesService transportBasicPricesService;
    private final TransportBasicPricesRepository transportBasicPricesRepository;

    public TransportBasicPricesController(
            final TransportBasicPricesService transportBasicPricesService,
            final TransportBasicPricesRepository transportBasicPricesRepository) {
        this.transportBasicPricesService = transportBasicPricesService;
        this.transportBasicPricesRepository = transportBasicPricesRepository;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGE_TRANSPORT_BASIC_PRICES','VIEW_TRANSPORT_BASIC_PRICE_MANY')")
    public ResponseEntity<List<TransportBasicPricesDTO>> getAllTransportBasicPrices(
    ) {
        return ResponseEntity.ok(transportBasicPricesService.findAll());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('MANAGE_TRANSPORT_BASIC_PRICES','VIEW_TRANSPORT_BASIC_PRICE_MANY')")
    public ResponseEntity<Page<TransportBasicPricesDTO>> searchTransportBasicPrices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "transportBasicPriceId:asc") String[] sortBy,
            @RequestParam(required = false) VehicleType vehicleType,
            @RequestParam(required = false) boolean isLatest
            ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<TransportBasicPrices> spec = new GenericSpecification<>();
        Specification<TransportBasicPrices> vehicleTypeSpec = spec.equals("vehicleType", vehicleType);
        Specification<TransportBasicPrices> isLatestSpec = spec.equals("isLatest", isLatest);
        Specification<TransportBasicPrices> finalSpec = Specification.where(vehicleTypeSpec).and(isLatestSpec);
        return ResponseEntity.ok(transportBasicPricesService.search(finalSpec,pageable.getPageable()));
    }
    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_TRANSPORT_BASIC_PRICES','VIEW_TRANSPORT_BASIC_PRICE_MANY')")
    public ResponseEntity<Page<TransportBasicPricesDTO>> getAllTransportBasicPrices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "transportBasicPriceId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(transportBasicPricesService.findAll(pageable.getPageable()));
    }


    @GetMapping("/{transportBasicPriceId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_TRANSPORT_BASIC_PRICES','VIEW_TRANSPORT_BASIC_PRICE')")
    public ResponseEntity<TransportBasicPricesDTO> getTransportBasicPrices(
            @PathVariable(name = "transportBasicPriceId") final Long transportBasicPriceId) {
        return ResponseEntity.ok(transportBasicPricesService.get(transportBasicPriceId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @PreAuthorize("hasAnyAuthority('MANAGE_TRANSPORT_BASIC_PRICES','UPDATE_TRANSPORT_BASIC_PRICE_ONE')")
    public ResponseEntity<Long> createTransportBasicPrices(
            @RequestBody @Valid final TransportBasicPricesDTO transportBasicPricesDTO) {
        final Long createdTransportBasicPriceId = transportBasicPricesService.create(transportBasicPricesDTO);
        return new ResponseEntity<>(createdTransportBasicPriceId, HttpStatus.CREATED);
    }

    @PutMapping("/{transportBasicPriceId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_TRANSPORT_BASIC_PRICES','UPDATE_TRANSPORT_BASIC_PRICE_ONE')")
    public ResponseEntity<Long> updateTransportBasicPrices(
            @PathVariable(name = "transportBasicPriceId") final Long transportBasicPriceId,
            @RequestBody @Valid final TransportBasicPricesDTO transportBasicPricesDTO) {
        transportBasicPricesService.update(transportBasicPriceId, transportBasicPricesDTO);
        return ResponseEntity.ok(transportBasicPriceId);
    }

    @DeleteMapping("/{transportBasicPriceId}")
    @ApiResponse(responseCode = "204")
    @PreAuthorize("hasAnyAuthority('MANAGE_TRANSPORT_BASIC_PRICES','DELETE_TRANSPORT_BASIC_PRICE_ONE')")
    public ResponseEntity<Void> deleteTransportBasicPrices(
            @PathVariable(name = "transportBasicPriceId") final Long transportBasicPriceId) {
        final ReferencedWarning referencedWarning = transportBasicPricesService.getReferencedWarning(transportBasicPriceId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        transportBasicPricesService.delete(transportBasicPriceId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/previousValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_TRANSPORT_BASIC_PRICES','VIEW_TRANSPORT_BASIC_PRICE_MANY')")
    public ResponseEntity<Map<Long, VehicleType>> getPreviousValues() {
        return ResponseEntity.ok(transportBasicPricesRepository.findAll(Sort.by("transportBasicPriceId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(TransportBasicPrices::getTransportBasicPriceId, TransportBasicPrices::getVehicleType)));
    }

}
