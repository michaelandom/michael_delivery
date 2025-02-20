package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.UserFavoriteAddressDTO;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.service.UserFavoriteAddressService;
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
@RequestMapping(value = "/api/userFavoriteAddresses", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserFavoriteAddressResource {

    private final UserFavoriteAddressService userFavoriteAddressService;
    private final UsersRepository usersRepository;

    public UserFavoriteAddressResource(final UserFavoriteAddressService userFavoriteAddressService,
            final UsersRepository usersRepository) {
        this.userFavoriteAddressService = userFavoriteAddressService;
        this.usersRepository = usersRepository;
    }

    @GetMapping
    public ResponseEntity<List<UserFavoriteAddressDTO>> getAllUserFavoriteAddresses() {
        return ResponseEntity.ok(userFavoriteAddressService.findAll());
    }

    @GetMapping("/{favoriteAddressId}")
    public ResponseEntity<UserFavoriteAddressDTO> getUserFavoriteAddress(
            @PathVariable(name = "favoriteAddressId") final Long favoriteAddressId) {
        return ResponseEntity.ok(userFavoriteAddressService.get(favoriteAddressId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createUserFavoriteAddress(
            @RequestBody @Valid final UserFavoriteAddressDTO userFavoriteAddressDTO) {
        final Long createdFavoriteAddressId = userFavoriteAddressService.create(userFavoriteAddressDTO);
        return new ResponseEntity<>(createdFavoriteAddressId, HttpStatus.CREATED);
    }

    @PutMapping("/{favoriteAddressId}")
    public ResponseEntity<Long> updateUserFavoriteAddress(
            @PathVariable(name = "favoriteAddressId") final Long favoriteAddressId,
            @RequestBody @Valid final UserFavoriteAddressDTO userFavoriteAddressDTO) {
        userFavoriteAddressService.update(favoriteAddressId, userFavoriteAddressDTO);
        return ResponseEntity.ok(favoriteAddressId);
    }

    @DeleteMapping("/{favoriteAddressId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUserFavoriteAddress(
            @PathVariable(name = "favoriteAddressId") final Long favoriteAddressId) {
        userFavoriteAddressService.delete(favoriteAddressId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/userValues")
    public ResponseEntity<Map<Long, String>> getUserValues() {
        return ResponseEntity.ok(usersRepository.findAll(Sort.by("userId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Users::getUserId, Users::getUsername)));
    }

}
