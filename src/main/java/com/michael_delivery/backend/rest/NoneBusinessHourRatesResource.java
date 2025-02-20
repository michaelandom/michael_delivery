package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.NoneBusinessHourRatesDTO;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.service.NoneBusinessHourRatesService;
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
@RequestMapping(value = "/api/noneBusinessHourRates", produces = MediaType.APPLICATION_JSON_VALUE)
public class NoneBusinessHourRatesResource {

    private final NoneBusinessHourRatesService noneBusinessHourRatesService;
    private final UsersRepository usersRepository;

    public NoneBusinessHourRatesResource(
            final NoneBusinessHourRatesService noneBusinessHourRatesService,
            final UsersRepository usersRepository) {
        this.noneBusinessHourRatesService = noneBusinessHourRatesService;
        this.usersRepository = usersRepository;
    }

    @GetMapping
    public ResponseEntity<List<NoneBusinessHourRatesDTO>> getAllNoneBusinessHourRatess() {
        return ResponseEntity.ok(noneBusinessHourRatesService.findAll());
    }

    @GetMapping("/{noneBusinessHourRateId}")
    public ResponseEntity<NoneBusinessHourRatesDTO> getNoneBusinessHourRates(
            @PathVariable(name = "noneBusinessHourRateId") final Long noneBusinessHourRateId) {
        return ResponseEntity.ok(noneBusinessHourRatesService.get(noneBusinessHourRateId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createNoneBusinessHourRates(
            @RequestBody @Valid final NoneBusinessHourRatesDTO noneBusinessHourRatesDTO) {
        final Long createdNoneBusinessHourRateId = noneBusinessHourRatesService.create(noneBusinessHourRatesDTO);
        return new ResponseEntity<>(createdNoneBusinessHourRateId, HttpStatus.CREATED);
    }

    @PutMapping("/{noneBusinessHourRateId}")
    public ResponseEntity<Long> updateNoneBusinessHourRates(
            @PathVariable(name = "noneBusinessHourRateId") final Long noneBusinessHourRateId,
            @RequestBody @Valid final NoneBusinessHourRatesDTO noneBusinessHourRatesDTO) {
        noneBusinessHourRatesService.update(noneBusinessHourRateId, noneBusinessHourRatesDTO);
        return ResponseEntity.ok(noneBusinessHourRateId);
    }

    @DeleteMapping("/{noneBusinessHourRateId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteNoneBusinessHourRates(
            @PathVariable(name = "noneBusinessHourRateId") final Long noneBusinessHourRateId) {
        noneBusinessHourRatesService.delete(noneBusinessHourRateId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/createdByValues")
    public ResponseEntity<Map<Long, String>> getCreatedByValues() {
        return ResponseEntity.ok(usersRepository.findAll(Sort.by("userId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Users::getUserId, Users::getUsername)));
    }

}
