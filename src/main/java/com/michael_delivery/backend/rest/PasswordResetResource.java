package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.domain.PasswordReset;
import com.michael_delivery.backend.enums.OrderStatusType;
import com.michael_delivery.backend.enums.VehicleType;
import com.michael_delivery.backend.model.PasswordResetDTO;
import com.michael_delivery.backend.model.PageableBodyDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.PasswordResetDTO;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.service.PasswordResetService;
import com.michael_delivery.backend.util.CustomCollectors;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/passwordResets", produces = MediaType.APPLICATION_JSON_VALUE)
public class PasswordResetResource {

    private final PasswordResetService passwordResetService;
    private final UsersRepository usersRepository;

    public PasswordResetResource(final PasswordResetService passwordResetService,
            final UsersRepository usersRepository) {
        this.passwordResetService = passwordResetService;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<PasswordResetDTO>> getAllPasswordReset(
    ) {
        return ResponseEntity.ok(passwordResetService.findAll());
    }

    @GetMapping
    public ResponseEntity<Page<PasswordResetDTO>> getAllPasswordReset(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "orderId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(passwordResetService.findAll(pageable.getPageable()));
    }

    @GetMapping("/{passwordResetId}")
    public ResponseEntity<PasswordResetDTO> getPasswordReset(
            @PathVariable(name = "passwordResetId") final Long passwordResetId) {
        return ResponseEntity.ok(passwordResetService.get(passwordResetId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPasswordReset(
            @RequestBody @Valid final PasswordResetDTO passwordResetDTO) {
        final Long createdPasswordResetId = passwordResetService.create(passwordResetDTO);
        return new ResponseEntity<>(createdPasswordResetId, HttpStatus.CREATED);
    }

    @PutMapping("/{passwordResetId}")
    public ResponseEntity<Long> updatePasswordReset(
            @PathVariable(name = "passwordResetId") final Long passwordResetId,
            @RequestBody @Valid final PasswordResetDTO passwordResetDTO) {
        passwordResetService.update(passwordResetId, passwordResetDTO);
        return ResponseEntity.ok(passwordResetId);
    }

    @DeleteMapping("/{passwordResetId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePasswordReset(
            @PathVariable(name = "passwordResetId") final Long passwordResetId) {
        passwordResetService.delete(passwordResetId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/userValues")
    public ResponseEntity<Map<Long, String>> getUserValues() {
        return ResponseEntity.ok(usersRepository.findAll(Sort.by("userId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Users::getUserId, Users::getUsername)));
    }

    @GetMapping("/reseatedByValues")
    public ResponseEntity<Map<Long, String>> getReseatedByValues() {
        return ResponseEntity.ok(usersRepository.findAll(Sort.by("userId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Users::getUserId, Users::getUsername)));
    }

}
