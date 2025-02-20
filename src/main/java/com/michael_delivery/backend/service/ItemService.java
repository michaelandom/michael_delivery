package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.Destination;
import com.michael_delivery.backend.domain.Item;
import com.michael_delivery.backend.domain.SizeAndWeightDescriptions;
import com.michael_delivery.backend.model.ItemDTO;
import com.michael_delivery.backend.repos.DestinationRepository;
import com.michael_delivery.backend.repos.ItemRepository;
import com.michael_delivery.backend.repos.SizeAndWeightDescriptionsRepository;
import com.michael_delivery.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final DestinationRepository destinationRepository;
    private final SizeAndWeightDescriptionsRepository sizeAndWeightDescriptionsRepository;

    public ItemService(final ItemRepository itemRepository,
            final DestinationRepository destinationRepository,
            final SizeAndWeightDescriptionsRepository sizeAndWeightDescriptionsRepository) {
        this.itemRepository = itemRepository;
        this.destinationRepository = destinationRepository;
        this.sizeAndWeightDescriptionsRepository = sizeAndWeightDescriptionsRepository;
    }

    public List<ItemDTO> findAll() {
        final List<Item> items = itemRepository.findAll(Sort.by("itemId"));
        return items.stream()
                .map(item -> mapToDTO(item, new ItemDTO()))
                .toList();
    }

    public ItemDTO get(final Long itemId) {
        return itemRepository.findById(itemId)
                .map(item -> mapToDTO(item, new ItemDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ItemDTO itemDTO) {
        final Item item = new Item();
        mapToEntity(itemDTO, item);
        return itemRepository.save(item).getItemId();
    }

    public void update(final Long itemId, final ItemDTO itemDTO) {
        final Item item = itemRepository.findById(itemId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(itemDTO, item);
        itemRepository.save(item);
    }

    public void delete(final Long itemId) {
        itemRepository.deleteById(itemId);
    }

    private ItemDTO mapToDTO(final Item item, final ItemDTO itemDTO) {
        itemDTO.setItemId(item.getItemId());
        itemDTO.setName(item.getName());
        itemDTO.setItemClassification(item.getItemClassification());
        itemDTO.setPhotoUrls(item.getPhotoUrls());
        itemDTO.setDestination(item.getDestination() == null ? null : item.getDestination().getDestinationId());
        itemDTO.setSizeWeightDescription(item.getSizeWeightDescription() == null ? null : item.getSizeWeightDescription().getSizeWeightDescriptionId());
        return itemDTO;
    }

    private Item mapToEntity(final ItemDTO itemDTO, final Item item) {
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

}
