package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.model.Faq;
import com.michael_delivery.backend.dto.FaqDTO;
import com.michael_delivery.backend.dto.PageableBodyDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.service.FaqService;
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
@RequestMapping(value = "/api/faq", produces = MediaType.APPLICATION_JSON_VALUE)
public class FaqController {

    private final FaqService faqService;

    public FaqController(final FaqService faqService) {
        this.faqService = faqService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGE_FAQ','VIEW_FAQ_MANY')")
    public ResponseEntity<List<FaqDTO>> getAllFaq(
    ) {
        return ResponseEntity.ok(faqService.findAll());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('MANAGE_FAQ','VIEW_FAQ_MANY')")
    public ResponseEntity<Page<FaqDTO>> searchFaq(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id:asc") String[] sortBy,
            @RequestParam(required = false) String question,
            @RequestParam(required = false) String answer,
            @RequestParam(required = false) boolean isForRider
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<Faq> spec = new GenericSpecification<>();
        Specification<Faq> questionSpec = spec.contains("question", question);
        Specification<Faq> answerSpec = spec.contains("answer", answer);
        Specification<Faq> isForRiderSpec = spec.equals("isForRider", isForRider);
        Specification<Faq> finalSpec = Specification.where(questionSpec).and(answerSpec).and(isForRiderSpec);
        return ResponseEntity.ok(faqService.search(finalSpec,pageable.getPageable()));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_FAQ','VIEW_FAQ_MANY')")
    public ResponseEntity<Page<FaqDTO>> getAllFaq(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(faqService.findAll(pageable.getPageable()));
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MANAGE_FAQ','VIEW_FAQ')")
    public ResponseEntity<FaqDTO> getFaq(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(faqService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @PreAuthorize("hasAnyAuthority('MANAGE_FAQ','UPDATE_FAQ_MANY')")
    public ResponseEntity<Long> createFaq(@RequestBody @Valid final FaqDTO faqDTO) {
        final Long createdId = faqService.create(faqDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MANAGE_FAQ','UPDATE_FAQ_MANY')")
    public ResponseEntity<Long> updateFaq(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final FaqDTO faqDTO) {
        faqService.update(id, faqDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    @PreAuthorize("hasAnyAuthority('MANAGE_FAQ','DELETE_FAQ_ONE')")
    public ResponseEntity<Void> deleteFaq(@PathVariable(name = "id") final Long id) {
        faqService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
