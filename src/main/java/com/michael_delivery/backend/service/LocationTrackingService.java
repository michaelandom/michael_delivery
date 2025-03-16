package com.michael_delivery.backend.service;


import com.michael_delivery.backend.model.LocationTracking;
import com.michael_delivery.backend.repository.LocationTrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LocationTrackingService {

    @Autowired
    private LocationTrackingRepository locationTrackingRepository;

    public Page<LocationTracking> getLocationTrackingByRiderId(String riderId, Pageable pageable) {
        return locationTrackingRepository.findByRiderId(riderId, pageable);
    }

    public Page<LocationTracking> getLocationTrackingByRiderIdAndTimestampBetween(String riderId, Date startDate, Date endDate, Pageable pageable) {
        return locationTrackingRepository.findByRiderIdAndTimestampBetween(riderId, startDate, endDate, pageable);
    }

    public Page<LocationTracking> getLocationTrackingByOrderId(String orderId, Pageable pageable) {
        return locationTrackingRepository.findByOrderId(orderId, pageable);
    }

    public Page<LocationTracking> getLocationTrackingByStatus(String status, Pageable pageable) {
        return locationTrackingRepository.findByStatus(status, pageable);
    }

    public Page<LocationTracking> getLocationTrackingByNetworkType(String networkType, Pageable pageable) {
        return locationTrackingRepository.findByNetworkType(networkType, pageable);
    }

    public Page<LocationTracking> getLocationTrackingByMetadataDeviceId(String deviceId, Pageable pageable) {
        return locationTrackingRepository.findByMetadataDeviceId(deviceId, pageable);
    }

    public Page<LocationTracking> getLocationTrackingByMetadataAppState(String appState, Pageable pageable) {
        return locationTrackingRepository.findByMetadataAppState(appState, pageable);
    }

    public Page<LocationTracking> getLocationTrackingByMetadataLocationPermission(String locationPermission, Pageable pageable) {
        return locationTrackingRepository.findByMetadataLocationPermission(locationPermission, pageable);
    }

    public Page<LocationTracking> getLocationTrackingNear(Point location, Distance distance, Pageable pageable) {
        return locationTrackingRepository.findByLocationNear(location, distance, pageable);
    }

    public Page<LocationTracking> getRidersWithLowBattery(Double threshold, Pageable pageable) {
        return locationTrackingRepository.findRidersWithLowBattery(threshold, pageable);
    }

    public List<LocationTracking> getAllLocationTrackingByRiderId(String riderId) {
        return locationTrackingRepository.findByRiderId(riderId);
    }

    public List<LocationTracking> getAllLocationTrackingByOrderId(String orderId) {
        return locationTrackingRepository.findByOrderId(orderId);
    }

    public void deleteLocationTracking(String id) {
        locationTrackingRepository.deleteById(id);
    }

    public void deleteAllLocationTracking() {
        locationTrackingRepository.deleteAll();
    }
    public LocationTracking saveLocationTracking(LocationTracking locationTracking) {
        return locationTrackingRepository.save(locationTracking);
    }

    public List<LocationTracking> saveAllLocationTracking(List<LocationTracking> locationTrackings) {
        return locationTrackingRepository.saveAll(locationTrackings);
    }
    public boolean existsById(String id) {
        return locationTrackingRepository.existsById(id);
    }
}
