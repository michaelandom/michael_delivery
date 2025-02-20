package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.QuestionsDTO;
import com.michael_delivery.backend.service.QuestionsService;
import com.michael_delivery.backend.util.ReferencedException;
import com.michael_delivery.backend.util.ReferencedWarning;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/questions", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuestionsResource {

    private final QuestionsService questionsService;

    public QuestionsResource(final QuestionsService questionsService) {
        this.questionsService = questionsService;
    }

    @GetMapping
    public ResponseEntity<List<QuestionsDTO>> getAllQuestions() {
        return ResponseEntity.ok(questionsService.findAll());
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionsDTO> getQuestions(
            @PathVariable(name = "questionId") final Long questionId) {
        return ResponseEntity.ok(questionsService.get(questionId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createQuestions(
            @RequestBody @Valid final QuestionsDTO questionsDTO) {
        final Long createdQuestionId = questionsService.create(questionsDTO);
        return new ResponseEntity<>(createdQuestionId, HttpStatus.CREATED);
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<Long> updateQuestions(
            @PathVariable(name = "questionId") final Long questionId,
            @RequestBody @Valid final QuestionsDTO questionsDTO) {
        questionsService.update(questionId, questionsDTO);
        return ResponseEntity.ok(questionId);
    }

    @DeleteMapping("/{questionId}")
    @ApiResponse(responseCode = "204")
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
