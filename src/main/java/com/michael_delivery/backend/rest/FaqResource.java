package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.domain.Faq;
import com.michael_delivery.backend.enums.PaymentStatusType;
import com.michael_delivery.backend.model.FaqDTO;
import com.michael_delivery.backend.model.PageableBodyDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.FaqDTO;
import com.michael_delivery.backend.service.FaqService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping(value = "/api/faq", produces = MediaType.APPLICATION_JSON_VALUE)
public class FaqResource {

    private final FaqService faqService;

    public FaqResource(final FaqService faqService) {
        this.faqService = faqService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<FaqDTO>> getAllFaq(
    ) {
        return ResponseEntity.ok(faqService.findAll());
    }

    @GetMapping("/search")
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
    public ResponseEntity<Page<FaqDTO>> getAllFaq(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(faqService.findAll(pageable.getPageable()));
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
