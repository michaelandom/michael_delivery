package com.michael_delivery.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.RidersDTO;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.service.RidersService;
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
@RequestMapping(value = "/api/riders", produces = MediaType.APPLICATION_JSON_VALUE)
public class RidersResource {

    private final RidersService ridersService;
    private final UsersRepository usersRepository;

    public RidersResource(final RidersService ridersService,
            final UsersRepository usersRepository) {
        this.ridersService = ridersService;
        this.usersRepository = usersRepository;
    }

    @GetMapping
    public ResponseEntity<List<RidersDTO>> getAllRiders() {
        return ResponseEntity.ok(ridersService.findAll());
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
