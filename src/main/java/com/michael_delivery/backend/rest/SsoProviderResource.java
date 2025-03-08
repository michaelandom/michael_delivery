package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.domain.SsoProvider;
import com.michael_delivery.backend.enums.SizeAndWeightType;
import com.michael_delivery.backend.model.PageableBodyDTO;
import com.michael_delivery.backend.model.SsoProviderDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.SsoProviderDTO;
import com.michael_delivery.backend.service.SsoProviderService;
import com.michael_delivery.backend.util.ReferencedException;
import com.michael_delivery.backend.util.ReferencedWarning;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/ssoProviders", produces = MediaType.APPLICATION_JSON_VALUE)
public class SsoProviderResource {

    private final SsoProviderService ssoProviderService;

    public SsoProviderResource(final SsoProviderService ssoProviderService) {
        this.ssoProviderService = ssoProviderService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<SsoProviderDTO>> getAllSsoProvider(
    ) {
        return ResponseEntity.ok(ssoProviderService.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<SsoProviderDTO>> searchSsoProvider(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ssoProviderId:asc") String[] sortBy,
            @RequestParam(required = false) String ssoProvider
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<SsoProvider> spec = new GenericSpecification<>();
        Specification<SsoProvider> ssoProviderSpec = spec.equals("ssoProvider", ssoProvider);
        Specification<SsoProvider> finalSpec = Specification.where(ssoProviderSpec);
        return ResponseEntity.ok(ssoProviderService.search(finalSpec,pageable.getPageable()));
    }
    @GetMapping
    public ResponseEntity<Page<SsoProviderDTO>> getAllSsoProvider(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ssoProviderId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(ssoProviderService.findAll(pageable.getPageable()));
    }

    @GetMapping("/{ssoProviderId}")
    public ResponseEntity<SsoProviderDTO> getSsoProvider(
            @PathVariable(name = "ssoProviderId") final Long ssoProviderId) {
        return ResponseEntity.ok(ssoProviderService.get(ssoProviderId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createSsoProvider(
            @RequestBody @Valid final SsoProviderDTO ssoProviderDTO) {
        final Long createdSsoProviderId = ssoProviderService.create(ssoProviderDTO);
        return new ResponseEntity<>(createdSsoProviderId, HttpStatus.CREATED);
    }

    @PutMapping("/{ssoProviderId}")
    public ResponseEntity<Long> updateSsoProvider(
            @PathVariable(name = "ssoProviderId") final Long ssoProviderId,
            @RequestBody @Valid final SsoProviderDTO ssoProviderDTO) {
        ssoProviderService.update(ssoProviderId, ssoProviderDTO);
        return ResponseEntity.ok(ssoProviderId);
    }

    @DeleteMapping("/{ssoProviderId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSsoProvider(
            @PathVariable(name = "ssoProviderId") final Long ssoProviderId) {
        final ReferencedWarning referencedWarning = ssoProviderService.getReferencedWarning(ssoProviderId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        ssoProviderService.delete(ssoProviderId);
        return ResponseEntity.noContent().build();
    }

}
