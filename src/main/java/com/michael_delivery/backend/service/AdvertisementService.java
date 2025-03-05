package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.Advertisement;
import com.michael_delivery.backend.model.AdvertisementDTO;
import com.michael_delivery.backend.repos.AdvertisementRepository;
import com.michael_delivery.backend.specification.AdvertisementSpecification;
import com.michael_delivery.backend.util.ShareFunction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class AdvertisementService extends BaseService<Advertisement, AdvertisementDTO,Long, AdvertisementRepository> {
    private final AdvertisementRepository advertisementRepository;
    public AdvertisementService(final AdvertisementRepository advertisementRepository) {
        super(advertisementRepository,"advertisementId");
        this.advertisementRepository = advertisementRepository;
    }

    @Override
    protected AdvertisementDTO createDTO() {
        return new AdvertisementDTO();
    }

    @Override
    protected Advertisement createEntity() {
        return new Advertisement();
    }

    public Page<AdvertisementDTO> search(String title, String content, Pageable pageable) {
        pageable = ShareFunction.getPageable(pageable);
        Specification<Advertisement> spec = Specification.where(null);

        if (title != null && !title.isEmpty()) {
            spec = spec.and(AdvertisementSpecification.titleContains(title));
        }
        if (content != null && !content.isEmpty()) {
            spec = spec.and(AdvertisementSpecification.contentContains(content));
        }

        return advertisementRepository.findAll(spec, pageable);
    }

    @Override
    protected AdvertisementDTO mapToDTO(final Advertisement advertisement,
                                      final AdvertisementDTO advertisementDTO) {
        advertisementDTO.setAdvertisementId(advertisement.getAdvertisementId());
        advertisementDTO.setTitle(advertisement.getTitle());
        advertisementDTO.setContent(advertisement.getContent());
        advertisementDTO.setImageUrl(advertisement.getImageUrl());
        return advertisementDTO;
    }
    @Override
    protected Advertisement mapToEntity(final AdvertisementDTO advertisementDTO,
                                      final Advertisement advertisement) {
        advertisement.setTitle(advertisementDTO.getTitle());
        advertisement.setContent(advertisementDTO.getContent());
        advertisement.setImageUrl(advertisementDTO.getImageUrl());
        return advertisement;
    }

}
