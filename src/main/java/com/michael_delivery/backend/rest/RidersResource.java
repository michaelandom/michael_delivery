package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.domain.Riders;
import com.michael_delivery.backend.enums.RiderStatusType;
import com.michael_delivery.backend.model.PageableBodyDTO;
import com.michael_delivery.backend.model.RidersDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.RidersDTO;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.service.RidersService;
import com.michael_delivery.backend.util.CustomCollectors;
import com.michael_delivery.backend.util.ReferencedException;
import com.michael_delivery.backend.util.ReferencedWarning;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/riders", produces = MediaType.APPLICATION_JSON_VALUE)
public class RidersResource {

    private final RidersService ridersService;
    private final UsersRepository usersRepository;

    public RidersResource(final RidersService ridersService,
            final UsersRepository usersRepository) {
        this.ridersService = ridersService;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<RidersDTO>> getAllRiders(
    ) {
        return ResponseEntity.ok(ridersService.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<RidersDTO>> searchRiders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "riderId:asc") String[] sortBy,
            @RequestParam(required = false) Boolean isOnline,
            @RequestParam(required = false) Boolean isDeleted,
            @RequestParam(required = false) Boolean isSuspend,
            @RequestParam(required = false) Boolean passedQuiz,
            @RequestParam(required = false) Boolean profileCompleted,
            @RequestParam(required = false) Boolean isApproved,
            @RequestParam(required = false) Boolean isRejected,
            @RequestParam(required = false) RiderStatusType status,
            @RequestParam(required = false) OffsetDateTime visaValidFrom,
            @RequestParam(required = false) OffsetDateTime lastLocationTime,
            @RequestParam(required = false) OffsetDateTime visaValidTo,
            @RequestParam(required = false) String emergencyContactFirstName,
            @RequestParam(required = false) String emergencyContactPhoneNumber,
            @RequestParam(required = false) String emergencyContactLastName,
            @RequestParam(required = false) String bankName,
            @RequestParam(required = false) String bsbNumber,
            @RequestParam(required = false) String accountNumber,
            @RequestParam(required = false) String adminNote,
            @RequestParam(required = false) boolean visaValidFromIsAfter,
            @RequestParam(required = false) boolean lastLocationTimeIsAfter,
            @RequestParam(required = false) boolean visaValidToIsAfter


    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<Riders> spec = new GenericSpecification<>();
        Specification<Riders> isOnlineSpec = spec.equals("isOnline", isOnline);
        Specification<Riders> isDeletedSpec = spec.equals("isDeleted", isDeleted);
        Specification<Riders> isSuspendSpec = spec.equals("isSuspend", isSuspend);
        Specification<Riders> passedQuizSpec = spec.equals("passedQuiz", passedQuiz);
        Specification<Riders> profileCompletedSpec = spec.equals("profileCompleted", profileCompleted);
        Specification<Riders> isApprovedSpec = spec.equals("isApproved", isApproved);
        Specification<Riders> isRejectedSpec = spec.equals("isRejected", isRejected);
        Specification<Riders> statusSpec = spec.equals("status", status);
        Specification<Riders> lastLocationTimeSpec =visaValidFromIsAfter? spec.dateAfter("lastLocationTime", lastLocationTime): spec.dateBefore("lastLocationTime", lastLocationTime) ;
        Specification<Riders> visaValidFromSpec = lastLocationTimeIsAfter ? spec.dateAfter("visaValidFrom", visaValidFrom)  : spec.dateBefore("visaValidFrom", visaValidFrom) ;
        Specification<Riders> visaValidToSpec = visaValidToIsAfter? spec.dateAfter("visaValidTo", visaValidTo) : spec.dateBefore("visaValidTo", visaValidTo) ;
        Specification<Riders> emergencyContactFirstNameSpec = spec.contains("emergencyContactFirstName", emergencyContactFirstName);
        Specification<Riders> emergencyContactLastNameSpec = spec.contains("emergencyContactLastName", emergencyContactLastName);
        Specification<Riders> emergencyContactPhoneNumberSpec = spec.contains("emergencyContactPhoneNumber", emergencyContactPhoneNumber);
        Specification<Riders> bankNameSpec = spec.contains("bankName", bankName);
        Specification<Riders> bsbNumberSpec = spec.contains("bsbNumber", bsbNumber);
        Specification<Riders> accountNumberSpec = spec.contains("accountNumber", accountNumber);
        Specification<Riders> adminNoteSpec = spec.contains("adminNote", adminNote);
        Specification<Riders> finalSpec = Specification.where(isOnlineSpec).and(isDeletedSpec).and(isSuspendSpec).and(passedQuizSpec)
                .and(profileCompletedSpec)
                .and(isApprovedSpec)
                .and(isRejectedSpec)
                .and(statusSpec)
                .and(lastLocationTimeSpec)
                .and(visaValidFromSpec)
                .and(visaValidToSpec)
                .and(emergencyContactFirstNameSpec)
                .and(emergencyContactLastNameSpec)
                .and(emergencyContactPhoneNumberSpec)
                .and(bankNameSpec)
                .and(bsbNumberSpec)
                .and(accountNumberSpec)
                .and(adminNoteSpec);
        return ResponseEntity.ok(ridersService.search(finalSpec,pageable.getPageable()));
    }
    @GetMapping
    public ResponseEntity<Page<RidersDTO>> getAllRiders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "riderId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(ridersService.findAll(pageable.getPageable()));
    }

    @GetMapping("/{riderId}")
    public ResponseEntity<RidersDTO> getRiders(@PathVariable(name = "riderId") final Long riderId) {
        return ResponseEntity.ok(ridersService.get(riderId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createRiders(@RequestBody @Valid final RidersDTO ridersDTO) {
        final Long createdRiderId = ridersService.create(ridersDTO);
        return new ResponseEntity<>(createdRiderId, HttpStatus.CREATED);
    }

    @PutMapping("/{riderId}")
    public ResponseEntity<Long> updateRiders(@PathVariable(name = "riderId") final Long riderId,
            @RequestBody @Valid final RidersDTO ridersDTO) {
        ridersService.update(riderId, ridersDTO);
        return ResponseEntity.ok(riderId);
    }

    @DeleteMapping("/{riderId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRiders(@PathVariable(name = "riderId") final Long riderId) {
        final ReferencedWarning referencedWarning = ridersService.getReferencedWarning(riderId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        ridersService.delete(riderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/userValues")
    public ResponseEntity<Map<Long, String>> getUserValues() {
        return ResponseEntity.ok(usersRepository.findAll(Sort.by("userId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Users::getUserId, Users::getUsername)));
    }

}
