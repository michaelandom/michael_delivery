package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.Orders;
import com.michael_delivery.backend.model.DeliveryDetailDTO;
import com.michael_delivery.backend.repos.OrdersRepository;
import com.michael_delivery.backend.service.DeliveryDetailService;
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
@RequestMapping(value = "/api/deliveryDetails", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeliveryDetailResource {

    private final DeliveryDetailService deliveryDetailService;
    private final OrdersRepository ordersRepository;

    public DeliveryDetailResource(final DeliveryDetailService deliveryDetailService,
            final OrdersRepository ordersRepository) {
        this.deliveryDetailService = deliveryDetailService;
        this.ordersRepository = ordersRepository;
    }

    @GetMapping
    public ResponseEntity<List<DeliveryDetailDTO>> getAllDeliveryDetails() {
        return ResponseEntity.ok(deliveryDetailService.findAll());
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
