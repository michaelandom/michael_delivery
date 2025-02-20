package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.enums.PickupTimeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.DeliveryDetail;
import com.michael_delivery.backend.model.NoteDeliveryDetailDTO;
import com.michael_delivery.backend.repos.DeliveryDetailRepository;
import com.michael_delivery.backend.service.NoteDeliveryDetailService;
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
@RequestMapping(value = "/api/noteDeliveryDetails", produces = MediaType.APPLICATION_JSON_VALUE)
public class NoteDeliveryDetailResource {

    private final NoteDeliveryDetailService noteDeliveryDetailService;
    private final DeliveryDetailRepository deliveryDetailRepository;

    public NoteDeliveryDetailResource(final NoteDeliveryDetailService noteDeliveryDetailService,
            final DeliveryDetailRepository deliveryDetailRepository) {
        this.noteDeliveryDetailService = noteDeliveryDetailService;
        this.deliveryDetailRepository = deliveryDetailRepository;
    }

    @GetMapping
    public ResponseEntity<List<NoteDeliveryDetailDTO>> getAllNoteDeliveryDetails() {
        return ResponseEntity.ok(noteDeliveryDetailService.findAll());
    }

    @GetMapping("/{noteDeliveryDetailId}")
    public ResponseEntity<NoteDeliveryDetailDTO> getNoteDeliveryDetail(
            @PathVariable(name = "noteDeliveryDetailId") final Long noteDeliveryDetailId) {
        return ResponseEntity.ok(noteDeliveryDetailService.get(noteDeliveryDetailId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createNoteDeliveryDetail(
            @RequestBody @Valid final NoteDeliveryDetailDTO noteDeliveryDetailDTO) {
        final Long createdNoteDeliveryDetailId = noteDeliveryDetailService.create(noteDeliveryDetailDTO);
        return new ResponseEntity<>(createdNoteDeliveryDetailId, HttpStatus.CREATED);
    }

    @PutMapping("/{noteDeliveryDetailId}")
    public ResponseEntity<Long> updateNoteDeliveryDetail(
            @PathVariable(name = "noteDeliveryDetailId") final Long noteDeliveryDetailId,
            @RequestBody @Valid final NoteDeliveryDetailDTO noteDeliveryDetailDTO) {
        noteDeliveryDetailService.update(noteDeliveryDetailId, noteDeliveryDetailDTO);
        return ResponseEntity.ok(noteDeliveryDetailId);
    }

    @DeleteMapping("/{noteDeliveryDetailId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteNoteDeliveryDetail(
            @PathVariable(name = "noteDeliveryDetailId") final Long noteDeliveryDetailId) {
        noteDeliveryDetailService.delete(noteDeliveryDetailId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/deliveryDetailValues")
    public ResponseEntity<Map<Long, PickupTimeType>> getDeliveryDetailValues() {
        return ResponseEntity.ok(deliveryDetailRepository.findAll(Sort.by("deliveryDetailId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(DeliveryDetail::getDeliveryDetailId, DeliveryDetail::getPickupTime)));
    }

}
