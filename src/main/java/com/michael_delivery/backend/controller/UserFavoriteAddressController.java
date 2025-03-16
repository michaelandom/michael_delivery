package com.michael_delivery.backend.controller;

import com.michael_delivery.backend.model.UserFavoriteAddress;
import com.michael_delivery.backend.enums.AddressType;
import com.michael_delivery.backend.dto.PageableBodyDTO;
import com.michael_delivery.backend.dto.UserFavoriteAddressDTO;
import com.michael_delivery.backend.specification.GenericSpecification;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.model.Users;
import com.michael_delivery.backend.repository.UsersRepository;
import com.michael_delivery.backend.service.UserFavoriteAddressService;
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
@RequestMapping(value = "/api/userFavoriteAddresses", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserFavoriteAddressController {

    private final UserFavoriteAddressService userFavoriteAddressService;
    private final UsersRepository usersRepository;

    public UserFavoriteAddressController(final UserFavoriteAddressService userFavoriteAddressService,
            final UsersRepository usersRepository) {
        this.userFavoriteAddressService = userFavoriteAddressService;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('MANAGE_USER_FAVORITE_ADDRESSES','VIEW_USER_FAVORITE_ADDRESS_MANY')")
    public ResponseEntity<List<UserFavoriteAddressDTO>> getAllUserFavoriteAddress(
    ) {
        return ResponseEntity.ok(userFavoriteAddressService.findAll());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('MANAGE_USER_FAVORITE_ADDRESSES','VIEW_USER_FAVORITE_ADDRESS_MANY')")
    public ResponseEntity<Page<UserFavoriteAddressDTO>> searchUserFavoriteAddress(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "favoriteAddressId:asc") String[] sortBy,
            @RequestParam(required = false) String longAddress,
            @RequestParam(required = false) String shortAddress,
            @RequestParam(required = false) String nickName,
            @RequestParam(required = false) AddressType addressType,
            @RequestParam(required = false) String customAddress
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        GenericSpecification<UserFavoriteAddress> spec = new GenericSpecification<>();
        Specification<UserFavoriteAddress> longAddressSpec = spec.contains("longAddress", longAddress);
        Specification<UserFavoriteAddress> shortAddressSpec = spec.contains("shortAddress", shortAddress);
        Specification<UserFavoriteAddress> nickNameSpec = spec.contains("nickName", nickName);
        Specification<UserFavoriteAddress> addressTypeSpec = spec.equals("addressType", addressType);
        Specification<UserFavoriteAddress> customAddressSpec = spec.contains("customAddress", customAddress);
        Specification<UserFavoriteAddress> finalSpec = Specification.where(longAddressSpec).and(shortAddressSpec)
                .and(nickNameSpec).and(addressTypeSpec).and(customAddressSpec);
        return ResponseEntity.ok(userFavoriteAddressService.search(finalSpec,pageable.getPageable()));
    }
    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGE_USER_FAVORITE_ADDRESSES','VIEW_USER_FAVORITE_ADDRESS_MANY')")
    public ResponseEntity<Page<UserFavoriteAddressDTO>> getAllUserFavoriteAddress(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "favoriteAddressId:asc") String[] sortBy
    ) {
        PageableBodyDTO pageable = new PageableBodyDTO(page, size, sortBy);
        return ResponseEntity.ok(userFavoriteAddressService.findAll(pageable.getPageable()));
    }

    @GetMapping("/{favoriteAddressId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_USER_FAVORITE_ADDRESSES','VIEW_USER_FAVORITE_ADDRESS')")
    public ResponseEntity<UserFavoriteAddressDTO> getUserFavoriteAddress(
            @PathVariable(name = "favoriteAddressId") final Long favoriteAddressId) {
        return ResponseEntity.ok(userFavoriteAddressService.get(favoriteAddressId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @PreAuthorize("hasAnyAuthority('MANAGE_USER_FAVORITE_ADDRESSES','UPDATE_USER_FAVORITE_ADDRESS_ONE')")
    public ResponseEntity<Long> createUserFavoriteAddress(
            @RequestBody @Valid final UserFavoriteAddressDTO userFavoriteAddressDTO) {
        final Long createdFavoriteAddressId = userFavoriteAddressService.create(userFavoriteAddressDTO);
        return new ResponseEntity<>(createdFavoriteAddressId, HttpStatus.CREATED);
    }

    @PutMapping("/{favoriteAddressId}")
    @PreAuthorize("hasAnyAuthority('MANAGE_USER_FAVORITE_ADDRESSES','UPDATE_USER_FAVORITE_ADDRESS_ONE')")
    public ResponseEntity<Long> updateUserFavoriteAddress(
            @PathVariable(name = "favoriteAddressId") final Long favoriteAddressId,
            @RequestBody @Valid final UserFavoriteAddressDTO userFavoriteAddressDTO) {
        userFavoriteAddressService.update(favoriteAddressId, userFavoriteAddressDTO);
        return ResponseEntity.ok(favoriteAddressId);
    }

    @DeleteMapping("/{favoriteAddressId}")
    @ApiResponse(responseCode = "204")
    @PreAuthorize("hasAnyAuthority('MANAGE_USER_FAVORITE_ADDRESSES','DELETE_USER_FAVORITE_ADDRESS_ONE')")
    public ResponseEntity<Void> deleteUserFavoriteAddress(
            @PathVariable(name = "favoriteAddressId") final Long favoriteAddressId) {
        userFavoriteAddressService.delete(favoriteAddressId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/userValues")
    @PreAuthorize("hasAnyAuthority('MANAGE_USER_FAVORITE_ADDRESSES','VIEW_USER_FAVORITE_ADDRESS_MANY')")
    public ResponseEntity<Map<Long, String>> getUserValues() {
        return ResponseEntity.ok(usersRepository.findAll(Sort.by("userId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Users::getUserId, Users::getUsername)));
    }

}
