package com.michael_delivery.backend.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

// OrderTracking Model
@Document(collection = "orders")
public class OrderTracking {
    @Id
    private String id;
    private String orderId;
    private String riderId;
    private String userId;
    private String status;
    private OrderTracking.LocationInfo pickup;
    private OrderTracking.LocationInfo dropoff;
    private OrderTracking.Route route;
    private OrderTracking.OrderMetadata metadata;
    private Date createdAt;
    private Date updatedAt;

    public static class LocationInfo {
        @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
        private Location location;
        private OrderTracking.Address address;
        private Date timestamp;

        // Getters and setters
        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public OrderTracking.Address getAddress() {
            return address;
        }

        public void setAddress(OrderTracking.Address address) {
            this.address = address;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }
    }

    public static class Address {
        private String formatted;
        private String street;
        private String city;
        private String state;
        private String country;
        private String postalCode;

        // Getters and setters
        public String getFormatted() {
            return formatted;
        }

        public void setFormatted(String formatted) {
            this.formatted = formatted;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }
    }

    public static class Route {
        private OrderTracking.Path currentPath;
        private List<OrderTracking.AlternativePath> alternativePaths;
        private OrderTracking.Distance distance;
        private OrderTracking.Duration duration;
        private List<OrderTracking.Waypoint> waypoints;
        private List<OrderTracking.RouteChange> routeChanges;

        // Getters and setters
        public OrderTracking.Path getCurrentPath() {
            return currentPath;
        }

        public void setCurrentPath(OrderTracking.Path currentPath) {
            this.currentPath = currentPath;
        }

        public List<OrderTracking.AlternativePath> getAlternativePaths() {
            return alternativePaths;
        }

        public void setAlternativePaths(List<OrderTracking.AlternativePath> alternativePaths) {
            this.alternativePaths = alternativePaths;
        }

        public OrderTracking.Distance getDistance() {
            return distance;
        }

        public void setDistance(OrderTracking.Distance distance) {
            this.distance = distance;
        }

        public OrderTracking.Duration getDuration() {
            return duration;
        }

        public void setDuration(OrderTracking.Duration duration) {
            this.duration = duration;
        }

        public List<OrderTracking.Waypoint> getWaypoints() {
            return waypoints;
        }

        public void setWaypoints(List<OrderTracking.Waypoint> waypoints) {
            this.waypoints = waypoints;
        }

        public List<OrderTracking.RouteChange> getRouteChanges() {
            return routeChanges;
        }

        public void setRouteChanges(List<OrderTracking.RouteChange> routeChanges) {
            this.routeChanges = routeChanges;
        }
    }

    public static class Path {
        private String type;
        private List<List<Double>> coordinates;

        // Getters and setters
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<List<Double>> getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(List<List<Double>> coordinates) {
            this.coordinates = coordinates;
        }
    }

    public static class AlternativePath {
        private OrderTracking.Path path;
        private Double estimatedDuration;
        private Double estimatedDistance;
        private String reason;

        // Getters and setters
        public OrderTracking.Path getPath() {
            return path;
        }

        public void setPath(OrderTracking.Path path) {
            this.path = path;
        }

        public Double getEstimatedDuration() {
            return estimatedDuration;
        }

        public void setEstimatedDuration(Double estimatedDuration) {
            this.estimatedDuration = estimatedDuration;
        }

        public Double getEstimatedDistance() {
            return estimatedDistance;
        }

        public void setEstimatedDistance(Double estimatedDistance) {
            this.estimatedDistance = estimatedDistance;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }

    public static class Distance {
        private Double estimated;
        private Double actual;
        private String unit;

        // Getters and setters
        public Double getEstimated() {
            return estimated;
        }

        public void setEstimated(Double estimated) {
            this.estimated = estimated;
        }

        public Double getActual() {
            return actual;
        }

        public void setActual(Double actual) {
            this.actual = actual;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }

    public static class Duration {
        private Double estimated;
        private Double actual;
        private String unit;

        // Getters and setters
        public Double getEstimated() {
            return estimated;
        }

        public void setEstimated(Double estimated) {
            this.estimated = estimated;
        }

        public Double getActual() {
            return actual;
        }

        public void setActual(Double actual) {
            this.actual = actual;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }

    public static class Waypoint {
        private Location location;
        private Date timestamp;
        private String type;
        private Integer sequence;
        private String status;

        // Getters and setters
        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Integer getSequence() {
            return sequence;
        }

        public void setSequence(Integer sequence) {
            this.sequence = sequence;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class RouteChange {
        private Date timestamp;
        private String reason;
        private OrderTracking.Path previousPath;
        private OrderTracking.Path newPath;
        private Double distanceImpact;
        private Double durationImpact;

        // Getters and setters
        public Date getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public OrderTracking.Path getPreviousPath() {
            return previousPath;
        }

        public void setPreviousPath(OrderTracking.Path previousPath) {
            this.previousPath = previousPath;
        }

        public OrderTracking.Path getNewPath() {
            return newPath;
        }

        public void setNewPath(OrderTracking.Path newPath) {
            this.newPath = newPath;
        }

        public Double getDistanceImpact() {
            return distanceImpact;
        }

        public void setDistanceImpact(Double distanceImpact) {
            this.distanceImpact = distanceImpact;
        }

        public Double getDurationImpact() {
            return durationImpact;
        }

        public void setDurationImpact(Double durationImpact) {
            this.durationImpact = durationImpact;
        }
    }

    public static class OrderMetadata {
        private String dispatchId;
        private String serviceType;
        private String vehicleType;
        private String routeOptimizationVersion;
        private String pricingVersion;
        private String matchingAlgorithmVersion;
        private List<OrderTracking.SystemNote> systemNotes;

        // Getters and setters
        public String getDispatchId() {
            return dispatchId;
        }

        public void setDispatchId(String dispatchId) {
            this.dispatchId = dispatchId;
        }

        public String getServiceType() {
            return serviceType;
        }

        public void setServiceType(String serviceType) {
            this.serviceType = serviceType;
        }

        public String getVehicleType() {
            return vehicleType;
        }

        public void setVehicleType(String vehicleType) {
            this.vehicleType = vehicleType;
        }

        public String getRouteOptimizationVersion() {
            return routeOptimizationVersion;
        }

        public void setRouteOptimizationVersion(String routeOptimizationVersion) {
            this.routeOptimizationVersion = routeOptimizationVersion;
        }

        public String getPricingVersion() {
            return pricingVersion;
        }

        public void setPricingVersion(String pricingVersion) {
            this.pricingVersion = pricingVersion;
        }

        public String getMatchingAlgorithmVersion() {
            return matchingAlgorithmVersion;
        }

        public void setMatchingAlgorithmVersion(String matchingAlgorithmVersion) {
            this.matchingAlgorithmVersion = matchingAlgorithmVersion;
        }

        public List<OrderTracking.SystemNote> getSystemNotes() {
            return systemNotes;
        }

        public void setSystemNotes(List<OrderTracking.SystemNote> systemNotes) {
            this.systemNotes = systemNotes;
        }
    }

    public static class SystemNote {
        private Date timestamp;
        private String type;
        private String message;
        private String severity;

        // Getters and setters
        public Date getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getSeverity() {
            return severity;
        }

        public void setSeverity(String severity) {
            this.severity = severity;
        }
    }

    // Getters and setters for main class
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRiderId() {
        return riderId;
    }

    public void setRiderId(String riderId) {
        this.riderId = riderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OrderTracking.LocationInfo getPickup() {
        return pickup;
    }

    public void setPickup(OrderTracking.LocationInfo pickup) {
        this.pickup = pickup;
    }

    public OrderTracking.LocationInfo getDropoff() {
        return dropoff;
    }

    public void setDropoff(OrderTracking.LocationInfo dropoff) {
        this.dropoff = dropoff;
    }

    public OrderTracking.Route getRoute() {
        return route;
    }

    public void setRoute(OrderTracking.Route route) {
        this.route = route;
    }

    public OrderTracking.OrderMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(OrderTracking.OrderMetadata metadata) {
        this.metadata = metadata;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
