package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.model.Penalities;
import com.michael_delivery.backend.dto.PageableBodyDTO;
import com.michael_delivery.backend.dto.PenalitiesDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.Riders;
import com.michael_delivery.backend.model.Users;
import com.michael_delivery.backend.repository.RidersRepository;
import com.michael_delivery.backend.repository.UsersRepository;
import com.michael_delivery.backend.service.PenalitiesService;
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
@RequestMapping(value = "/api/penalities", produces = MediaType.APPLICATION_JSON_VALUE)
public class PenalitiesController {

    private final PenalitiesService penalitiesService;
    private final RidersRepository ridersRepository;
    private final UsersRepository usersRepository;

    public PenalitiesController(final PenalitiesService penalitiesService,
            final RidersRepository ridersRepository, final UsersRepository usersRepository) {
        this.penalitiesService = penalitiesService;
        this.ridersRepository = ridersRepository;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGE_PENALTIES','VIEW_PENALTY_MANY')")
    public ResponseEntity<List<PenalitiesDTO>> getAllPenalities(
    ) {
        return ResponseEntity.ok(penalitiesService.findAll());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('MANAGE_PENALTIES','VIEW_PENALTY_MANY')")
    public ResponseEntity<Page<PenalitiesDTO>> searchPenalities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "penalitieId:asc") String[] sortBy,
            @RequestParam(required = false) String reason,
            @RequestParam(required = false) String deductedAmount,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String orderNumber,
            @RequestParam(required = false) boolean isActive,
            @RequestParam(required = false) boolean isWarning,
            @RequestParam(required = false) boolean deductedAmountIsGreaterThan
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<Penalities> spec = new GenericSpecification<>();
        Specification<Penalities> reasonSpec = spec.contains("reason", reason);
        Specification<Penalities> deductedAmountSpec = deductedAmountIsGreaterThan? spec.greaterThan("deductedAmount", deductedAmount) : spec.lessThan("deductedAmount", deductedAmount);
        Specification<Penalities> descriptionSpec = spec.contains("description", description);
        Specification<Penalities> orderNumberSpec = spec.equals("orderNumber", orderNumber);
        Specification<Penalities> isWarningSpec = spec.equals("isWarning", isWarning);
        Specification<Penalities> isActiveSpec = spec.equals("isActive", isActive);
        Specification<Penalities> finalSpec = Specification.where(reasonSpec)
                .and(deductedAmountSpec)
                .and(descriptionSpec)
                .and(orderNumberSpec)
                .and(isActiveSpec)
                .and(isWarningSpec);
        return ResponseEntity.ok(penalitiesService.search(finalSpec,pageable.getPageable()));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_PENALTIES','VIEW_PENALTY_MANY')")
    public ResponseEntity<Page<PenalitiesDTO>> getAllPenalities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "penalitieId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(penalitiesService.findAll(pageable.getPageable()));
    }
    

    @GetMapping("/{penalitieId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_PENALTIES','VIEW_PENALTY')")
    public ResponseEntity<PenalitiesDTO> getPenalities(
            @PathVariable(name = "penalitieId") final Long penalitieId) {
        return ResponseEntity.ok(penalitiesService.get(penalitieId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @PreAuthorize("hasAnyAuthority('MANAGE_PENALTIES','UPDATE_PENALTY_MANY')")
    public ResponseEntity<Long> createPenalities(
            @RequestBody @Valid final PenalitiesDTO penalitiesDTO) {
        final Long createdPenalitieId = penalitiesService.create(penalitiesDTO);
        return new ResponseEntity<>(createdPenalitieId, HttpStatus.CREATED);
    }

    @PutMapping("/{penalitieId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_PENALTIES','UPDATE_PENALTY_MANY')")
    public ResponseEntity<Long> updatePenalities(
            @PathVariable(name = "penalitieId") final Long penalitieId,
            @RequestBody @Valid final PenalitiesDTO penalitiesDTO) {
        penalitiesService.update(penalitieId, penalitiesDTO);
        return ResponseEntity.ok(penalitieId);
    }

    @DeleteMapping("/{penalitieId}")
    @ApiResponse(responseCode = "204")
    @PreAuthorize("hasAnyAuthority('MANAGE_PENALTIES','DELETE_PENALTY_ONE')")
    public ResponseEntity<Void> deletePenalities(
            @PathVariable(name = "penalitieId") final Long penalitieId) {
        penalitiesService.delete(penalitieId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/riderValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_PENALTIES','VIEW_PENALTY_MANY')")
    public ResponseEntity<Map<Long, String>> getRiderValues() {
        return ResponseEntity.ok(ridersRepository.findAll(Sort.by("riderId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Riders::getRiderId, Riders::getEmergencyContactFirstName)));
    }

    @GetMapping("/adminValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_PENALTIES','VIEW_PENALTY_MANY')")
    public ResponseEntity<Map<Long, String>> getAdminValues() {
        return ResponseEntity.ok(usersRepository.findAll(Sort.by("userId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Users::getUserId, Users::getUsername)));
    }

}
