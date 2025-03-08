package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.domain.Coupons;
import com.michael_delivery.backend.domain.DeliveryDetail;
import com.michael_delivery.backend.enums.PickupTimeType;
import com.michael_delivery.backend.model.DeliveryDetailDTO;
import com.michael_delivery.backend.model.PageableBodyDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.Orders;
import com.michael_delivery.backend.model.DeliveryDetailDTO;
import com.michael_delivery.backend.repos.OrdersRepository;
import com.michael_delivery.backend.service.DeliveryDetailService;
import com.michael_delivery.backend.util.CustomCollectors;
import com.michael_delivery.backend.util.ReferencedException;
import com.michael_delivery.backend.util.ReferencedWarning;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/deliveryDetails", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeliveryDetailResource {

    private final DeliveryDetailService deliveryDetailService;
    private final OrdersRepository ordersRepository;

    public DeliveryDetailResource(final DeliveryDetailService deliveryDetailService,
            final OrdersRepository ordersRepository) {
        this.deliveryDetailService = deliveryDetailService;
        this.ordersRepository = ordersRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<DeliveryDetailDTO>> getAllDeliveryDetail(
    ) {
        return ResponseEntity.ok(deliveryDetailService.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<DeliveryDetailDTO>> searchDeliveryDetail(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "deliveryDetailId:asc") String[] sortBy,
            @RequestParam(required = false) Double pickupLatitude,
            @RequestParam(required = false) Double pickupLongitude,
            @RequestParam(required = false) String pickupAddressText,
            @RequestParam(required = false) String recipientPhoneNumber,
            @RequestParam(required = false) String recipientName,
            @RequestParam(required = false) PickupTimeType pickupTime,
            @RequestParam(required = false) Date pickupDateTime,
            @RequestParam(required = false) Date pickedUpDateTime,
            @RequestParam(required = false) Date desiredArrivalDateTime,
            @RequestParam(required = false) boolean pickupDateTimeIsAfter,
            @RequestParam(required = false) boolean pickedUpDateTimeIsAfter,
            @RequestParam(required = false) boolean desiredArrivalDateTimeIsAfter
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<DeliveryDetail> spec = new GenericSpecification<>();
        Specification<DeliveryDetail> pickupLatitudeSpec = spec.equals("pickupLatitude", pickupLatitude);
        Specification<DeliveryDetail> pickupLongitudeSpec = spec.equals("pickupLongitude", pickupLongitude);
        Specification<DeliveryDetail> pickupAddressTextSpec = spec.contains("pickupLongitude", pickupAddressText);
        Specification<DeliveryDetail> recipientPhoneNumberSpec = spec.contains("recipientPhoneNumber", recipientPhoneNumber);
        Specification<DeliveryDetail> recipientNameSpec = spec.contains("recipientName", recipientName);
        Specification<DeliveryDetail> pickupTimeSpec = spec.equals("pickupTime", pickupTime);
        Specification<DeliveryDetail> pickupDateTimeSpec = pickupDateTimeIsAfter ? spec.dateAfter("pickupDateTime",pickupDateTime) : spec.dateBefore("pickupDateTime", pickupDateTime);
        Specification<DeliveryDetail> pickedUpDateTimeSpec = pickedUpDateTimeIsAfter ? spec.dateAfter("pickedUpDateTime",pickedUpDateTime) : spec.dateBefore("pickedUpDateTime", pickedUpDateTime);
        Specification<DeliveryDetail> desiredArrivalDateTimeSpec = desiredArrivalDateTimeIsAfter ? spec.dateAfter("desiredArrivalDateTime",desiredArrivalDateTime) : spec.dateBefore("desiredArrivalDateTime", desiredArrivalDateTime);
        Specification<DeliveryDetail> finalSpec = Specification.where(pickupLatitudeSpec).and(pickupLongitudeSpec).and(pickupAddressTextSpec)
                .and(recipientPhoneNumberSpec).and(recipientNameSpec).and(pickupTimeSpec)
                .and(pickupDateTimeSpec).and(pickedUpDateTimeSpec).and(desiredArrivalDateTimeSpec);
        return ResponseEntity.ok(deliveryDetailService.search(finalSpec,pageable.getPageable()));
    }

    @GetMapping
    public ResponseEntity<Page<DeliveryDetailDTO>> getAllDeliveryDetail(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "deliveryDetailId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(deliveryDetailService.findAll(pageable.getPageable()));
    }


    @GetMapping("/{deliveryDetailId}")
    public ResponseEntity<DeliveryDetailDTO> getDeliveryDetail(
            @PathVariable(name = "deliveryDetailId") final Long deliveryDetailId) {
        return ResponseEntity.ok(deliveryDetailService.get(deliveryDetailId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDeliveryDetail(
            @RequestBody @Valid final DeliveryDetailDTO deliveryDetailDTO) {
        final Long createdDeliveryDetailId = deliveryDetailService.create(deliveryDetailDTO);
        return new ResponseEntity<>(createdDeliveryDetailId, HttpStatus.CREATED);
    }

    @PutMapping("/{deliveryDetailId}")
    public ResponseEntity<Long> updateDeliveryDetail(
            @PathVariable(name = "deliveryDetailId") final Long deliveryDetailId,
            @RequestBody @Valid final DeliveryDetailDTO deliveryDetailDTO) {
        deliveryDetailService.update(deliveryDetailId, deliveryDetailDTO);
        return ResponseEntity.ok(deliveryDetailId);
    }

    @DeleteMapping("/{deliveryDetailId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDeliveryDetail(
            @PathVariable(name = "deliveryDetailId") final Long deliveryDetailId) {
        final ReferencedWarning referencedWarning = deliveryDetailService.getReferencedWarning(deliveryDetailId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        deliveryDetailService.delete(deliveryDetailId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/orderValues")
    public ResponseEntity<Map<Long, String>> getOrderValues() {
        return ResponseEntity.ok(ordersRepository.findAll(Sort.by("orderId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Orders::getOrderId, Orders::getOrderNumber)));
    }

}
