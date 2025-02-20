package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.SsoProviderDTO;
import com.michael_delivery.backend.service.SsoProviderService;
import com.michael_delivery.backend.util.ReferencedException;
import com.michael_delivery.backend.util.ReferencedWarning;
import jakarta.validation.Valid;
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

    @GetMapping
    public ResponseEntity<List<SsoProviderDTO>> getAllSsoProviders() {
        return ResponseEntity.ok(ssoProviderService.findAll());
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
