package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.model.Questions;
import com.michael_delivery.backend.dto.PageableBodyDTO;
import com.michael_delivery.backend.dto.QuestionsDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.service.QuestionsService;
import com.michael_delivery.backend.util.ReferencedException;
import com.michael_delivery.backend.util.ReferencedWarning;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/questions", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuestionsController {

    private final QuestionsService questionsService;

    public QuestionsController(final QuestionsService questionsService) {
        this.questionsService = questionsService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGE_QUESTIONS','VIEW_QUESTION_MANY')")
    public ResponseEntity<List<QuestionsDTO>> getAllQuestions(
    ) {
        return ResponseEntity.ok(questionsService.findAll());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('MANAGE_QUESTIONS','VIEW_QUESTION_MANY')")
    public ResponseEntity<Page<QuestionsDTO>> searchQuestions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "questionId:asc") String[] sortBy,
            @RequestParam(required = false) String questionText,
            @RequestParam(required = false) String description

    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<Questions> spec = new GenericSpecification<>();
        Specification<Questions> questionTextSpec = spec.contains("questionText", questionText);
        Specification<Questions> descriptionSpec = spec.equals("description", description);
        Specification<Questions> finalSpec = Specification.where(questionTextSpec)
                .and(descriptionSpec);
        return ResponseEntity.ok(questionsService.search(finalSpec,pageable.getPageable()));
    }
    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_QUESTIONS','VIEW_QUESTION_MANY')")
    public ResponseEntity<Page<QuestionsDTO>> getAllQuestions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "questionId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(questionsService.findAll(pageable.getPageable()));
    }

    @GetMapping("/{questionId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_QUESTIONS','VIEW_QUESTION')")
    public ResponseEntity<QuestionsDTO> getQuestions(
            @PathVariable(name = "questionId") final Long questionId) {
        return ResponseEntity.ok(questionsService.get(questionId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @PreAuthorize("hasAnyAuthority('MANAGE_QUESTIONS','UPDATE_QUESTION_ONE')")
    public ResponseEntity<Long> createQuestions(
            @RequestBody @Valid final QuestionsDTO questionsDTO) {
        final Long createdQuestionId = questionsService.create(questionsDTO);
        return new ResponseEntity<>(createdQuestionId, HttpStatus.CREATED);
    }

    @PutMapping("/{questionId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_QUESTIONS','UPDATE_QUESTION_ONE')")
    public ResponseEntity<Long> updateQuestions(
            @PathVariable(name = "questionId") final Long questionId,
            @RequestBody @Valid final QuestionsDTO questionsDTO) {
        questionsService.update(questionId, questionsDTO);
        return ResponseEntity.ok(questionId);
    }

    @DeleteMapping("/{questionId}")
    @ApiResponse(responseCode = "204")
    @PreAuthorize("hasAnyAuthority('MANAGE_QUESTIONS','DELETE_QUESTION_ONE')")
    public ResponseEntity<Void> deleteQuestions(
            @PathVariable(name = "questionId") final Long questionId) {
        final ReferencedWarning referencedWarning = questionsService.getReferencedWarning(questionId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        questionsService.delete(questionId);
        return ResponseEntity.noContent().build();
    }

}
