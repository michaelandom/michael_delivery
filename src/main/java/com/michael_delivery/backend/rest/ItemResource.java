package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.Destination;
import com.michael_delivery.backend.domain.SizeAndWeightDescriptions;
import com.michael_delivery.backend.model.ItemDTO;
import com.michael_delivery.backend.repos.DestinationRepository;
import com.michael_delivery.backend.repos.SizeAndWeightDescriptionsRepository;
import com.michael_delivery.backend.service.ItemService;
import com.michael_delivery.backend.util.CustomCollectors;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
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

    @GetMapping
    public ResponseEntity<List<ItemDTO>> getAllItems() {
        return ResponseEntity.ok(itemService.findAll());
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
    public ResponseEntity<Map<Long, String>> getSizeWeightDescriptionValues() {
        return ResponseEntity.ok(sizeAndWeightDescriptionsRepository.findAll(Sort.by("sizeWeightDescriptionId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(SizeAndWeightDescriptions::getSizeWeightDescriptionId, SizeAndWeightDescriptions::getSize)));
    }

}
