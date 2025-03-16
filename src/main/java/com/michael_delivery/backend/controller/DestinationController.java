package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.model.Destination;
import com.michael_delivery.backend.dto.DestinationDTO;
import com.michael_delivery.backend.dto.PageableBodyDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.Orders;
import com.michael_delivery.backend.model.Riders;
import com.michael_delivery.backend.repository.OrdersRepository;
import com.michael_delivery.backend.repository.RidersRepository;
import com.michael_delivery.backend.service.DestinationService;
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
@RequestMapping(value = "/api/destinations", produces = MediaType.APPLICATION_JSON_VALUE)
public class DestinationController {

    private final DestinationService destinationService;
    private final OrdersRepository ordersRepository;
    private final RidersRepository ridersRepository;

    public DestinationController(final DestinationService destinationService,
            final OrdersRepository ordersRepository, final RidersRepository ridersRepository) {
        this.destinationService = destinationService;
        this.ordersRepository = ordersRepository;
        this.ridersRepository = ridersRepository;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGE_DESTINATIONS','VIEW_DESTINATION_MANY')")
    public ResponseEntity<List<DestinationDTO>> getAllDestination(
    ) {
        return ResponseEntity.ok(destinationService.findAll());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('MANAGE_DESTINATIONS','VIEW_DESTINATION_MANY')")
    public ResponseEntity<Page<DestinationDTO>> searchDestination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "destinationId:asc") String[] sortBy,
            @RequestParam(required = false) Double destinationLatitude,
            @RequestParam(required = false) Double destinationLongitude,
            @RequestParam(required = false) String destinationAddressText,
            @RequestParam(required = false) Integer sequence,
            @RequestParam(required = false) String recipientPhoneNumber,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) String recipientName,
            @RequestParam(required = false) String safeStorage,
            @RequestParam(required = false) String specificRecipient
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<Destination> spec = new GenericSpecification<>();
        Specification<Destination> destinationLatitudeSpec = spec.equals("destinationLatitude", destinationLatitude);
        Specification<Destination> destinationLongitudeSpec = spec.equals("destinationLongitude", destinationLongitude);
        Specification<Destination> destinationAddressTextSpec = spec.contains("destinationAddressText", destinationAddressText);
        Specification<Destination> recipientPhoneNumberSpec = spec.contains("recipientPhoneNumber", recipientPhoneNumber);
        Specification<Destination> recipientNameSpec = spec.contains("recipientName", recipientName);
        Specification<Destination> sequenceSpec = spec.equals("sequence", sequence);
        Specification<Destination> priceSpec = spec.equals("price", price);
        Specification<Destination> safeStorageSpec = spec.equals("safeStorage", safeStorage);
        Specification<Destination> specificRecipientSpec = spec.equals("specificRecipient", specificRecipient);
        Specification<Destination> finalSpec = Specification.where(destinationLatitudeSpec).and(destinationLongitudeSpec)
                .and(destinationAddressTextSpec)
                .and(recipientPhoneNumberSpec)
                .and(recipientNameSpec)
                .and(sequenceSpec)
                .and(priceSpec)
                .and(safeStorageSpec)
                .and(specificRecipientSpec);
        return ResponseEntity.ok(destinationService.search(finalSpec,pageable.getPageable()));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_DESTINATIONS','VIEW_DESTINATION_MANY')")
    public ResponseEntity<Page<DestinationDTO>> getAllDestination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "destinationId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(destinationService.findAll(pageable.getPageable()));
    }

    @GetMapping("/{destinationId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_DESTINATIONS','VIEW_DESTINATION')")
    public ResponseEntity<DestinationDTO> getDestination(
            @PathVariable(name = "destinationId") final Long destinationId) {
        return ResponseEntity.ok(destinationService.get(destinationId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @PreAuthorize("hasAnyAuthority('MANAGE_DESTINATIONS','UPDATE_DESTINATION_ONE')")
    public ResponseEntity<Long> createDestination(
            @RequestBody @Valid final DestinationDTO destinationDTO) {
        final Long createdDestinationId = destinationService.create(destinationDTO);
        return new ResponseEntity<>(createdDestinationId, HttpStatus.CREATED);
    }

    @PutMapping("/{destinationId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_DESTINATIONS','UPDATE_DESTINATION_ONE')")
    public ResponseEntity<Long> updateDestination(
            @PathVariable(name = "destinationId") final Long destinationId,
            @RequestBody @Valid final DestinationDTO destinationDTO) {
        destinationService.update(destinationId, destinationDTO);
        return ResponseEntity.ok(destinationId);
    }

    @DeleteMapping("/{destinationId}")
    @ApiResponse(responseCode = "204")
    @PreAuthorize("hasAnyAuthority('MANAGE_DESTINATIONS','DELETE_DESTINATION_ONE')")
    public ResponseEntity<Void> deleteDestination(
            @PathVariable(name = "destinationId") final Long destinationId) {
        final ReferencedWarning referencedWarning = destinationService.getReferencedWarning(destinationId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        destinationService.delete(destinationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/orderValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_DESTINATIONS','VIEW_DESTINATION_MANY')")
    public ResponseEntity<Map<Long, String>> getOrderValues() {
        return ResponseEntity.ok(ordersRepository.findAll(Sort.by("orderId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Orders::getOrderId, Orders::getOrderNumber)));
    }

    @GetMapping("/deliveryByValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_DESTINATIONS','VIEW_DESTINATION_MANY')")
    public ResponseEntity<Map<Long, String>> getDeliveryByValues() {
        return ResponseEntity.ok(ridersRepository.findAll(Sort.by("riderId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Riders::getRiderId, Riders::getEmergencyContactFirstName)));
    }

}
