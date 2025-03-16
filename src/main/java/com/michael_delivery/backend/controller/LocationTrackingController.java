package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.model.LocationTracking;
import com.michael_delivery.backend.service.LocationTrackingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/location-tracking")
public class LocationTrackingController {


    private LocationTrackingService locationTrackingService;

    public LocationTrackingController(LocationTrackingService locationTrackingService) {
        this.locationTrackingService = locationTrackingService;
    }

    @GetMapping("/rider/{riderId}")
    public ResponseEntity<Page<LocationTracking>> getLocationTrackingByRiderId(
            @PathVariable String riderId, Pageable pageable) {
        Page<LocationTracking> locationTrackings = locationTrackingService.getLocationTrackingByRiderId(riderId, pageable);
        return new ResponseEntity<>(locationTrackings, HttpStatus.OK);
    }

    @GetMapping("/rider/{riderId}/date-range")
    public ResponseEntity<Page<LocationTracking>> getLocationTrackingByRiderIdAndDateRange(
            @PathVariable String riderId,
            @RequestParam("startDate") Date startDate,
            @RequestParam("endDate") Date endDate,
            Pageable pageable) {
        Page<LocationTracking> locationTrackings = locationTrackingService.getLocationTrackingByRiderIdAndTimestampBetween(riderId, startDate, endDate, pageable);
        return new ResponseEntity<>(locationTrackings, HttpStatus.OK);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Page<LocationTracking>> getLocationTrackingByOrderId(
            @PathVariable String orderId, Pageable pageable) {
        Page<LocationTracking> locationTrackings = locationTrackingService.getLocationTrackingByOrderId(orderId, pageable);
        return new ResponseEntity<>(locationTrackings, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<LocationTracking>> getLocationTrackingByStatus(
            @PathVariable String status, Pageable pageable) {
        Page<LocationTracking> locationTrackings = locationTrackingService.getLocationTrackingByStatus(status, pageable);
        return new ResponseEntity<>(locationTrackings, HttpStatus.OK);
    }

    @GetMapping("/network-type/{networkType}")
    public ResponseEntity<Page<LocationTracking>> getLocationTrackingByNetworkType(
            @PathVariable String networkType, Pageable pageable) {
        Page<LocationTracking> locationTrackings = locationTrackingService.getLocationTrackingByNetworkType(networkType, pageable);
        return new ResponseEntity<>(locationTrackings, HttpStatus.OK);
    }

    @GetMapping("/metadata/device-id/{deviceId}")
    public ResponseEntity<Page<LocationTracking>> getLocationTrackingByMetadataDeviceId(
            @PathVariable String deviceId, Pageable pageable) {
        Page<LocationTracking> locationTrackings = locationTrackingService.getLocationTrackingByMetadataDeviceId(deviceId, pageable);
        return new ResponseEntity<>(locationTrackings, HttpStatus.OK);
    }

    @GetMapping("/metadata/app-state/{appState}")
    public ResponseEntity<Page<LocationTracking>> getLocationTrackingByMetadataAppState(
            @PathVariable String appState, Pageable pageable) {
        Page<LocationTracking> locationTrackings = locationTrackingService.getLocationTrackingByMetadataAppState(appState, pageable);
        return new ResponseEntity<>(locationTrackings, HttpStatus.OK);
    }

    @GetMapping("/metadata/location-permission/{locationPermission}")
    public ResponseEntity<Page<LocationTracking>> getLocationTrackingByMetadataLocationPermission(
            @PathVariable String locationPermission, Pageable pageable) {
        Page<LocationTracking> locationTrackings = locationTrackingService.getLocationTrackingByMetadataLocationPermission(locationPermission, pageable);
        return new ResponseEntity<>(locationTrackings, HttpStatus.OK);
    }

    @GetMapping("/near")
    public ResponseEntity<Page<LocationTracking>> getLocationTrackingNear(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude,
            @RequestParam("distance") double distance,
            Pageable pageable) {
        Point location = new Point(longitude, latitude);
        Distance dist = new Distance(distance); // Set the distance
        Page<LocationTracking> locationTrackings = locationTrackingService.getLocationTrackingNear(location, dist, pageable);
        return new ResponseEntity<>(locationTrackings, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocationTracking(@PathVariable String id) {
        if (locationTrackingService.existsById(id)) {
            locationTrackingService.deleteLocationTracking(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllLocationTracking() {
        locationTrackingService.deleteAllLocationTracking();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/low-battery")
    public ResponseEntity<Page<LocationTracking>> getRidersWithLowBattery(
            @RequestParam("threshold") double threshold, Pageable pageable) {
        Page<LocationTracking> locationTrackings = locationTrackingService.getRidersWithLowBattery(threshold, pageable);
        return new ResponseEntity<>(locationTrackings, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LocationTracking> saveLocationTracking(@RequestBody LocationTracking locationTracking) {
        LocationTracking savedLocationTracking = locationTrackingService.saveLocationTracking(locationTracking);
        return new ResponseEntity<>(savedLocationTracking, HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<LocationTracking>> saveAllLocationTracking(@RequestBody List<LocationTracking> locationTrackings) {
        List<LocationTracking> savedLocationTrackings = locationTrackingService.saveAllLocationTracking(locationTrackings);
        return new ResponseEntity<>(savedLocationTrackings, HttpStatus.CREATED);
    }
}
