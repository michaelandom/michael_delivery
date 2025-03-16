package com.michael_delivery.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import static com.michael_delivery.backend.util.ValidationConstants.StrongPasswordValidator.STRONG_PASSWORD_REGEX;

public class SetPasswordDTO {

    @NotNull
    @Pattern(regexp = STRONG_PASSWORD_REGEX, message = "Password must be at least 8 characters long, " +
            "contain one uppercase letter, one lowercase letter, " +
            "one digit, and one special character.")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
