package com.michael_delivery.backend.service;


import com.michael_delivery.backend.domain.ActivityLog;
import com.michael_delivery.backend.repos.ActivityLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityLogService {

    private final ActivityLogRepository activityLogRepository;


    public ActivityLogService(ActivityLogRepository activityLogRepository) {
        this.activityLogRepository = activityLogRepository;
    }

    public ActivityLog saveActivityLog(ActivityLog activityLog) {
        return activityLogRepository.save(activityLog);
    }

    public List<ActivityLog> saveAllActivityLogs(List<ActivityLog> activityLogs) {
        return activityLogRepository.saveAll(activityLogs);
    }

    public Page<ActivityLog> getAllActivityLogs(Pageable pageable) {
        return activityLogRepository.findAll(pageable);
    }

    public Optional<ActivityLog> getActivityLogById(String id) {
        return activityLogRepository.findById(id);
    }

    public Page<ActivityLog> getActivityLogsByUserId(String userId, Pageable pageable) {
        return activityLogRepository.findByUserId(userId, pageable);
    }

    public Page<ActivityLog> getActivityLogsByRiderId(String riderId, Pageable pageable) {
        return activityLogRepository.findByRiderId(riderId, pageable);
    }

    public Page<ActivityLog> getActivityLogsByType(String type, Pageable pageable) {
        return activityLogRepository.findByType(type, pageable);
    }

    public Page<ActivityLog> getActivityLogsByTimeRange(Date startDate, Date endDate, Pageable pageable) {
        return activityLogRepository.findByTimestampBetween(startDate, endDate, pageable);
    }

    public Page<ActivityLog> getActivityLogsByUserIdAndType(String userId, String type, Pageable pageable) {
        return activityLogRepository.findByUserIdAndType(userId, type, pageable);
    }

    public Page<ActivityLog> getActivityLogsByUserIdAndTimeRange(String userId, Date startDate, Date endDate, Pageable pageable) {
        return activityLogRepository.findByUserIdAndTimestampBetween(userId, startDate, endDate, pageable);
    }

    public Page<ActivityLog> getActivityLogsByAction(String action, Pageable pageable) {
        return activityLogRepository.findByDetailsAction(action, pageable);
    }

    public Page<ActivityLog> getActivityLogsByPlatform(String platform, Pageable pageable) {
        return activityLogRepository.findByDetailsPlatform(platform, pageable);
    }

    public Page<ActivityLog> getActivityLogsByIpAddress(String ipAddress, Pageable pageable) {
        return activityLogRepository.findByDetailsIpAddress(ipAddress, pageable);
    }

    public Page<ActivityLog> getActivityLogsByFeatureFlag(String featureFlag, Pageable pageable) {
        return activityLogRepository.findByFeatureFlag(featureFlag, pageable);
    }

    public List<ActivityLog> getAllActivityLogs() {
        return activityLogRepository.findAll();
    }

    public List<ActivityLog> getActivityLogsByUserId(String userId) {
        return activityLogRepository.findByUserId(userId);
    }

    public List<ActivityLog> getActivityLogsByRiderId(String riderId) {
        return activityLogRepository.findByRiderId(riderId);
    }

    public List<ActivityLog> getActivityLogsByType(String type) {
        return activityLogRepository.findByType(type);
    }

    public void deleteActivityLog(String id) {
        activityLogRepository.deleteById(id);
    }

    public void deleteAllActivityLogs() {
        activityLogRepository.deleteAll();
    }

    public long countActivityLogs() {
        return activityLogRepository.count();
    }

    public boolean existsById(String id) {
        return activityLogRepository.existsById(id);
    }
}