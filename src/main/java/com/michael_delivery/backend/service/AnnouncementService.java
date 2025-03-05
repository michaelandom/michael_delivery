package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.Announcement;
import com.michael_delivery.backend.model.AnnouncementDTO;
import com.michael_delivery.backend.repos.AnnouncementRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AnnouncementService extends BaseService<Announcement, AnnouncementDTO,Long, AnnouncementRepository>{

    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(final AnnouncementRepository announcementRepository) {
        super(announcementRepository,"advertisementId");
        this.announcementRepository = announcementRepository;
    }



    @Override
    protected AnnouncementDTO mapToDTO(final Announcement announcement,
            final AnnouncementDTO announcementDTO) {
        announcementDTO.setAnnouncementId(announcement.getAnnouncementId());
        announcementDTO.setTitle(announcement.getTitle());
        announcementDTO.setContent(announcement.getContent());
        announcementDTO.setImageUrl(announcement.getImageUrl());
        return announcementDTO;
    }

    @Override
    protected Announcement mapToEntity(final AnnouncementDTO announcementDTO,
            final Announcement announcement) {
        announcement.setTitle(announcementDTO.getTitle());
        announcement.setContent(announcementDTO.getContent());
        announcement.setImageUrl(announcementDTO.getImageUrl());
        return announcement;
    }

    @Override
    protected AnnouncementDTO createDTO() {
        return new AnnouncementDTO();
    }

    @Override
    protected Announcement createEntity() {
        return new Announcement();
    }

}
