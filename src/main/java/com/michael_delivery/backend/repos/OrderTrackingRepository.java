package com.michael_delivery.backend.repos;


import com.michael_delivery.backend.domain.OrderTracking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderTrackingRepository extends MongoRepository<OrderTracking, String>,
        PagingAndSortingRepository<OrderTracking, String> {
    Page<OrderTracking> findByRiderId(String riderId, Pageable pageable);

    Page<OrderTracking> findByUserId(String userId, Pageable pageable);

    Page<OrderTracking> findByStatus(String status, Pageable pageable);

    Page<OrderTracking> findByMetadataServiceType(String serviceType, Pageable pageable);

    Page<OrderTracking> findByMetadataVehicleType(String vehicleType, Pageable pageable);

    Page<OrderTracking> findByCreatedAtBetween(Date startDate, Date endDate, Pageable pageable);

    Page<OrderTracking> findByUserIdAndStatus(String userId, String status, Pageable pageable);

    @Query("{'pickup.location': {$near: {$geometry: {type: 'Point', coordinates: ?0}, $maxDistance: ?1}}}")
    Page<OrderTracking> findByPickupLocationNear(List<Double> coordinates, Double maxDistance, Pageable pageable);

    @Query("{'dropoff.location': {$near: {$geometry: {type: 'Point', coordinates: ?0}, $maxDistance: ?1}}}")
    Page<OrderTracking> findByDropoffLocationNear(List<Double> coordinates, Double maxDistance, Pageable pageable);

    @Query("{'route.duration.actual': {$gt: '$route.duration.estimated'}}")
    Page<OrderTracking> findOrdersWithDelays(Pageable pageable);

    @Query("{'route.routeChanges': {$exists: true, $ne: []}}")
    Page<OrderTracking> findOrdersWithRouteChanges(Pageable pageable);

    @Query("{'route.routeChanges.reason': ?0}")
    Page<OrderTracking> findOrdersByRouteChangeReason(String reason, Pageable pageable);

    Optional<OrderTracking> findByOrderId(String orderId);
    List<OrderTracking> findByUserId(String userId);
    long countByStatus(String status);
}