package com.michael_delivery.backend.service;

import com.michael_delivery.backend.domain.Users;
import com.michael_delivery.backend.enums.AccountType;
import com.michael_delivery.backend.repos.UsersRepository;
import com.michael_delivery.backend.util.UnauthorizedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;



@Service
public class AuthService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    public AuthService(
            final UsersRepository usersRepository
    ) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();

    }



    public Users authenticate(final String username, final String password) {
        final Users users = usersRepository.findById(1L).orElseThrow(UnauthorizedException::new);
        if (users.getPasswordHash().isEmpty() || !passwordEncoder.matches(password, users.getPassword())  || users.getAccountType().equals(AccountType.SSO)) {
            throw new UnauthorizedException();
        }
        users.setLastLogin( OffsetDateTime.now());
        usersRepository.save(users);
        return users;
    }


}