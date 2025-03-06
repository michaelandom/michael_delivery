package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.Destination;
import com.michael_delivery.backend.domain.Groups;
import com.michael_delivery.backend.domain.Item;
import com.michael_delivery.backend.domain.SizeAndWeightDescriptions;
import com.michael_delivery.backend.model.GroupsDTO;
import com.michael_delivery.backend.model.ItemDTO;
import com.michael_delivery.backend.repos.DestinationRepository;
import com.michael_delivery.backend.repos.GroupsRepository;
import com.michael_delivery.backend.repos.ItemRepository;
import com.michael_delivery.backend.repos.SizeAndWeightDescriptionsRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ItemService extends BaseService<Item, ItemDTO,Long, ItemRepository> {

    private final ItemRepository itemRepository;
    private final DestinationRepository destinationRepository;
    private final SizeAndWeightDescriptionsRepository sizeAndWeightDescriptionsRepository;

    public ItemService(final ItemRepository itemRepository,
            final DestinationRepository destinationRepository,
            final SizeAndWeightDescriptionsRepository sizeAndWeightDescriptionsRepository) {
        super(itemRepository,"itemId");
        this.itemRepository = itemRepository;
        this.destinationRepository = destinationRepository;
        this.sizeAndWeightDescriptionsRepository = sizeAndWeightDescriptionsRepository;
    }


    protected ItemDTO mapToDTO(final Item item, final ItemDTO itemDTO) {
        itemDTO.setItemId(item.getItemId());
        itemDTO.setName(item.getName());
        itemDTO.setItemClassification(item.getItemClassification());
        itemDTO.setPhotoUrls(item.getPhotoUrls());
        itemDTO.setDestination(item.getDestination() == null ? null : item.getDestination().getDestinationId());
        itemDTO.setSizeWeightDescription(item.getSizeWeightDescription() == null ? null : item.getSizeWeightDescription().getSizeWeightDescriptionId());
        return itemDTO;
    }

    protected Item mapToEntity(final ItemDTO itemDTO, final Item item) {
        item.setName(itemDTO.getName());
        item.setItemClassification(itemDTO.getItemClassification());
        item.setPhotoUrls(itemDTO.getPhotoUrls());
        final Destination destination = itemDTO.getDestination() == null ? null : destinationRepository.findById(itemDTO.getDestination())
                .orElseThrow(() -> new NotFoundException("destination not found"));
        item.setDestination(destination);
        final SizeAndWeightDescriptions sizeWeightDescription = itemDTO.getSizeWeightDescription() == null ? null : sizeAndWeightDescriptionsRepository.findById(itemDTO.getSizeWeightDescription())
                .orElseThrow(() -> new NotFoundException("sizeWeightDescription not found"));
        item.setSizeWeightDescription(sizeWeightDescription);
        return item;
    }

    @Override
    protected ItemDTO createDTO() {
        return new ItemDTO();
    }

    @Override
    protected Item createEntity() {
        return new Item();
    }

}
