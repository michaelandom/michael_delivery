package com.michael_delivery.backend.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

// Activity Logs Model
@Document(collection = "activity_logs")
public class ActivityLog  extends BaseModel<String> {
    @Id
    private String id;
    private String userId;
    private String riderId;
    private String type;
    private Date timestamp;
    private Details details;
    private Metadata metadata;

    // Nested classes
    public static class Details {
        private String action;
        private String actionSource;
        private String actionReason;
        private String platform;
        private String ipAddress;
        private Location location;
        private DeviceInfo deviceInfo;

        // Getters and setters
        public String getAction() { return action; }
        public void setAction(String action) { this.action = action; }
        public String getActionSource() { return actionSource; }
        public void setActionSource(String actionSource) { this.actionSource = actionSource; }
        public String getActionReason() { return actionReason; }
        public void setActionReason(String actionReason) { this.actionReason = actionReason; }
        public String getPlatform() { return platform; }
        public void setPlatform(String platform) { this.platform = platform; }
        public String getIpAddress() { return ipAddress; }
        public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
        public Location getLocation() { return location; }
        public void setLocation(Location location) { this.location = location; }
        public DeviceInfo getDeviceInfo() { return deviceInfo; }
        public void setDeviceInfo(DeviceInfo deviceInfo) { this.deviceInfo = deviceInfo; }
    }

    public static class DeviceInfo {
        private String type;
        private String os;
        private String osVersion;
        private String browser;
        private String browserVersion;
        private String appVersion;

        // Getters and setters
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getOs() { return os; }
        public void setOs(String os) { this.os = os; }
        public String getOsVersion() { return osVersion; }
        public void setOsVersion(String osVersion) { this.osVersion = osVersion; }
        public String getBrowser() { return browser; }
        public void setBrowser(String browser) { this.browser = browser; }
        public String getBrowserVersion() { return browserVersion; }
        public void setBrowserVersion(String browserVersion) { this.browserVersion = browserVersion; }
        public String getAppVersion() { return appVersion; }
        public void setAppVersion(String appVersion) { this.appVersion = appVersion; }
    }

    public static class Metadata {
        private String sessionId;
        private String requestId;
        private String correlationId;
        private String previousEventId;
        private String serviceVersion;
        private List<String> featureFlags;
        private String environment;
        private String region;

        // Getters and setters
        public String getSessionId() { return sessionId; }
        public void setSessionId(String sessionId) { this.sessionId = sessionId; }
        public String getRequestId() { return requestId; }
        public void setRequestId(String requestId) { this.requestId = requestId; }
        public String getCorrelationId() { return correlationId; }
        public void setCorrelationId(String correlationId) { this.correlationId = correlationId; }
        public String getPreviousEventId() { return previousEventId; }
        public void setPreviousEventId(String previousEventId) { this.previousEventId = previousEventId; }
        public String getServiceVersion() { return serviceVersion; }
        public void setServiceVersion(String serviceVersion) { this.serviceVersion = serviceVersion; }
        public List<String> getFeatureFlags() { return featureFlags; }
        public void setFeatureFlags(List<String> featureFlags) { this.featureFlags = featureFlags; }
        public String getEnvironment() { return environment; }
        public void setEnvironment(String environment) { this.environment = environment; }
        public String getRegion() { return region; }
        public void setRegion(String region) { this.region = region; }
    }

    // Getters and setters for main class
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getRiderId() { return riderId; }
    public void setRiderId(String riderId) { this.riderId = riderId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
    public Details getDetails() { return details; }
    public void setDetails(Details details) { this.details = details; }
    public Metadata getMetadata() { return metadata; }
    public void setMetadata(Metadata metadata) { this.metadata = metadata; }
}

