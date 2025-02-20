package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.StateDTO;
import com.michael_delivery.backend.service.StateService;
import com.michael_delivery.backend.util.ReferencedException;
import com.michael_delivery.backend.util.ReferencedWarning;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/states", produces = MediaType.APPLICATION_JSON_VALUE)
public class StateResource {

    private final StateService stateService;

    public StateResource(final StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping
    public ResponseEntity<List<StateDTO>> getAllStates() {
        return ResponseEntity.ok(stateService.findAll());
    }

    @GetMapping("/{stateId}")
    public ResponseEntity<StateDTO> getState(@PathVariable(name = "stateId") final Long stateId) {
        return ResponseEntity.ok(stateService.get(stateId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createState(@RequestBody @Valid final StateDTO stateDTO) {
        final Long createdStateId = stateService.create(stateDTO);
        return new ResponseEntity<>(createdStateId, HttpStatus.CREATED);
    }

    @PutMapping("/{stateId}")
    public ResponseEntity<Long> updateState(@PathVariable(name = "stateId") final Long stateId,
            @RequestBody @Valid final StateDTO stateDTO) {
        stateService.update(stateId, stateDTO);
        return ResponseEntity.ok(stateId);
    }

    @DeleteMapping("/{stateId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteState(@PathVariable(name = "stateId") final Long stateId) {
        final ReferencedWarning referencedWarning = stateService.getReferencedWarning(stateId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        stateService.delete(stateId);
        return ResponseEntity.noContent().build();
    }

}
