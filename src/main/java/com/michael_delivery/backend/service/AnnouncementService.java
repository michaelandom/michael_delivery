package com.michael_delivery.backend.service;
import com.michael_delivery.backend.model.Announcement;
import com.michael_delivery.backend.dto.AnnouncementDTO;
import com.michael_delivery.backend.repository.AnnouncementRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class AnnouncementService extends BaseService<Announcement, AnnouncementDTO,Long, AnnouncementRepository>{

    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(final AnnouncementRepository announcementRepository) {
        super(announcementRepository,"announcementId");
        this.announcementRepository = announcementRepository;
    }

    @Override
    public Page<AnnouncementDTO> search(Specification<Announcement> query, Pageable pageable) {
        return this.announcementRepository.findAll(query, pageable);
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
