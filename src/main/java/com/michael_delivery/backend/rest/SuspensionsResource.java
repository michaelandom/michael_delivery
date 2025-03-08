package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.domain.Suspensions;
import com.michael_delivery.backend.enums.SuspensionReasonType;
import com.michael_delivery.backend.model.PageableBodyDTO;
import com.michael_delivery.backend.model.SuspensionsDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.Riders;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.SuspensionsDTO;
import com.michael_delivery.backend.repos.RidersRepository;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.service.SuspensionsService;
import com.michael_delivery.backend.util.CustomCollectors;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/suspensions", produces = MediaType.APPLICATION_JSON_VALUE)
public class SuspensionsResource {

    private final SuspensionsService suspensionsService;
    private final RidersRepository ridersRepository;
    private final UsersRepository usersRepository;

    public SuspensionsResource(final SuspensionsService suspensionsService,
            final RidersRepository ridersRepository, final UsersRepository usersRepository) {
        this.suspensionsService = suspensionsService;
        this.ridersRepository = ridersRepository;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<SuspensionsDTO>> getAllSuspensions(
    ) {
        return ResponseEntity.ok(suspensionsService.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<SuspensionsDTO>> searchSuspensions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "suspensionId:asc") String[] sortBy,
            @RequestParam(required = false) String reason,
            @RequestParam(required = false) boolean isSystemSuspenstion,
            @RequestParam(required = false) SuspensionReasonType reasonType,
            @RequestParam(required = false) boolean isActive,
            @RequestParam(required = false) OffsetDateTime startingFrom,
            @RequestParam(required = false) OffsetDateTime endingAt,
            @RequestParam(required = false) boolean startingFromIsAfter,
            @RequestParam(required = false) boolean endingAtIsAfter
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<Suspensions> spec = new GenericSpecification<>();
        Specification<Suspensions> reasonSpec = spec.equals("reason", reason);
        Specification<Suspensions> isSystemSuspenstionSpec = spec.equals("isSystemSuspenstion", isSystemSuspenstion);
        Specification<Suspensions> reasonTypeSpec = spec.equals("reasonType", reasonType);
        Specification<Suspensions> startingFromSpec =startingFromIsAfter ? spec.dateAfter("startingFrom", startingFrom): spec.dateBefore("startingFrom", startingFrom);
        Specification<Suspensions> endingAtSpec = endingAtIsAfter ? spec.dateBefore("endingAt", endingAt) : spec.dateAfter("endingAt", endingAt);
        Specification<Suspensions> isActiveSpec = spec.equals("isActive", isActive);
        Specification<Suspensions> finalSpec = Specification.where(reasonSpec).and(isSystemSuspenstionSpec)
                .and(reasonTypeSpec)
                .and(startingFromSpec)
                .and(endingAtSpec)
                .and(isActiveSpec)
                .and(isActiveSpec);
        return ResponseEntity.ok(suspensionsService.search(finalSpec,pageable.getPageable()));
    }
    @GetMapping
    public ResponseEntity<Page<SuspensionsDTO>> getAllSuspensions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "suspensionId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(suspensionsService.findAll(pageable.getPageable()));
    }

    @GetMapping("/{suspensionId}")
    public ResponseEntity<SuspensionsDTO> getSuspensions(
            @PathVariable(name = "suspensionId") final Long suspensionId) {
        return ResponseEntity.ok(suspensionsService.get(suspensionId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createSuspensions(
            @RequestBody @Valid final SuspensionsDTO suspensionsDTO) {
        final Long createdSuspensionId = suspensionsService.create(suspensionsDTO);
        return new ResponseEntity<>(createdSuspensionId, HttpStatus.CREATED);
    }

    @PutMapping("/{suspensionId}")
    public ResponseEntity<Long> updateSuspensions(
            @PathVariable(name = "suspensionId") final Long suspensionId,
            @RequestBody @Valid final SuspensionsDTO suspensionsDTO) {
        suspensionsService.update(suspensionId, suspensionsDTO);
        return ResponseEntity.ok(suspensionId);
    }

    @DeleteMapping("/{suspensionId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSuspensions(
            @PathVariable(name = "suspensionId") final Long suspensionId) {
        suspensionsService.delete(suspensionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/riderValues")
    public ResponseEntity<Map<Long, String>> getRiderValues() {
        return ResponseEntity.ok(ridersRepository.findAll(Sort.by("riderId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Riders::getRiderId, Riders::getEmergencyContactFirstName)));
    }

    @GetMapping("/suspenedByValues")
    public ResponseEntity<Map<Long, String>> getSuspenedByValues() {
        return ResponseEntity.ok(usersRepository.findAll(Sort.by("userId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Users::getUserId, Users::getUsername)));
    }

}
