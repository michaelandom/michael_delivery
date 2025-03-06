package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.OrderTracking;
import com.michael_delivery.backend.repos.OrderTrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;

@Service
public class OrderTrackingService {

    private final OrderTrackingRepository orderTrackingRepository;

    @Autowired
    public OrderTrackingService(OrderTrackingRepository orderTrackingRepository) {
        this.orderTrackingRepository = orderTrackingRepository;
    }

    public OrderTracking saveOrderTracking(OrderTracking orderTracking) {
        return orderTrackingRepository.save(orderTracking);
    }

    public Optional<OrderTracking> findById(String id) {
        return orderTrackingRepository.findById(id);
    }

    public Optional<OrderTracking> findByOrderId(String orderId) {
        return orderTrackingRepository.findByOrderId(orderId);
    }

    public List<OrderTracking> findAllByUserId(String userId) {
        return orderTrackingRepository.findByUserId(userId);
    }

    public Page<OrderTracking> findByRiderId(String riderId, Pageable pageable) {
        return orderTrackingRepository.findByRiderId(riderId, pageable);
    }

    public Page<OrderTracking> findByUserId(String userId, Pageable pageable) {
        return orderTrackingRepository.findByUserId(userId, pageable);
    }

    public Page<OrderTracking> findByStatus(String status, Pageable pageable) {
        return orderTrackingRepository.findByStatus(status, pageable);
    }

    public Page<OrderTracking> findByMetadataServiceType(String serviceType, Pageable pageable) {
        return orderTrackingRepository.findByMetadataServiceType(serviceType, pageable);
    }

    public Page<OrderTracking> findByMetadataVehicleType(String vehicleType, Pageable pageable) {
        return orderTrackingRepository.findByMetadataVehicleType(vehicleType, pageable);
    }

    public Page<OrderTracking> findByCreatedAtBetween(Date startDate, Date endDate, Pageable pageable) {
        return orderTrackingRepository.findByCreatedAtBetween(startDate, endDate, pageable);
    }

    public Page<OrderTracking> findByUserIdAndStatus(String userId, String status, Pageable pageable) {
        return orderTrackingRepository.findByUserIdAndStatus(userId, status, pageable);
    }

    public Page<OrderTracking> findByPickupLocationNear(List<Double> coordinates, Double maxDistance, Pageable pageable) {
        return orderTrackingRepository.findByPickupLocationNear(coordinates, maxDistance, pageable);
    }

    public Page<OrderTracking> findByDropoffLocationNear(List<Double> coordinates, Double maxDistance, Pageable pageable) {
        return orderTrackingRepository.findByDropoffLocationNear(coordinates, maxDistance, pageable);
    }

    public Page<OrderTracking> findOrdersWithDelays(Pageable pageable) {
        return orderTrackingRepository.findOrdersWithDelays(pageable);
    }

    public Page<OrderTracking> findOrdersWithRouteChanges(Pageable pageable) {
        return orderTrackingRepository.findOrdersWithRouteChanges(pageable);
    }

    public Page<OrderTracking> findOrdersByRouteChangeReason(String reason, Pageable pageable) {
        return orderTrackingRepository.findOrdersByRouteChangeReason(reason, pageable);
    }

    public long countByStatus(String status) {
        return orderTrackingRepository.countByStatus(status);
    }

    public void deleteOrderTracking(String id) {
        orderTrackingRepository.deleteById(id);
    }

    public List<OrderTracking> findAll() {
        return orderTrackingRepository.findAll();
    }

    public Page<OrderTracking> findAll(Pageable pageable) {
        return orderTrackingRepository.findAll(pageable);
    }

    public Page<OrderTracking> findOrdersNearLocation(double latitude, double longitude, double maxDistanceInMeters,
                                                      boolean isPickup, Pageable pageable) {
        List<Double> coordinates = Arrays.asList(longitude, latitude);
        if (isPickup) {
            return orderTrackingRepository.findByPickupLocationNear(coordinates, maxDistanceInMeters, pageable);
        } else {
            return orderTrackingRepository.findByDropoffLocationNear(coordinates, maxDistanceInMeters, pageable);
        }
    }
}