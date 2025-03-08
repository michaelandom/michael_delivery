package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.domain.State;
import com.michael_delivery.backend.model.PageableBodyDTO;
import com.michael_delivery.backend.model.StateDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.StateDTO;
import com.michael_delivery.backend.service.StateService;
import com.michael_delivery.backend.util.ReferencedException;
import com.michael_delivery.backend.util.ReferencedWarning;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
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

    @GetMapping("/all")
    public ResponseEntity<List<StateDTO>> getAllState(
    ) {
        return ResponseEntity.ok(stateService.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<StateDTO>> searchState(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "stateId:asc") String[] sortBy,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<State> spec = new GenericSpecification<>();
        Specification<State> nameSpec = spec.equals("name", name);
        Specification<State> codeSpec = spec.equals("code", code);
        Specification<State> finalSpec = Specification.where(nameSpec).and(codeSpec);
        return ResponseEntity.ok(stateService.search(finalSpec,pageable.getPageable()));
    }
    @GetMapping
    public ResponseEntity<Page<StateDTO>> getAllState(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "stateId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(stateService.findAll(pageable.getPageable()));
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
