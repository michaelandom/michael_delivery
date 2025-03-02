package com.michael_delivery.backend.rest;

import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.model.UsernameAndPasswordLoginDTO;
import com.michael_delivery.backend.model.UsersDTO;
import com.michael_delivery.backend.model.response.UserResponse;
import com.michael_delivery.backend.service.AuthService;
import com.michael_delivery.backend.service.UsersService;
import com.michael_delivery.backend.util.JwtUtil;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthResource {

    private final UsersService usersService;
    private final AuthService authService;
    private final JwtUtil jwtUtil;


    public AuthResource(final UsersService usersService,final AuthService authService, final JwtUtil jwtUtil) {
        this.usersService = usersService;
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/register")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> register(@RequestBody @Valid final UsersDTO usersDTO) {
        final Long createdUserId = usersService.create(usersDTO,false);
        return new ResponseEntity<>(createdUserId, HttpStatus.CREATED);
    }

    @PostMapping("/register-sso")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> registerSso(@RequestBody @Valid final UsersDTO usersDTO) {
        final Long createdUserId = usersService.create(usersDTO,true);
        return new ResponseEntity<>(createdUserId, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<UserResponse> login(@RequestBody @Valid final UsernameAndPasswordLoginDTO usersDTO) {
      final Users users = authService.authenticate(usersDTO.getUsername(), usersDTO.getPassword());
      UserResponse userResponse=  usersService.getUserData(users.getUserId());
        String accessToken = jwtUtil.generateToken(userResponse.getUserId(),userResponse.getUsername(),userResponse.getPermissions());
        String refreshToken = jwtUtil.generateToken(userResponse.getUserId(),userResponse.getUsername());
        userResponse.setAccessToken(accessToken);
        userResponse.setRefreshToken(refreshToken);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }




}
