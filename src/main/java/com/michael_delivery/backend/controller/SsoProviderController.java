package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.model.SsoProvider;
import com.michael_delivery.backend.dto.PageableBodyDTO;
import com.michael_delivery.backend.dto.SsoProviderDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.service.SsoProviderService;
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
@RequestMapping(value = "/api/ssoProviders", produces = MediaType.APPLICATION_JSON_VALUE)
public class SsoProviderController {

    private final SsoProviderService ssoProviderService;

    public SsoProviderController(final SsoProviderService ssoProviderService) {
        this.ssoProviderService = ssoProviderService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGE_SSO_PROVIDERS','VIEW_SSO_PROVIDER_MANY')")
    public ResponseEntity<List<SsoProviderDTO>> getAllSsoProvider(
    ) {
        return ResponseEntity.ok(ssoProviderService.findAll());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('MANAGE_SSO_PROVIDERS','VIEW_SSO_PROVIDER_MANY')")
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
    @PreAuthorize("hasAnyAuthority('MANAGE_SSO_PROVIDERS','VIEW_SSO_PROVIDER_MANY')")
    public ResponseEntity<Page<SsoProviderDTO>> getAllSsoProvider(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ssoProviderId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(ssoProviderService.findAll(pageable.getPageable()));
    }

    @GetMapping("/{ssoProviderId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_SSO_PROVIDERS','VIEW_SSO_PROVIDER')")
    public ResponseEntity<SsoProviderDTO> getSsoProvider(
            @PathVariable(name = "ssoProviderId") final Long ssoProviderId) {
        return ResponseEntity.ok(ssoProviderService.get(ssoProviderId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @PreAuthorize("hasAnyAuthority('MANAGE_SSO_PROVIDERS','UPDATE_SSO_PROVIDER_ONE')")
    public ResponseEntity<Long> createSsoProvider(
            @RequestBody @Valid final SsoProviderDTO ssoProviderDTO) {
        final Long createdSsoProviderId = ssoProviderService.create(ssoProviderDTO);
        return new ResponseEntity<>(createdSsoProviderId, HttpStatus.CREATED);
    }

    @PutMapping("/{ssoProviderId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_SSO_PROVIDERS','UPDATE_SSO_PROVIDER_ONE')")
    public ResponseEntity<Long> updateSsoProvider(
            @PathVariable(name = "ssoProviderId") final Long ssoProviderId,
            @RequestBody @Valid final SsoProviderDTO ssoProviderDTO) {
        ssoProviderService.update(ssoProviderId, ssoProviderDTO);
        return ResponseEntity.ok(ssoProviderId);
    }

    @DeleteMapping("/{ssoProviderId}")
    @ApiResponse(responseCode = "204")
    @PreAuthorize("hasAnyAuthority('MANAGE_SSO_PROVIDERS','DELETE_SSO_PROVIDER_ONE')")
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
