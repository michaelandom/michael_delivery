package com.michael_delivery.backend.repository;

import com.michael_delivery.backend.model.Announcement;
import com.michael_delivery.backend.dto.AnnouncementDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AnnouncementRepository extends JpaRepository<Announcement, Long>, BaseRepository<AnnouncementDTO, Announcement> {
}
