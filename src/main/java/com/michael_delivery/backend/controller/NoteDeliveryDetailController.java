package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.model.NoteDeliveryDetail;
import com.michael_delivery.backend.enums.PickupTimeType;
import com.michael_delivery.backend.dto.NoteDeliveryDetailDTO;
import com.michael_delivery.backend.dto.PageableBodyDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.DeliveryDetail;
import com.michael_delivery.backend.repository.DeliveryDetailRepository;
import com.michael_delivery.backend.service.NoteDeliveryDetailService;
import com.michael_delivery.backend.util.CustomCollectors;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/noteDeliveryDetails", produces = MediaType.APPLICATION_JSON_VALUE)
public class NoteDeliveryDetailController {

    private final NoteDeliveryDetailService noteDeliveryDetailService;
    private final DeliveryDetailRepository deliveryDetailRepository;

    public NoteDeliveryDetailController(final NoteDeliveryDetailService noteDeliveryDetailService,
            final DeliveryDetailRepository deliveryDetailRepository) {
        this.noteDeliveryDetailService = noteDeliveryDetailService;
        this.deliveryDetailRepository = deliveryDetailRepository;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGE_NOTE_DELIVERY_DETAILS','VIEW_NOTE_DELIVERY_DETAIL_MANY')")
    public ResponseEntity<List<NoteDeliveryDetailDTO>> getAllNoteDeliveryDetail(
    ) {
        return ResponseEntity.ok(noteDeliveryDetailService.findAll());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('MANAGE_NOTE_DELIVERY_DETAILS','VIEW_NOTE_DELIVERY_DETAIL_MANY')")
    public ResponseEntity<Page<NoteDeliveryDetailDTO>> searchNoteDeliveryDetail(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "noteDeliveryDetailId:asc") String[] sortBy,
            @RequestParam(required = false) String note
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<NoteDeliveryDetail> spec = new GenericSpecification<>();
        Specification<NoteDeliveryDetail> noteSpec = spec.contains("note", note);
        Specification<NoteDeliveryDetail> finalSpec = Specification.where(noteSpec);
        return ResponseEntity.ok(noteDeliveryDetailService.search(finalSpec,pageable.getPageable()));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_NOTE_DELIVERY_DETAILS','VIEW_NOTE_DELIVERY_DETAIL_MANY')")
    public ResponseEntity<Page<NoteDeliveryDetailDTO>> getAllNoteDeliveryDetail(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "noteDeliveryDetailId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(noteDeliveryDetailService.findAll(pageable.getPageable()));
    }


    @GetMapping("/{noteDeliveryDetailId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_NOTE_DELIVERY_DETAILS','VIEW_NOTE_DELIVERY_DETAIL')")
    public ResponseEntity<NoteDeliveryDetailDTO> getNoteDeliveryDetail(
            @PathVariable(name = "noteDeliveryDetailId") final Long noteDeliveryDetailId) {
        return ResponseEntity.ok(noteDeliveryDetailService.get(noteDeliveryDetailId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @PreAuthorize("hasAnyAuthority('MANAGE_NOTE_DELIVERY_DETAILS','UPDATE_NOTE_DELIVERY_DETAIL_ONE')")
    public ResponseEntity<Long> createNoteDeliveryDetail(
            @RequestBody @Valid final NoteDeliveryDetailDTO noteDeliveryDetailDTO) {
        final Long createdNoteDeliveryDetailId = noteDeliveryDetailService.create(noteDeliveryDetailDTO);
        return new ResponseEntity<>(createdNoteDeliveryDetailId, HttpStatus.CREATED);
    }

    @PutMapping("/{noteDeliveryDetailId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_NOTE_DELIVERY_DETAILS','UPDATE_NOTE_DELIVERY_DETAIL_ONE')")
    public ResponseEntity<Long> updateNoteDeliveryDetail(
            @PathVariable(name = "noteDeliveryDetailId") final Long noteDeliveryDetailId,
            @RequestBody @Valid final NoteDeliveryDetailDTO noteDeliveryDetailDTO) {
        noteDeliveryDetailService.update(noteDeliveryDetailId, noteDeliveryDetailDTO);
        return ResponseEntity.ok(noteDeliveryDetailId);
    }

    @DeleteMapping("/{noteDeliveryDetailId}")
    @ApiResponse(responseCode = "204")
    @PreAuthorize("hasAnyAuthority('MANAGE_NOTE_DELIVERY_DETAILS','DELETE_NOTE_DELIVERY_DETAIL_ONE')")
    public ResponseEntity<Void> deleteNoteDeliveryDetail(
            @PathVariable(name = "noteDeliveryDetailId") final Long noteDeliveryDetailId) {
        noteDeliveryDetailService.delete(noteDeliveryDetailId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/deliveryDetailValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_NOTE_DELIVERY_DETAILS','VIEW_NOTE_DELIVERY_DETAIL_MANY')")
    public ResponseEntity<Map<Long, PickupTimeType>> getDeliveryDetailValues() {
        return ResponseEntity.ok(deliveryDetailRepository.findAll(Sort.by("deliveryDetailId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(DeliveryDetail::getDeliveryDetailId, DeliveryDetail::getPickupTime)));
    }

}
