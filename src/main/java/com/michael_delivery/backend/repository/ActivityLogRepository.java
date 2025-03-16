package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.ActivityLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface ActivityLogRepository extends MongoRepository<ActivityLog, String>,
        PagingAndSortingRepository<ActivityLog, String> {
    Page<ActivityLog> findByUserId(String userId, Pageable pageable);

    Page<ActivityLog> findByRiderId(String riderId, Pageable pageable);

    Page<ActivityLog> findByType(String type, Pageable pageable);

    Page<ActivityLog> findByTimestampBetween(Date startDate, Date endDate, Pageable pageable);

    Page<ActivityLog> findByUserIdAndType(String userId, String type, Pageable pageable);

    Page<ActivityLog> findByUserIdAndTimestampBetween(String userId, Date startDate, Date endDate, Pageable pageable);

    Page<ActivityLog> findByDetailsAction(String action, Pageable pageable);

    Page<ActivityLog> findByDetailsPlatform(String platform, Pageable pageable);

    Page<ActivityLog> findByDetailsIpAddress(String ipAddress, Pageable pageable);

    @Query("{'metadata.featureFlags': ?0}")
    Page<ActivityLog> findByFeatureFlag(String featureFlag, Pageable pageable);

    List<ActivityLog> findByUserId(String userId);
    List<ActivityLog> findByRiderId(String riderId);
    List<ActivityLog> findByType(String type);
}
