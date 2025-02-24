package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.model.ChangePasswordDTO;
import com.michael_delivery.backend.model.SetPasswordDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.BillingAddress;
import com.michael_delivery.backend.domain.BussinessAccount;
import com.michael_delivery.backend.domain.SsoProvider;
import com.michael_delivery.backend.model.UsersDTO;
import com.michael_delivery.backend.repos.BillingAddressRepository;
import com.michael_delivery.backend.repos.BussinessAccountRepository;
import com.michael_delivery.backend.repos.SsoProviderRepository;
import com.michael_delivery.backend.service.UsersService;
import com.michael_delivery.backend.util.CustomCollectors;
import com.michael_delivery.backend.util.ReferencedException;
import com.michael_delivery.backend.util.ReferencedWarning;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsersResource {

    private final UsersService usersService;
    private final SsoProviderRepository ssoProviderRepository;
    private final BillingAddressRepository billingAddressRepository;
    private final BussinessAccountRepository bussinessAccountRepository;

    public UsersResource(final UsersService usersService,
            final SsoProviderRepository ssoProviderRepository,
            final BillingAddressRepository billingAddressRepository,
            final BussinessAccountRepository bussinessAccountRepository) {
        this.usersService = usersService;
        this.ssoProviderRepository = ssoProviderRepository;
        this.billingAddressRepository = billingAddressRepository;
        this.bussinessAccountRepository = bussinessAccountRepository;
    }

    @GetMapping
    public ResponseEntity<List<UsersDTO>> getAllUsers() {
        return ResponseEntity.ok(usersService.findAll());
    }

    @GetMapping("/{userId}")
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
    public ResponseEntity<Long> createUsers(@RequestBody @Valid final UsersDTO usersDTO) {
        final Long createdUserId = usersService.create(usersDTO);
        return new ResponseEntity<>(createdUserId, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Long> updateUsers(@PathVariable(name = "userId") final Long userId,
            @RequestBody @Valid final UsersDTO usersDTO) {
        usersService.update(userId, usersDTO);
        return ResponseEntity.ok(userId);
    }

    @DeleteMapping("/{userId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUsers(@PathVariable(name = "userId") final Long userId) {
        final ReferencedWarning referencedWarning = usersService.getReferencedWarning(userId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        usersService.delete(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ssoProviderValues")
    public ResponseEntity<Map<Long, String>> getSsoProviderValues() {
        return ResponseEntity.ok(ssoProviderRepository.findAll(Sort.by("ssoProviderId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(SsoProvider::getSsoProviderId, SsoProvider::getSsoProvider)));
    }

    @GetMapping("/billingAddressValues")
    public ResponseEntity<Map<Long, Long>> getBillingAddressValues() {
        return ResponseEntity.ok(billingAddressRepository.findAll(Sort.by("billingAddressId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(BillingAddress::getBillingAddressId, BillingAddress::getBillingAddressId)));
    }

    @GetMapping("/bussinessAccountValues")
    public ResponseEntity<Map<Long, String>> getBussinessAccountValues() {
        return ResponseEntity.ok(bussinessAccountRepository.findAll(Sort.by("bussinessAccountId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(BussinessAccount::getBussinessAccountId, BussinessAccount::getCompanyAbn)));
    }

}
