package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.model.DeleteRequest;
import com.michael_delivery.backend.dto.DeleteRequestDTO;
import com.michael_delivery.backend.dto.PageableBodyDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.Users;
import com.michael_delivery.backend.repository.UsersRepository;
import com.michael_delivery.backend.service.DeleteRequestService;
import com.michael_delivery.backend.util.CustomCollectors;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/deleteRequests", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeleteRequestController {

    private final DeleteRequestService deleteRequestService;
    private final UsersRepository usersRepository;

    public DeleteRequestController(final DeleteRequestService deleteRequestService,
            final UsersRepository usersRepository) {
        this.deleteRequestService = deleteRequestService;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGE_DELETE_REQUESTS','VIEW_DELETE_REQUEST_MANY')")
    public ResponseEntity<List<DeleteRequestDTO>> getAllDeleteRequests(
    ) {
        return ResponseEntity.ok(deleteRequestService.findAll());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('MANAGE_DELETE_REQUESTS','VIEW_DELETE_REQUEST_MANY')")
    public ResponseEntity<Page<DeleteRequestDTO>> searchDeleteRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "deleteRequestId:asc") String[] sortBy,
            @RequestParam(required = false) String note,
            @RequestParam(required = false) String reason
         ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<DeleteRequest> spec = new GenericSpecification<>();
        Specification<DeleteRequest> reasonSpec = spec.contains("reason", reason);
        Specification<DeleteRequest> noteSpec = spec.contains("note", note);
        Specification<DeleteRequest> finalSpec = Specification.where(reasonSpec).and(noteSpec);
        return ResponseEntity.ok(deleteRequestService.search(finalSpec,pageable.getPageable()));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_DELETE_REQUESTS','VIEW_DELETE_REQUEST_MANY')")
    public ResponseEntity<Page<DeleteRequestDTO>> getAllDeleteRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "deleteRequestId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(deleteRequestService.findAll(pageable.getPageable()));
    }


    @GetMapping("/{deleteRequestId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_DELETE_REQUESTS','VIEW_DELETE_REQUEST')")
    public ResponseEntity<DeleteRequestDTO> getDeleteRequest(
            @PathVariable(name = "deleteRequestId") final Long deleteRequestId) {
        return ResponseEntity.ok(deleteRequestService.get(deleteRequestId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @PreAuthorize("hasAnyAuthority('MANAGE_DELETE_REQUESTS','UPDATE_DELETE_REQUEST_ONE')")
    public ResponseEntity<Long> createDeleteRequest(
            @RequestBody @Valid final DeleteRequestDTO deleteRequestDTO) {
        final Long createdDeleteRequestId = deleteRequestService.create(deleteRequestDTO);
        return new ResponseEntity<>(createdDeleteRequestId, HttpStatus.CREATED);
    }

    @PutMapping("/{deleteRequestId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_DELETE_REQUESTS','UPDATE_DELETE_REQUEST_ONE')")
    public ResponseEntity<Long> updateDeleteRequest(
            @PathVariable(name = "deleteRequestId") final Long deleteRequestId,
            @RequestBody @Valid final DeleteRequestDTO deleteRequestDTO) {
        deleteRequestService.update(deleteRequestId, deleteRequestDTO);
        return ResponseEntity.ok(deleteRequestId);
    }

    @DeleteMapping("/{deleteRequestId}")
    @ApiResponse(responseCode = "204")
    @PreAuthorize("hasAnyAuthority('MANAGE_DELETE_REQUESTS','DELETE_DELETE_REQUEST_ONE')")
    public ResponseEntity<Void> deleteDeleteRequest(
            @PathVariable(name = "deleteRequestId") final Long deleteRequestId) {
        deleteRequestService.delete(deleteRequestId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/userValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_DELETE_REQUESTS','VIEW_DELETE_REQUEST_MANY')")
    public ResponseEntity<Map<Long, String>> getUserValues() {
        return ResponseEntity.ok(usersRepository.findAll(Sort.by("userId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Users::getUserId, Users::getUsername)));
    }

}
