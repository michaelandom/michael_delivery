package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.Advertisement;
import com.michael_delivery.backend.model.AdvertisementDTO;
import com.michael_delivery.backend.repos.AdvertisementRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.michael_delivery.backend.util.NotFoundException;
import java.util.List;

@Service
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;

    public AdvertisementService(final AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    public List<AdvertisementDTO> findAll() {
        final List<Advertisement> advertisements = advertisementRepository.findAll(Sort.by("advertisementId"));
        return advertisements.stream()
                .map(advertisement -> mapToDTO(advertisement, new AdvertisementDTO()))
                .toList();
    }

    public AdvertisementDTO get(final Long advertisementId) {
        return advertisementRepository.findById(advertisementId)
                .map(advertisement -> mapToDTO(advertisement, new AdvertisementDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final AdvertisementDTO advertisementDTO) {
        final Advertisement advertisement = new Advertisement();
        mapToEntity(advertisementDTO, advertisement);
        return advertisementRepository.save(advertisement).getAdvertisementId();
    }

    public void update(final Long advertisementId, final AdvertisementDTO advertisementDTO) {
        final Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(advertisementDTO, advertisement);
        advertisementRepository.save(advertisement);
    }

    public void delete(final Long advertisementId) {
        advertisementRepository.deleteById(advertisementId);
    }

    private AdvertisementDTO mapToDTO(final Advertisement advertisement,
                                      final AdvertisementDTO advertisementDTO) {
        advertisementDTO.setAdvertisementId(advertisement.getAdvertisementId());
        advertisementDTO.setTitle(advertisement.getTitle());
        advertisementDTO.setContent(advertisement.getContent());
        advertisementDTO.setImageUrl(advertisement.getImageUrl());
        return advertisementDTO;
    }

    private Advertisement mapToEntity(final AdvertisementDTO advertisementDTO,
                                      final Advertisement advertisement) {
        advertisement.setTitle(advertisementDTO.getTitle());
        advertisement.setContent(advertisementDTO.getContent());
        advertisement.setImageUrl(advertisementDTO.getImageUrl());
        return advertisement;
    }

}
