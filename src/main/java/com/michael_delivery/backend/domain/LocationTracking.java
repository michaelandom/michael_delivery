package com.michael_delivery.backend.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

// Location Tracking Model
@Document(collection = "location_tracking")
public class LocationTracking {
    @Id
    private String id;
    private String riderId;
    private Date timestamp;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private Location location;
    private Double speed;
    private Double heading;
    private LocationTracking.Acceleration acceleration;
    private Double batteryLevel;
    private String batteryStatus;
    private String orderId;
    private String status;
    private Double signalStrength;
    private String networkType;
    private LocationTracking.LocationMetadata metadata;

    public static class Acceleration {
        private Double x;
        private Double y;
        private Double z;

        // Getters and setters
        public Double getX() {
            return x;
        }

        public void setX(Double x) {
            this.x = x;
        }

        public Double getY() {
            return y;
        }

        public void setY(Double y) {
            this.y = y;
        }

        public Double getZ() {
            return z;
        }

        public void setZ(Double z) {
            this.z = z;
        }
    }

    public static class LocationMetadata {
        private String deviceId;
        private String appState;
        private String locationPermission;
        private String locationSource;

        // Getters and setters
        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getAppState() {
            return appState;
        }

        public void setAppState(String appState) {
            this.appState = appState;
        }

        public String getLocationPermission() {
            return locationPermission;
        }

        public void setLocationPermission(String locationPermission) {
            this.locationPermission = locationPermission;
        }

        public String getLocationSource() {
            return locationSource;
        }

        public void setLocationSource(String locationSource) {
            this.locationSource = locationSource;
        }
    }

    // Getters and setters for main class
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRiderId() {
        return riderId;
    }

    public void setRiderId(String riderId) {
        this.riderId = riderId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getHeading() {
        return heading;
    }

    public void setHeading(Double heading) {
        this.heading = heading;
    }

    public LocationTracking.Acceleration getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(LocationTracking.Acceleration acceleration) {
        this.acceleration = acceleration;
    }

    public Double getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(Double batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public String getBatteryStatus() {
        return batteryStatus;
    }

    public void setBatteryStatus(String batteryStatus) {
        this.batteryStatus = batteryStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(Double signalStrength) {
        this.signalStrength = signalStrength;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public LocationTracking.LocationMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(LocationTracking.LocationMetadata metadata) {
        this.metadata = metadata;
    }
}
