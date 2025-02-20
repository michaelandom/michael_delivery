package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.FaqDTO;
import com.michael_delivery.backend.service.FaqService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/faq", produces = MediaType.APPLICATION_JSON_VALUE)
public class FaqResource {

    private final FaqService faqService;

    public FaqResource(final FaqService faqService) {
        this.faqService = faqService;
    }

    @GetMapping
    public ResponseEntity<List<FaqDTO>> getAllFaqs() {
        return ResponseEntity.ok(faqService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FaqDTO> getFaq(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(faqService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createFaq(@RequestBody @Valid final FaqDTO faqDTO) {
        final Long createdId = faqService.create(faqDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateFaq(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final FaqDTO faqDTO) {
        faqService.update(id, faqDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteFaq(@PathVariable(name = "id") final Long id) {
        faqService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
