package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.LocationTracking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface LocationTrackingRepository extends MongoRepository<LocationTracking, String>,
        PagingAndSortingRepository<LocationTracking, String> {
    Page<LocationTracking> findByRiderId(String riderId, Pageable pageable);

    Page<LocationTracking> findByRiderIdAndTimestampBetween(String riderId, Date startDate, Date endDate, Pageable pageable);

    Page<LocationTracking> findByOrderId(String orderId, Pageable pageable);

    Page<LocationTracking> findByStatus(String status, Pageable pageable);

    Page<LocationTracking> findByNetworkType(String networkType, Pageable pageable);

    Page<LocationTracking> findByMetadataDeviceId(String deviceId, Pageable pageable);

    Page<LocationTracking> findByMetadataAppState(String appState, Pageable pageable);

    Page<LocationTracking> findByMetadataLocationPermission(String locationPermission, Pageable pageable);

    Page<LocationTracking> findByLocationNear(Point location, Distance distance, Pageable pageable);

    @Query("{'batteryLevel': {$lt: ?0}}")
    Page<LocationTracking> findRidersWithLowBattery(Double threshold, Pageable pageable);

    List<LocationTracking> findByRiderId(String riderId);
    List<LocationTracking> findByOrderId(String orderId);
}