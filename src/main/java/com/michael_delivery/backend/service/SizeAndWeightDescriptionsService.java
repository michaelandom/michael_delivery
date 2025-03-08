package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.CancellationRiderRequest;
import com.michael_delivery.backend.domain.Destination;
import com.michael_delivery.backend.domain.Item;
import com.michael_delivery.backend.domain.SizeAndWeightDescriptions;
import com.michael_delivery.backend.model.CancellationRiderRequestDTO;
import com.michael_delivery.backend.model.DestinationDTO;
import com.michael_delivery.backend.model.SizeAndWeightDescriptionsDTO;
import com.michael_delivery.backend.repos.CancellationRiderRequestRepository;
import com.michael_delivery.backend.repos.ItemRepository;
import com.michael_delivery.backend.repos.SizeAndWeightDescriptionsRepository;
import com.michael_delivery.backend.util.NotFoundException;
import com.michael_delivery.backend.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SizeAndWeightDescriptionsService extends BaseService<SizeAndWeightDescriptions, SizeAndWeightDescriptionsDTO,Long, SizeAndWeightDescriptionsRepository> {

    private final SizeAndWeightDescriptionsRepository sizeAndWeightDescriptionsRepository;
    private final ItemRepository itemRepository;

    public SizeAndWeightDescriptionsService(
            final SizeAndWeightDescriptionsRepository sizeAndWeightDescriptionsRepository,
            final ItemRepository itemRepository) {
        super(sizeAndWeightDescriptionsRepository,"sizeWeightDescriptionId");
        this.sizeAndWeightDescriptionsRepository = sizeAndWeightDescriptionsRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public Page<SizeAndWeightDescriptionsDTO> search(Specification<SizeAndWeightDescriptions> query, Pageable pageable) {
        return this.sizeAndWeightDescriptionsRepository.findAll(query, pageable);
    }

    @Override
    protected SizeAndWeightDescriptionsDTO mapToDTO(
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

    @Override
    protected SizeAndWeightDescriptions mapToEntity(
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

    @Override
    protected SizeAndWeightDescriptionsDTO createDTO() {
        return new SizeAndWeightDescriptionsDTO();
    }

    @Override
    protected SizeAndWeightDescriptions createEntity() {
        return new SizeAndWeightDescriptions();
    }


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
