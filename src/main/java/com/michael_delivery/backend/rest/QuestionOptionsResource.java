package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.domain.QuestionOptions;
import com.michael_delivery.backend.enums.PickupTimeType;
import com.michael_delivery.backend.enums.VehicleType;
import com.michael_delivery.backend.model.PageableBodyDTO;
import com.michael_delivery.backend.model.PickupTimeBasicPricesDTO;
import com.michael_delivery.backend.model.QuestionOptionsDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.Questions;
import com.michael_delivery.backend.model.QuestionOptionsDTO;
import com.michael_delivery.backend.repos.QuestionsRepository;
import com.michael_delivery.backend.service.QuestionOptionsService;
import com.michael_delivery.backend.util.CustomCollectors;
import com.michael_delivery.backend.util.ReferencedException;
import com.michael_delivery.backend.util.ReferencedWarning;
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
@RequestMapping(value = "/api/questionOptions", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuestionOptionsResource {

    private final QuestionOptionsService questionOptionsService;
    private final QuestionsRepository questionsRepository;

    public QuestionOptionsResource(final QuestionOptionsService questionOptionsService,
            final QuestionsRepository questionsRepository) {
        this.questionOptionsService = questionOptionsService;
        this.questionsRepository = questionsRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<QuestionOptionsDTO>> getAllQuestionOptions(
    ) {
        return ResponseEntity.ok(questionOptionsService.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<QuestionOptionsDTO>> searchQuestionOptions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "questionOptionId:asc") String[] sortBy,
            @RequestParam(required = false) String questionOption,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Boolean isCorrect


    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<QuestionOptions> spec = new GenericSpecification<>();
        Specification<QuestionOptions> questionOptionSpec = spec.contains("questionOption", questionOption);
        Specification<QuestionOptions> descriptionSpec = spec.equals("description", description);
        Specification<QuestionOptions> isCorrectSpec = spec.equals("isCorrect", isCorrect);
        Specification<QuestionOptions> finalSpec = Specification.where(questionOptionSpec)
                .and(descriptionSpec)
                .and(isCorrectSpec);
        return ResponseEntity.ok(questionOptionsService.search(finalSpec,pageable.getPageable()));
    }
    @GetMapping
    public ResponseEntity<Page<QuestionOptionsDTO>> getAllQuestionOptions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "questionOptionId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(questionOptionsService.findAll(pageable.getPageable()));
    }

    @GetMapping("/{questionOptionId}")
    public ResponseEntity<QuestionOptionsDTO> getQuestionOptions(
            @PathVariable(name = "questionOptionId") final Long questionOptionId) {
        return ResponseEntity.ok(questionOptionsService.get(questionOptionId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createQuestionOptions(
            @RequestBody @Valid final QuestionOptionsDTO questionOptionsDTO) {
        final Long createdQuestionOptionId = questionOptionsService.create(questionOptionsDTO);
        return new ResponseEntity<>(createdQuestionOptionId, HttpStatus.CREATED);
    }

    @PutMapping("/{questionOptionId}")
    public ResponseEntity<Long> updateQuestionOptions(
            @PathVariable(name = "questionOptionId") final Long questionOptionId,
            @RequestBody @Valid final QuestionOptionsDTO questionOptionsDTO) {
        questionOptionsService.update(questionOptionId, questionOptionsDTO);
        return ResponseEntity.ok(questionOptionId);
    }

    @DeleteMapping("/{questionOptionId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteQuestionOptions(
            @PathVariable(name = "questionOptionId") final Long questionOptionId) {
        final ReferencedWarning referencedWarning = questionOptionsService.getReferencedWarning(questionOptionId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        questionOptionsService.delete(questionOptionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/questionValues")
    public ResponseEntity<Map<Long, String>> getQuestionValues() {
        return ResponseEntity.ok(questionsRepository.findAll(Sort.by("questionId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Questions::getQuestionId, Questions::getQuestionText)));
    }

}
