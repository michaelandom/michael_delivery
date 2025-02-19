package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.Announcement;
import com.michael_delivery.backend.model.AnnouncementDTO;
import com.michael_delivery.backend.repos.AnnouncementRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(final AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    public List<AnnouncementDTO> findAll() {
        final List<Announcement> announcements = announcementRepository.findAll(Sort.by("announcementId"));
        return announcements.stream()
                .map(announcement -> mapToDTO(announcement, new AnnouncementDTO()))
                .toList();
    }

    public AnnouncementDTO get(final Long announcementId) {
        return announcementRepository.findById(announcementId)
                .map(announcement -> mapToDTO(announcement, new AnnouncementDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final AnnouncementDTO announcementDTO) {
        final Announcement announcement = new Announcement();
        mapToEntity(announcementDTO, announcement);
        return announcementRepository.save(announcement).getAnnouncementId();
    }

    public void update(final Long announcementId, final AnnouncementDTO announcementDTO) {
        final Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(announcementDTO, announcement);
        announcementRepository.save(announcement);
    }

    public void delete(final Long announcementId) {
        announcementRepository.deleteById(announcementId);
    }

    private AnnouncementDTO mapToDTO(final Announcement announcement,
            final AnnouncementDTO announcementDTO) {
        announcementDTO.setAnnouncementId(announcement.getAnnouncementId());
        announcementDTO.setTitle(announcement.getTitle());
        announcementDTO.setContent(announcement.getContent());
        announcementDTO.setImageUrl(announcement.getImageUrl());
        return announcementDTO;
    }

    private Announcement mapToEntity(final AnnouncementDTO announcementDTO,
            final Announcement announcement) {
        announcement.setTitle(announcementDTO.getTitle());
        announcement.setContent(announcementDTO.getContent());
        announcement.setImageUrl(announcementDTO.getImageUrl());
        return announcement;
    }

}
