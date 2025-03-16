package com.michael_delivery.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import static com.michael_delivery.backend.util.ValidationConstants.StrongPasswordValidator.STRONG_PASSWORD_REGEX;

public class ChangePasswordDTO {

    @NotNull
    private String oldPassword;

    @NotNull
    @Pattern(regexp = STRONG_PASSWORD_REGEX, message = "Password must be at least 8 characters long, " +
            "contain one uppercase letter, one lowercase letter, " +
            "one digit, and one special character.")
    private String newPassword;


    public @NotNull String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(@NotNull String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
