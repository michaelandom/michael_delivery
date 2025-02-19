package com.michael_delivery.backend.repos;

import com.michael_delivery.backend.domain.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}
