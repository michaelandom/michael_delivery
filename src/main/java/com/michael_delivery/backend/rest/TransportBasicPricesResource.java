package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.domain.TransportBasicPrices;
import com.michael_delivery.backend.enums.SuspensionReasonType;
import com.michael_delivery.backend.enums.VehicleType;
import com.michael_delivery.backend.model.PageableBodyDTO;
import com.michael_delivery.backend.model.TransportBasicPricesDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.TransportBasicPrices;
import com.michael_delivery.backend.model.TransportBasicPricesDTO;
import com.michael_delivery.backend.repos.TransportBasicPricesRepository;
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
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/transportBasicPrices", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransportBasicPricesResource {

    private final TransportBasicPricesService transportBasicPricesService;
    private final TransportBasicPricesRepository transportBasicPricesRepository;

    public TransportBasicPricesResource(
            final TransportBasicPricesService transportBasicPricesService,
            final TransportBasicPricesRepository transportBasicPricesRepository) {
        this.transportBasicPricesService = transportBasicPricesService;
        this.transportBasicPricesRepository = transportBasicPricesRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TransportBasicPricesDTO>> getAllTransportBasicPrices(
    ) {
        return ResponseEntity.ok(transportBasicPricesService.findAll());
    }

    @GetMapping("/search")
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
    public ResponseEntity<Page<TransportBasicPricesDTO>> getAllTransportBasicPrices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "transportBasicPriceId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(transportBasicPricesService.findAll(pageable.getPageable()));
    }


    @GetMapping("/{transportBasicPriceId}")
    public ResponseEntity<TransportBasicPricesDTO> getTransportBasicPrices(
            @PathVariable(name = "transportBasicPriceId") final Long transportBasicPriceId) {
        return ResponseEntity.ok(transportBasicPricesService.get(transportBasicPriceId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createTransportBasicPrices(
            @RequestBody @Valid final TransportBasicPricesDTO transportBasicPricesDTO) {
        final Long createdTransportBasicPriceId = transportBasicPricesService.create(transportBasicPricesDTO);
        return new ResponseEntity<>(createdTransportBasicPriceId, HttpStatus.CREATED);
    }

    @PutMapping("/{transportBasicPriceId}")
    public ResponseEntity<Long> updateTransportBasicPrices(
            @PathVariable(name = "transportBasicPriceId") final Long transportBasicPriceId,
            @RequestBody @Valid final TransportBasicPricesDTO transportBasicPricesDTO) {
        transportBasicPricesService.update(transportBasicPriceId, transportBasicPricesDTO);
        return ResponseEntity.ok(transportBasicPriceId);
    }

    @DeleteMapping("/{transportBasicPriceId}")
    @ApiResponse(responseCode = "204")
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
    public ResponseEntity<Map<Long, VehicleType>> getPreviousValues() {
        return ResponseEntity.ok(transportBasicPricesRepository.findAll(Sort.by("transportBasicPriceId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(TransportBasicPrices::getTransportBasicPriceId, TransportBasicPrices::getVehicleType)));
    }

}
