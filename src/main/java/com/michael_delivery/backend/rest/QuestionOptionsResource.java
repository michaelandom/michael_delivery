package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.Questions;
import com.michael_delivery.backend.model.QuestionOptionsDTO;
import com.michael_delivery.backend.repos.QuestionsRepository;
import com.michael_delivery.backend.service.QuestionOptionsService;
import com.michael_delivery.backend.util.CustomCollectors;
import com.michael_delivery.backend.util.ReferencedException;
import com.michael_delivery.backend.util.ReferencedWarning;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/questionOptionss", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuestionOptionsResource {

    private final QuestionOptionsService questionOptionsService;
    private final QuestionsRepository questionsRepository;

    public QuestionOptionsResource(final QuestionOptionsService questionOptionsService,
            final QuestionsRepository questionsRepository) {
        this.questionOptionsService = questionOptionsService;
        this.questionsRepository = questionsRepository;
    }

    @GetMapping
    public ResponseEntity<List<QuestionOptionsDTO>> getAllQuestionOptionss() {
        return ResponseEntity.ok(questionOptionsService.findAll());
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
