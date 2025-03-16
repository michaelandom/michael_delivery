package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.model.*;
import com.michael_delivery.backend.enums.AccountStatusType;
import com.michael_delivery.backend.enums.AccountType;
import com.michael_delivery.backend.enums.GenderType;
import com.michael_delivery.backend.dto.*;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.repository.BillingAddressRepository;
import com.michael_delivery.backend.repository.BussinessAccountRepository;
import com.michael_delivery.backend.repository.SsoProviderRepository;
import com.michael_delivery.backend.service.UsersService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsersController {

    private final UsersService usersService;
    private final SsoProviderRepository ssoProviderRepository;
    private final BillingAddressRepository billingAddressRepository;
    private final BussinessAccountRepository bussinessAccountRepository;

    public UsersController(final UsersService usersService,
            final SsoProviderRepository ssoProviderRepository,
            final BillingAddressRepository billingAddressRepository,
            final BussinessAccountRepository bussinessAccountRepository) {
        this.usersService = usersService;
        this.ssoProviderRepository = ssoProviderRepository;
        this.billingAddressRepository = billingAddressRepository;
        this.bussinessAccountRepository = bussinessAccountRepository;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGE_VEHICLES','VIEW_VEHICLE_MANY')")
    public ResponseEntity<List<UsersDTO>> getAllUsers(
    ) {
        return ResponseEntity.ok(usersService.findAll());
    }

    @GetMapping("/search")
@PreAuthorize("hasAnyAuthority('MANAGE_VEHICLES','VIEW_VEHICLE_MANY')")
    public ResponseEntity<Page<UsersDTO>> searchUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "userId:asc") String[] sortBy,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) LocalDate dateOfBirth,
            @RequestParam(required = false) GenderType gender,
            @RequestParam(required = false) boolean emailVerified,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) boolean phoneVerified,
            @RequestParam(required = false) AccountType accountType,
            @RequestParam(required = false) AccountStatusType accountStatus,
            @RequestParam(required = false) OffsetDateTime lastLogin,
            @RequestParam(required = false) OffsetDateTime requestForDeleteAt,
            @RequestParam(required = false) boolean lastLoginIsAfter,
            @RequestParam(required = false) boolean requestForDeleteAtIsAfter
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<Users> spec = new GenericSpecification<>();
        Specification<Users> usernameSpec = spec.contains("username", username);
        Specification<Users> firstNameSpec = spec.contains("firstName", firstName);
        Specification<Users> lastNameSpec = spec.contains("nickName", lastName);
        Specification<Users> emailSpec = spec.equals("email", email);
        Specification<Users> dateOfBirthSpec = spec.equals("dateOfBirth", dateOfBirth);
        Specification<Users> genderSpec = spec.equals("gender", gender);
        Specification<Users> emailVerifiedSpec = spec.equals("emailVerified", emailVerified);
        Specification<Users> phoneSpec = spec.equals("phone", phone);
        Specification<Users> phoneVerifiedSpec = spec.equals("phoneVerified", phoneVerified);
        Specification<Users> accountTypeSpec = spec.equals("accountType", accountType);
        Specification<Users> accountStatusSpec = spec.equals("accountStatus", accountStatus);
        Specification<Users> lastLoginSpec = lastLoginIsAfter?spec.dateAfter("lastLogin", lastLogin) : spec.dateBefore("lastLogin", lastLogin);
        Specification<Users> requestForDeleteAtSpec =requestForDeleteAtIsAfter?spec.dateAfter("requestForDeleteAt", requestForDeleteAt) :spec.dateBefore("requestForDeleteAt", requestForDeleteAt) ;
        Specification<Users> finalSpec = Specification.where(usernameSpec)
                .and(firstNameSpec)
                .and(lastNameSpec)
                .and(emailSpec)
                .and(dateOfBirthSpec)
                .and(genderSpec)
                .and(emailVerifiedSpec)
                .and(phoneSpec)
                .and(phoneVerifiedSpec)
                .and(accountTypeSpec)
                .and(accountStatusSpec)
                .and(lastLoginSpec)
                .and(requestForDeleteAtSpec);
        return ResponseEntity.ok(usersService.search(finalSpec,pageable.getPageable()));
    }
    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_USERS','VIEW_USER_MANY')")
    public ResponseEntity<Page<UsersDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "userId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(usersService.findAll(pageable.getPageable()));
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_USERS','VIEW_USER')")
    public ResponseEntity<UsersDTO> getUsers(@PathVariable(name = "userId") final Long userId) {
        return ResponseEntity.ok(usersService.get(userId));
    }

    @PostMapping("/{userId}/password")
    public ResponseEntity<Long> setPassword(@PathVariable Long userId,
                                            @RequestBody SetPasswordDTO request) {
        return ResponseEntity.ok(usersService.setInitialPassword(userId,request.getPassword()));
    }


    @PutMapping("/{userId}/password")
    public ResponseEntity<Boolean> changePassword(@PathVariable Long userId,
                                            @RequestBody ChangePasswordDTO request) {
        return ResponseEntity.ok(usersService.changePassword(userId,request.getOldPassword(),request.getNewPassword()));
    }



    @PostMapping
    @ApiResponse(responseCode = "201")
    @PreAuthorize("hasAnyAuthority('MANAGE_USERS','UPDATE_USER_ONE')")
    public ResponseEntity<Long> createUsers(@RequestBody @Valid final UsersDTO usersDTO) {
        final Long createdUserId = usersService.create(usersDTO,false);
        return new ResponseEntity<>(createdUserId, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_USERS','UPDATE_USER_ONE')")
    public ResponseEntity<Long> updateUsers(@PathVariable(name = "userId") final Long userId,
            @RequestBody @Valid final UsersDTO usersDTO) {
        usersService.update(userId, usersDTO);
        return ResponseEntity.ok(userId);
    }

    @DeleteMapping("/{userId}")
    @ApiResponse(responseCode = "204")
    @PreAuthorize("hasAnyAuthority('MANAGE_USERS','DELETE_USER_ONE')")
    public ResponseEntity<Void> deleteUsers(@PathVariable(name = "userId") final Long userId) {
        final ReferencedWarning referencedWarning = usersService.getReferencedWarning(userId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        usersService.delete(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ssoProviderValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_USERS','VIEW_USER_MANY')")
    public ResponseEntity<Map<Long, String>> getSsoProviderValues() {
        return ResponseEntity.ok(ssoProviderRepository.findAll(Sort.by("ssoProviderId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(SsoProvider::getSsoProviderId, SsoProvider::getSsoProvider)));
    }

    @GetMapping("/billingAddressValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_USERS','VIEW_USER_MANY')")
    public ResponseEntity<Map<Long, Long>> getBillingAddressValues() {
        return ResponseEntity.ok(billingAddressRepository.findAll(Sort.by("billingAddressId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(BillingAddress::getBillingAddressId, BillingAddress::getBillingAddressId)));
    }

    @GetMapping("/bussinessAccountValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_USERS','VIEW_USER_MANY')")
    public ResponseEntity<Map<Long, String>> getBussinessAccountValues() {
        return ResponseEntity.ok(bussinessAccountRepository.findAll(Sort.by("bussinessAccountId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(BussinessAccount::getBussinessAccountId, BussinessAccount::getCompanyAbn)));
    }

}
