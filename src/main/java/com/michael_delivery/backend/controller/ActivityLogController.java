package com.michael_delivery.backend.controller;


import com.michael_delivery.backend.model.ActivityLog;
import com.michael_delivery.backend.service.ActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/activity-logs")
public class ActivityLogController {

    private final ActivityLogService activityLogService;

    @Autowired
    public ActivityLogController(ActivityLogService activityLogService) {
        this.activityLogService = activityLogService;
    }

    @PostMapping
    public ResponseEntity<ActivityLog> createActivityLog(@RequestBody ActivityLog activityLog) {
        ActivityLog savedActivityLog = activityLogService.saveActivityLog(activityLog);
        return new ResponseEntity<>(savedActivityLog, HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<ActivityLog>> createActivityLogs(@RequestBody List<ActivityLog> activityLogs) {
        List<ActivityLog> savedActivityLogs = activityLogService.saveAllActivityLogs(activityLogs);
        return new ResponseEntity<>(savedActivityLogs, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityLog> getActivityLogById(@PathVariable String id) {
        Optional<ActivityLog> activityLog = activityLogService.getActivityLogById(id);
        return activityLog.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<ActivityLog>> getAllActivityLogs(Pageable pageable) {
        Page<ActivityLog> activityLogs = activityLogService.getAllActivityLogs(pageable);
        return new ResponseEntity<>(activityLogs, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<ActivityLog>> getActivityLogsByUserId(@PathVariable String userId, Pageable pageable) {
        Page<ActivityLog> activityLogs = activityLogService.getActivityLogsByUserId(userId, pageable);
        return new ResponseEntity<>(activityLogs, HttpStatus.OK);
    }

    @GetMapping("/rider/{riderId}")
    public ResponseEntity<Page<ActivityLog>> getActivityLogsByRiderId(@PathVariable String riderId, Pageable pageable) {
        Page<ActivityLog> activityLogs = activityLogService.getActivityLogsByRiderId(riderId, pageable);
        return new ResponseEntity<>(activityLogs, HttpStatus.OK);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<Page<ActivityLog>> getActivityLogsByType(@PathVariable String type, Pageable pageable) {
        Page<ActivityLog> activityLogs = activityLogService.getActivityLogsByType(type, pageable);
        return new ResponseEntity<>(activityLogs, HttpStatus.OK);
    }

    @GetMapping("/time-range")
    public ResponseEntity<Page<ActivityLog>> getActivityLogsByTimeRange(@RequestParam Date startDate, @RequestParam Date endDate, Pageable pageable) {
        Page<ActivityLog> activityLogs = activityLogService.getActivityLogsByTimeRange(startDate, endDate, pageable);
        return new ResponseEntity<>(activityLogs, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/type/{type}")
    public ResponseEntity<Page<ActivityLog>> getActivityLogsByUserIdAndType(
            @PathVariable String userId, @PathVariable String type, Pageable pageable) {
        Page<ActivityLog> activityLogs = activityLogService.getActivityLogsByUserIdAndType(userId, type, pageable);
        return new ResponseEntity<>(activityLogs, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/time-range")
    public ResponseEntity<Page<ActivityLog>> getActivityLogsByUserIdAndTimeRange(
            @PathVariable String userId, @RequestParam Date startDate, @RequestParam Date endDate, Pageable pageable) {
        Page<ActivityLog> activityLogs = activityLogService.getActivityLogsByUserIdAndTimeRange(userId, startDate, endDate, pageable);
        return new ResponseEntity<>(activityLogs, HttpStatus.OK);
    }

    @GetMapping("/action/{action}")
    public ResponseEntity<Page<ActivityLog>> getActivityLogsByAction(@PathVariable String action, Pageable pageable) {
        Page<ActivityLog> activityLogs = activityLogService.getActivityLogsByAction(action, pageable);
        return new ResponseEntity<>(activityLogs, HttpStatus.OK);
    }

    @GetMapping("/platform/{platform}")
    public ResponseEntity<Page<ActivityLog>> getActivityLogsByPlatform(@PathVariable String platform, Pageable pageable) {
        Page<ActivityLog> activityLogs = activityLogService.getActivityLogsByPlatform(platform, pageable);
        return new ResponseEntity<>(activityLogs, HttpStatus.OK);
    }

    @GetMapping("/ip-address/{ipAddress}")
    public ResponseEntity<Page<ActivityLog>> getActivityLogsByIpAddress(@PathVariable String ipAddress, Pageable pageable) {
        Page<ActivityLog> activityLogs = activityLogService.getActivityLogsByIpAddress(ipAddress, pageable);
        return new ResponseEntity<>(activityLogs, HttpStatus.OK);
    }

    @GetMapping("/feature-flag/{featureFlag}")
    public ResponseEntity<Page<ActivityLog>> getActivityLogsByFeatureFlag(@PathVariable String featureFlag, Pageable pageable) {
        Page<ActivityLog> activityLogs = activityLogService.getActivityLogsByFeatureFlag(featureFlag, pageable);
        return new ResponseEntity<>(activityLogs, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivityLog(@PathVariable String id) {
        if (activityLogService.existsById(id)) {
            activityLogService.deleteActivityLog(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllActivityLogs() {
        activityLogService.deleteAllActivityLogs();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countActivityLogs() {
        long count = activityLogService.countActivityLogs();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
