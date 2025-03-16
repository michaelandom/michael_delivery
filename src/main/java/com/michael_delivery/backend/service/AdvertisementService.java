package com.michael_delivery.backend.service;

import com.michael_delivery.backend.model.Advertisement;
import com.michael_delivery.backend.dto.AdvertisementDTO;
import com.michael_delivery.backend.repository.AdvertisementRepository;
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

    @Override
    public Page<AdvertisementDTO> search(Specification<Advertisement> query,Pageable pageable) {
        return this.advertisementRepository.findAll(query, pageable);
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
