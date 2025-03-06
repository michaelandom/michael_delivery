package com.michael_delivery.backend.rest;


import com.michael_delivery.backend.domain.OrderTracking;
import com.michael_delivery.backend.service.OrderTrackingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/order-tracking")
public class OrderTrackingResource {

    private final OrderTrackingService orderTrackingService;

    public OrderTrackingResource(OrderTrackingService orderTrackingService) {
        this.orderTrackingService = orderTrackingService;
    }

    @GetMapping
    public ResponseEntity<Page<OrderTracking>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ?
                Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        return ResponseEntity.ok(orderTrackingService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderTracking> getOrderById(@PathVariable String id) {
        return orderTrackingService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderTracking> getOrderByOrderId(@PathVariable String orderId) {
        return orderTrackingService.findByOrderId(orderId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    @PostMapping
    public ResponseEntity<OrderTracking> createOrder(@RequestBody OrderTracking orderTracking) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderTrackingService.saveOrderTracking(orderTracking));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderTracking> updateOrder(
            @PathVariable String id,
            @RequestBody OrderTracking orderTracking) {

        if (!orderTrackingService.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }

        orderTracking.setId(id);
        return ResponseEntity.ok(orderTrackingService.saveOrderTracking(orderTracking));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String id) {
        if (!orderTrackingService.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }

        orderTrackingService.deleteOrderTracking(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<OrderTracking>> getOrdersByUserId(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(orderTrackingService.findByUserId(userId, pageable));
    }

    @GetMapping("/rider/{riderId}")
    public ResponseEntity<Page<OrderTracking>> getOrdersByRiderId(
            @PathVariable String riderId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(orderTrackingService.findByRiderId(riderId, pageable));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<OrderTracking>> getOrdersByStatus(
            @PathVariable String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(orderTrackingService.findByStatus(status, pageable));
    }

    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<Page<OrderTracking>> getOrdersByUserIdAndStatus(
            @PathVariable String userId,
            @PathVariable String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(orderTrackingService.findByUserIdAndStatus(userId, status, pageable));
    }

    @GetMapping("/service-type/{serviceType}")
    public ResponseEntity<Page<OrderTracking>> getOrdersByServiceType(
            @PathVariable String serviceType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(orderTrackingService.findByMetadataServiceType(serviceType, pageable));
    }

    @GetMapping("/vehicle-type/{vehicleType}")
    public ResponseEntity<Page<OrderTracking>> getOrdersByVehicleType(
            @PathVariable String vehicleType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(orderTrackingService.findByMetadataVehicleType(vehicleType, pageable));
    }

    @GetMapping("/date-range")
    public ResponseEntity<Page<OrderTracking>> getOrdersByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(orderTrackingService.findByCreatedAtBetween(startDate, endDate, pageable));
    }

    @GetMapping("/location/near")
    public ResponseEntity<Page<OrderTracking>> getOrdersNearLocation(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "5000") double maxDistance,
            @RequestParam(defaultValue = "true") boolean isPickup,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(orderTrackingService.findOrdersNearLocation(
                latitude, longitude, maxDistance, isPickup, pageable));
    }

    @GetMapping("/delayed")
    public ResponseEntity<Page<OrderTracking>> getDelayedOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(orderTrackingService.findOrdersWithDelays(pageable));
    }

    @GetMapping("/route-changes")
    public ResponseEntity<Page<OrderTracking>> getOrdersWithRouteChanges(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(orderTrackingService.findOrdersWithRouteChanges(pageable));
    }

    @GetMapping("/route-changes/reason/{reason}")
    public ResponseEntity<Page<OrderTracking>> getOrdersByRouteChangeReason(
            @PathVariable String reason,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(orderTrackingService.findOrdersByRouteChangeReason(reason, pageable));
    }

    @GetMapping("/count/status/{status}")
    public ResponseEntity<Map<String, Long>> countOrdersByStatus(@PathVariable String status) {
        long count = orderTrackingService.countByStatus(status);
        return ResponseEntity.ok(Map.of("count", count));
    }

}
