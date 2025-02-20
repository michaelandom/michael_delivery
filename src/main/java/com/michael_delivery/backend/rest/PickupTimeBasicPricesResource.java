package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.enums.PickupTimeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.PickupTimeBasicPrices;
import com.michael_delivery.backend.model.PickupTimeBasicPricesDTO;
import com.michael_delivery.backend.repos.PickupTimeBasicPricesRepository;
import com.michael_delivery.backend.service.PickupTimeBasicPricesService;
import com.michael_delivery.backend.util.CustomCollectors;
import com.michael_delivery.backend.util.ReferencedException;
import com.michael_delivery.backend.util.ReferencedWarning;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/pickupTimeBasicPrices", produces = MediaType.APPLICATION_JSON_VALUE)
public class PickupTimeBasicPricesResource {

    private final PickupTimeBasicPricesService pickupTimeBasicPricesService;
    private final PickupTimeBasicPricesRepository pickupTimeBasicPricesRepository;

    public PickupTimeBasicPricesResource(
            final PickupTimeBasicPricesService pickupTimeBasicPricesService,
            final PickupTimeBasicPricesRepository pickupTimeBasicPricesRepository) {
        this.pickupTimeBasicPricesService = pickupTimeBasicPricesService;
        this.pickupTimeBasicPricesRepository = pickupTimeBasicPricesRepository;
    }

    @GetMapping
    public ResponseEntity<List<PickupTimeBasicPricesDTO>> getAllPickupTimeBasicPrices() {
        return ResponseEntity.ok(pickupTimeBasicPricesService.findAll());
    }

    @GetMapping("/{pickupTimeBasicPriceId}")
    public ResponseEntity<PickupTimeBasicPricesDTO> getPickupTimeBasicPrices(
            @PathVariable(name = "pickupTimeBasicPriceId") final Long pickupTimeBasicPriceId) {
        return ResponseEntity.ok(pickupTimeBasicPricesService.get(pickupTimeBasicPriceId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPickupTimeBasicPrices(
            @RequestBody @Valid final PickupTimeBasicPricesDTO pickupTimeBasicPricesDTO) {
        final Long createdPickupTimeBasicPriceId = pickupTimeBasicPricesService.create(pickupTimeBasicPricesDTO);
        return new ResponseEntity<>(createdPickupTimeBasicPriceId, HttpStatus.CREATED);
    }

    @PutMapping("/{pickupTimeBasicPriceId}")
    public ResponseEntity<Long> updatePickupTimeBasicPrices(
            @PathVariable(name = "pickupTimeBasicPriceId") final Long pickupTimeBasicPriceId,
            @RequestBody @Valid final PickupTimeBasicPricesDTO pickupTimeBasicPricesDTO) {
        pickupTimeBasicPricesService.update(pickupTimeBasicPriceId, pickupTimeBasicPricesDTO);
        return ResponseEntity.ok(pickupTimeBasicPriceId);
    }

    @DeleteMapping("/{pickupTimeBasicPriceId}")
    @ApiResponse(responseCode = "204")
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
    public ResponseEntity<Map<Long, PickupTimeType>> getPreviousValues() {
        return ResponseEntity.ok(pickupTimeBasicPricesRepository.findAll(Sort.by("pickupTimeBasicPriceId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(PickupTimeBasicPrices::getPickupTimeBasicPriceId, PickupTimeBasicPrices::getPickupTime)));
    }

}
