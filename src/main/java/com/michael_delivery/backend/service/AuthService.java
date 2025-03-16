package com.michael_delivery.backend.service;

import com.michael_delivery.backend.model.Users;
import com.michael_delivery.backend.enums.AccountType;
import com.michael_delivery.backend.repository.UsersRepository;
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
        System.out.println("passwordEncoder");
        final Users users = usersRepository.findByUsername(username).orElseThrow(UnauthorizedException::new);
        System.out.println("passwordEncoder.encode(password)");
        if (users.getPasswordHash().isEmpty() || !passwordEncoder.matches(password, users.getPassword())  || users.getAccountType().equals(AccountType.SSO)) {
            throw new UnauthorizedException();
        }
        users.setLastLogin( OffsetDateTime.now());
        usersRepository.save(users);
        return users;
    }


}