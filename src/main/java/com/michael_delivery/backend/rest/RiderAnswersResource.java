package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.domain.RiderAnswers;
import com.michael_delivery.backend.enums.QuizKeyType;
import com.michael_delivery.backend.model.PageableBodyDTO;
import com.michael_delivery.backend.model.RiderAnswersDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.QuestionOptions;
import com.michael_delivery.backend.domain.Riders;
import com.michael_delivery.backend.model.RiderAnswersDTO;
import com.michael_delivery.backend.repos.QuestionOptionsRepository;
import com.michael_delivery.backend.repos.RidersRepository;
import com.michael_delivery.backend.service.RiderAnswersService;
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
@RequestMapping(value = "/api/riderAnswers", produces = MediaType.APPLICATION_JSON_VALUE)
public class RiderAnswersResource {

    private final RiderAnswersService riderAnswersService;
    private final RidersRepository ridersRepository;
    private final QuestionOptionsRepository questionOptionsRepository;

    public RiderAnswersResource(final RiderAnswersService riderAnswersService,
            final RidersRepository ridersRepository,
            final QuestionOptionsRepository questionOptionsRepository) {
        this.riderAnswersService = riderAnswersService;
        this.ridersRepository = ridersRepository;
        this.questionOptionsRepository = questionOptionsRepository;
    }
    @GetMapping("/all")
    public ResponseEntity<List<RiderAnswersDTO>> getAllRiderAnswers(
    ) {
        return ResponseEntity.ok(riderAnswersService.findAll());
    }
    @GetMapping
    public ResponseEntity<Page<RiderAnswersDTO>> getAllRiderAnswers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "riderAnswerId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(riderAnswersService.findAll(pageable.getPageable()));
    }

    @GetMapping("/{riderAnswerId}")
    public ResponseEntity<RiderAnswersDTO> getRiderAnswers(
            @PathVariable(name = "riderAnswerId") final Long riderAnswerId) {
        return ResponseEntity.ok(riderAnswersService.get(riderAnswerId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createRiderAnswers(
            @RequestBody @Valid final RiderAnswersDTO riderAnswersDTO) {
        final Long createdRiderAnswerId = riderAnswersService.create(riderAnswersDTO);
        return new ResponseEntity<>(createdRiderAnswerId, HttpStatus.CREATED);
    }

    @PutMapping("/{riderAnswerId}")
    public ResponseEntity<Long> updateRiderAnswers(
            @PathVariable(name = "riderAnswerId") final Long riderAnswerId,
            @RequestBody @Valid final RiderAnswersDTO riderAnswersDTO) {
        riderAnswersService.update(riderAnswerId, riderAnswersDTO);
        return ResponseEntity.ok(riderAnswerId);
    }

    @DeleteMapping("/{riderAnswerId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRiderAnswers(
            @PathVariable(name = "riderAnswerId") final Long riderAnswerId) {
        riderAnswersService.delete(riderAnswerId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/riderValues")
    public ResponseEntity<Map<Long, String>> getRiderValues() {
        return ResponseEntity.ok(ridersRepository.findAll(Sort.by("riderId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Riders::getRiderId, Riders::getEmergencyContactFirstName)));
    }

    @GetMapping("/optionValues")
    public ResponseEntity<Map<Long, String>> getOptionValues() {
        return ResponseEntity.ok(questionOptionsRepository.findAll(Sort.by("questionOptionId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(QuestionOptions::getQuestionOptionId, QuestionOptions::getQuestionOption)));
    }

}
