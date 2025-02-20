package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.DeleteRequestDTO;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.service.DeleteRequestService;
import com.michael_delivery.backend.util.CustomCollectors;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/deleteRequests", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeleteRequestResource {

    private final DeleteRequestService deleteRequestService;
    private final UsersRepository usersRepository;

    public DeleteRequestResource(final DeleteRequestService deleteRequestService,
            final UsersRepository usersRepository) {
        this.deleteRequestService = deleteRequestService;
        this.usersRepository = usersRepository;
    }

    @GetMapping
    public ResponseEntity<List<DeleteRequestDTO>> getAllDeleteRequests() {
        return ResponseEntity.ok(deleteRequestService.findAll());
    }

    @GetMapping("/{deleteRequestId}")
    public ResponseEntity<DeleteRequestDTO> getDeleteRequest(
            @PathVariable(name = "deleteRequestId") final Long deleteRequestId) {
        return ResponseEntity.ok(deleteRequestService.get(deleteRequestId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDeleteRequest(
            @RequestBody @Valid final DeleteRequestDTO deleteRequestDTO) {
        final Long createdDeleteRequestId = deleteRequestService.create(deleteRequestDTO);
        return new ResponseEntity<>(createdDeleteRequestId, HttpStatus.CREATED);
    }

    @PutMapping("/{deleteRequestId}")
    public ResponseEntity<Long> updateDeleteRequest(
            @PathVariable(name = "deleteRequestId") final Long deleteRequestId,
            @RequestBody @Valid final DeleteRequestDTO deleteRequestDTO) {
        deleteRequestService.update(deleteRequestId, deleteRequestDTO);
        return ResponseEntity.ok(deleteRequestId);
    }

    @DeleteMapping("/{deleteRequestId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDeleteRequest(
            @PathVariable(name = "deleteRequestId") final Long deleteRequestId) {
        deleteRequestService.delete(deleteRequestId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/userValues")
    public ResponseEntity<Map<Long, String>> getUserValues() {
        return ResponseEntity.ok(usersRepository.findAll(Sort.by("userId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Users::getUserId, Users::getUsername)));
    }

}
