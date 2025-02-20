package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.Riders;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.SuspensionsDTO;
import com.michael_delivery.backend.repos.RidersRepository;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.service.SuspensionsService;
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

    @GetMapping
    public ResponseEntity<List<SuspensionsDTO>> getAllSuspensions() {
        return ResponseEntity.ok(suspensionsService.findAll());
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
