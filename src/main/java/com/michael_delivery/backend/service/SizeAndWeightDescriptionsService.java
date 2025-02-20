package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.Item;
import com.michael_delivery.backend.domain.SizeAndWeightDescriptions;
import com.michael_delivery.backend.model.SizeAndWeightDescriptionsDTO;
import com.michael_delivery.backend.repos.ItemRepository;
import com.michael_delivery.backend.repos.SizeAndWeightDescriptionsRepository;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SizeAndWeightDescriptionsService {

    private final SizeAndWeightDescriptionsRepository sizeAndWeightDescriptionsRepository;
    private final ItemRepository itemRepository;

    public SizeAndWeightDescriptionsService(
            final SizeAndWeightDescriptionsRepository sizeAndWeightDescriptionsRepository,
            final ItemRepository itemRepository) {
        this.sizeAndWeightDescriptionsRepository = sizeAndWeightDescriptionsRepository;
        this.itemRepository = itemRepository;
    }

    public List<SizeAndWeightDescriptionsDTO> findAll() {
        final List<SizeAndWeightDescriptions> sizeAndWeightDescriptionses = sizeAndWeightDescriptionsRepository.findAll(Sort.by("sizeWeightDescriptionId"));
        return sizeAndWeightDescriptionses.stream()
                .map(sizeAndWeightDescriptions -> mapToDTO(sizeAndWeightDescriptions, new SizeAndWeightDescriptionsDTO()))
                .toList();
    }

    public SizeAndWeightDescriptionsDTO get(final Long sizeWeightDescriptionId) {
        return sizeAndWeightDescriptionsRepository.findById(sizeWeightDescriptionId)
                .map(sizeAndWeightDescriptions -> mapToDTO(sizeAndWeightDescriptions, new SizeAndWeightDescriptionsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final SizeAndWeightDescriptionsDTO sizeAndWeightDescriptionsDTO) {
        final SizeAndWeightDescriptions sizeAndWeightDescriptions = new SizeAndWeightDescriptions();
        mapToEntity(sizeAndWeightDescriptionsDTO, sizeAndWeightDescriptions);
        return sizeAndWeightDescriptionsRepository.save(sizeAndWeightDescriptions).getSizeWeightDescriptionId();
    }

    public void update(final Long sizeWeightDescriptionId,
            final SizeAndWeightDescriptionsDTO sizeAndWeightDescriptionsDTO) {
        final SizeAndWeightDescriptions sizeAndWeightDescriptions = sizeAndWeightDescriptionsRepository.findById(sizeWeightDescriptionId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(sizeAndWeightDescriptionsDTO, sizeAndWeightDescriptions);
        sizeAndWeightDescriptionsRepository.save(sizeAndWeightDescriptions);
    }

    public void delete(final Long sizeWeightDescriptionId) {
        sizeAndWeightDescriptionsRepository.deleteById(sizeWeightDescriptionId);
    }

    private SizeAndWeightDescriptionsDTO mapToDTO(
            final SizeAndWeightDescriptions sizeAndWeightDescriptions,
            final SizeAndWeightDescriptionsDTO sizeAndWeightDescriptionsDTO) {
        sizeAndWeightDescriptionsDTO.setSizeWeightDescriptionId(sizeAndWeightDescriptions.getSizeWeightDescriptionId());
        sizeAndWeightDescriptionsDTO.setSize(sizeAndWeightDescriptions.getSize());
        sizeAndWeightDescriptionsDTO.setSizeDescription(sizeAndWeightDescriptions.getSizeDescription());
        sizeAndWeightDescriptionsDTO.setWeight(sizeAndWeightDescriptions.getWeight());
        sizeAndWeightDescriptionsDTO.setIsLatest(sizeAndWeightDescriptions.getIsLatest());
//        sizeAndWeightDescriptionsDTO.setCreatedAt(sizeAndWeightDescriptions.getCreatedAt());
//        sizeAndWeightDescriptionsDTO.setUpdatedAt(sizeAndWeightDescriptions.getUpdatedAt());
//        sizeAndWeightDescriptionsDTO.setUniqueSizeCheck(sizeAndWeightDescriptions.getUniqueSizeCheck());
        sizeAndWeightDescriptionsDTO.setPrevious(sizeAndWeightDescriptions.getPrevious() == null ? null : sizeAndWeightDescriptions.getPrevious().getSizeWeightDescriptionId());
        return sizeAndWeightDescriptionsDTO;
    }

    private SizeAndWeightDescriptions mapToEntity(
            final SizeAndWeightDescriptionsDTO sizeAndWeightDescriptionsDTO,
            final SizeAndWeightDescriptions sizeAndWeightDescriptions) {
        sizeAndWeightDescriptions.setSize(sizeAndWeightDescriptionsDTO.getSize());
        sizeAndWeightDescriptions.setSizeDescription(sizeAndWeightDescriptionsDTO.getSizeDescription());
        sizeAndWeightDescriptions.setWeight(sizeAndWeightDescriptionsDTO.getWeight());
        sizeAndWeightDescriptions.setIsLatest(sizeAndWeightDescriptionsDTO.getIsLatest());
//        sizeAndWeightDescriptions.setUniqueSizeCheck(sizeAndWeightDescriptionsDTO.getUniqueSizeCheck());
        final SizeAndWeightDescriptions previous = sizeAndWeightDescriptionsDTO.getPrevious() == null ? null : sizeAndWeightDescriptionsRepository.findById(sizeAndWeightDescriptionsDTO.getPrevious())
                .orElseThrow(() -> new NotFoundException("previous not found"));
        sizeAndWeightDescriptions.setPrevious(previous);
        return sizeAndWeightDescriptions;
    }

//    public boolean uniqueSizeCheckExists(final String uniqueSizeCheck) {
//        return  sizeAndWeightDescriptionsRepository.existsByUniqueSizeCheckIgnoreCase(uniqueSizeCheck);
//    }

    public ReferencedWarning getReferencedWarning(final Long sizeWeightDescriptionId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final SizeAndWeightDescriptions sizeAndWeightDescriptions = sizeAndWeightDescriptionsRepository.findById(sizeWeightDescriptionId)
                .orElseThrow(NotFoundException::new);
        final SizeAndWeightDescriptions previousSizeAndWeightDescriptions = sizeAndWeightDescriptionsRepository.findFirstByPreviousAndSizeWeightDescriptionIdNot(sizeAndWeightDescriptions, sizeAndWeightDescriptions.getSizeWeightDescriptionId());
        if (previousSizeAndWeightDescriptions != null) {
            referencedWarning.setKey("sizeAndWeightDescriptions.sizeAndWeightDescriptions.previous.referenced");
            referencedWarning.addParam(previousSizeAndWeightDescriptions.getSizeWeightDescriptionId());
            return referencedWarning;
        }
        final Item sizeWeightDescriptionItem = itemRepository.findFirstBySizeWeightDescription(sizeAndWeightDescriptions);
        if (sizeWeightDescriptionItem != null) {
            referencedWarning.setKey("sizeAndWeightDescriptions.item.sizeWeightDescription.referenced");
            referencedWarning.addParam(sizeWeightDescriptionItem.getItemId());
            return referencedWarning;
        }
        return null;
    }

}
