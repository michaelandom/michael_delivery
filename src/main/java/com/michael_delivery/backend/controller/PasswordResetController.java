package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.dto.PasswordResetDTO;
import com.michael_delivery.backend.dto.PageableBodyDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.Users;
import com.michael_delivery.backend.repository.UsersRepository;
import com.michael_delivery.backend.service.PasswordResetService;
import com.michael_delivery.backend.util.CustomCollectors;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/passwordResets", produces = MediaType.APPLICATION_JSON_VALUE)
public class PasswordResetController {

    private final PasswordResetService passwordResetService;
    private final UsersRepository usersRepository;

    public PasswordResetController(final PasswordResetService passwordResetService,
            final UsersRepository usersRepository) {
        this.passwordResetService = passwordResetService;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGE_PASSWORD_RESETS','VIEW_PASSWORD_RESET_MANY')")
    public ResponseEntity<List<PasswordResetDTO>> getAllPasswordReset(
    ) {
        return ResponseEntity.ok(passwordResetService.findAll());
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_PASSWORD_RESETS','VIEW_PASSWORD_RESET_MANY')")
    public ResponseEntity<Page<PasswordResetDTO>> getAllPasswordReset(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "passwordResetId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(passwordResetService.findAll(pageable.getPageable()));
    }

    @GetMapping("/{passwordResetId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_PASSWORD_RESETS','VIEW_PASSWORD_RESET')")
    public ResponseEntity<PasswordResetDTO> getPasswordReset(
            @PathVariable(name = "passwordResetId") final Long passwordResetId) {
        return ResponseEntity.ok(passwordResetService.get(passwordResetId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @PreAuthorize("hasAnyAuthority('MANAGE_PASSWORD_RESETS','UPDATE_PASSWORD_RESET_ONE')")
    public ResponseEntity<Long> createPasswordReset(
            @RequestBody @Valid final PasswordResetDTO passwordResetDTO) {
        final Long createdPasswordResetId = passwordResetService.create(passwordResetDTO);
        return new ResponseEntity<>(createdPasswordResetId, HttpStatus.CREATED);
    }

    @PutMapping("/{passwordResetId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_PASSWORD_RESETS','UPDATE_PASSWORD_RESET_ONE')")
    public ResponseEntity<Long> updatePasswordReset(
            @PathVariable(name = "passwordResetId") final Long passwordResetId,
            @RequestBody @Valid final PasswordResetDTO passwordResetDTO) {
        passwordResetService.update(passwordResetId, passwordResetDTO);
        return ResponseEntity.ok(passwordResetId);
    }

    @DeleteMapping("/{passwordResetId}")
    @ApiResponse(responseCode = "204")
    @PreAuthorize("hasAnyAuthority('MANAGE_PASSWORD_RESETS','DELETE_PASSWORD_RESET_ONE')")
    public ResponseEntity<Void> deletePasswordReset(
            @PathVariable(name = "passwordResetId") final Long passwordResetId) {
        passwordResetService.delete(passwordResetId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/userValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_PASSWORD_RESETS','VIEW_PASSWORD_RESET_MANY')")
    public ResponseEntity<Map<Long, String>> getUserValues() {
        return ResponseEntity.ok(usersRepository.findAll(Sort.by("userId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Users::getUserId, Users::getUsername)));
    }

    @GetMapping("/reseatedByValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_PASSWORD_RESETS','VIEW_PASSWORD_RESET_MANY')")
    public ResponseEntity<Map<Long, String>> getReseatedByValues() {
        return ResponseEntity.ok(usersRepository.findAll(Sort.by("userId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Users::getUserId, Users::getUsername)));
    }

}
