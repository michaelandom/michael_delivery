package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.model.QuestionOptions;
import com.michael_delivery.backend.dto.PageableBodyDTO;
import com.michael_delivery.backend.dto.QuestionOptionsDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.Questions;
import com.michael_delivery.backend.repository.QuestionsRepository;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/questionOptions", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuestionOptionsController {

    private final QuestionOptionsService questionOptionsService;
    private final QuestionsRepository questionsRepository;

    public QuestionOptionsController(final QuestionOptionsService questionOptionsService,
            final QuestionsRepository questionsRepository) {
        this.questionOptionsService = questionOptionsService;
        this.questionsRepository = questionsRepository;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGE_QUESTION_OPTIONS','VIEW_QUESTION_OPTION_MANY')")
    public ResponseEntity<List<QuestionOptionsDTO>> getAllQuestionOptions(
    ) {
        return ResponseEntity.ok(questionOptionsService.findAll());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('MANAGE_QUESTION_OPTIONS','VIEW_QUESTION_OPTION_MANY')")
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
    @PreAuthorize("hasAnyAuthority('MANAGE_QUESTION_OPTIONS','VIEW_QUESTION_OPTION_MANY')")
    public ResponseEntity<Page<QuestionOptionsDTO>> getAllQuestionOptions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "questionOptionId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(questionOptionsService.findAll(pageable.getPageable()));
    }

    @GetMapping("/{questionOptionId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_QUESTION_OPTIONS','VIEW_QUESTION_OPTION')")
    public ResponseEntity<QuestionOptionsDTO> getQuestionOptions(
            @PathVariable(name = "questionOptionId") final Long questionOptionId) {
        return ResponseEntity.ok(questionOptionsService.get(questionOptionId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @PreAuthorize("hasAnyAuthority('MANAGE_QUESTION_OPTIONS','UPDATE_QUESTION_OPTION_ONE')")
    public ResponseEntity<Long> createQuestionOptions(
            @RequestBody @Valid final QuestionOptionsDTO questionOptionsDTO) {
        final Long createdQuestionOptionId = questionOptionsService.create(questionOptionsDTO);
        return new ResponseEntity<>(createdQuestionOptionId, HttpStatus.CREATED);
    }

    @PutMapping("/{questionOptionId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_QUESTION_OPTIONS','UPDATE_QUESTION_OPTION_ONE')")
    public ResponseEntity<Long> updateQuestionOptions(
            @PathVariable(name = "questionOptionId") final Long questionOptionId,
            @RequestBody @Valid final QuestionOptionsDTO questionOptionsDTO) {
        questionOptionsService.update(questionOptionId, questionOptionsDTO);
        return ResponseEntity.ok(questionOptionId);
    }

    @DeleteMapping("/{questionOptionId}")
    @ApiResponse(responseCode = "204")
    @PreAuthorize("hasAnyAuthority('MANAGE_QUESTION_OPTIONS','DELETE_QUESTION_OPTION_ONE')")
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
    @PreAuthorize("hasAnyAuthority('MANAGE_QUESTION_OPTIONS','VIEW_QUESTION_OPTION_MANY')")
    public ResponseEntity<Map<Long, String>> getQuestionValues() {
        return ResponseEntity.ok(questionsRepository.findAll(Sort.by("questionId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Questions::getQuestionId, Questions::getQuestionText)));
    }

}
