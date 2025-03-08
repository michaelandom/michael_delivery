package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.domain.Item;
import com.michael_delivery.backend.enums.ItemClassificationType;
import com.michael_delivery.backend.enums.SizeAndWeightType;
import com.michael_delivery.backend.model.GroupsDTO;
import com.michael_delivery.backend.model.ItemDTO;
import com.michael_delivery.backend.model.PageableBodyDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.Destination;
import com.michael_delivery.backend.domain.SizeAndWeightDescriptions;
import com.michael_delivery.backend.model.ItemDTO;
import com.michael_delivery.backend.repos.DestinationRepository;
import com.michael_delivery.backend.repos.SizeAndWeightDescriptionsRepository;
import com.michael_delivery.backend.service.ItemService;
import com.michael_delivery.backend.util.CustomCollectors;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/items", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemResource {

    private final ItemService itemService;
    private final DestinationRepository destinationRepository;
    private final SizeAndWeightDescriptionsRepository sizeAndWeightDescriptionsRepository;

    public ItemResource(final ItemService itemService,
            final DestinationRepository destinationRepository,
            final SizeAndWeightDescriptionsRepository sizeAndWeightDescriptionsRepository) {
        this.itemService = itemService;
        this.destinationRepository = destinationRepository;
        this.sizeAndWeightDescriptionsRepository = sizeAndWeightDescriptionsRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItemDTO>> getAllItem(
    ) {
        return ResponseEntity.ok(itemService.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ItemDTO>> searchItem(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "itemId:asc") String[] sortBy,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<Item> spec = new GenericSpecification<>();
        Specification<Item> nameSpec = spec.contains("name", name);
        Specification<Item> descriptionSpec = spec.contains("description", description);
        Specification<Item> finalSpec = Specification.where(nameSpec).and(descriptionSpec);
        return ResponseEntity.ok(itemService.search(finalSpec,pageable.getPageable()));
    }

    @GetMapping
    public ResponseEntity<Page<ItemDTO>> getAllItem(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "itemId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(itemService.findAll(pageable.getPageable()));
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDTO> getItem(@PathVariable(name = "itemId") final Long itemId) {
        return ResponseEntity.ok(itemService.get(itemId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createItem(@RequestBody @Valid final ItemDTO itemDTO) {
        final Long createdItemId = itemService.create(itemDTO);
        return new ResponseEntity<>(createdItemId, HttpStatus.CREATED);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<Long> updateItem(@PathVariable(name = "itemId") final Long itemId,
            @RequestBody @Valid final ItemDTO itemDTO) {
        itemService.update(itemId, itemDTO);
        return ResponseEntity.ok(itemId);
    }

    @DeleteMapping("/{itemId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteItem(@PathVariable(name = "itemId") final Long itemId) {
        itemService.delete(itemId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/destinationValues")
    public ResponseEntity<Map<Long, Long>> getDestinationValues() {
        return ResponseEntity.ok(destinationRepository.findAll(Sort.by("destinationId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Destination::getDestinationId, Destination::getDestinationId)));
    }

    @GetMapping("/sizeWeightDescriptionValues")
    public ResponseEntity<Map<Long, SizeAndWeightType>> getSizeWeightDescriptionValues() {
        return ResponseEntity.ok(sizeAndWeightDescriptionsRepository.findAll(Sort.by("sizeWeightDescriptionId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(SizeAndWeightDescriptions::getSizeWeightDescriptionId, SizeAndWeightDescriptions::getSize)));
    }

}
