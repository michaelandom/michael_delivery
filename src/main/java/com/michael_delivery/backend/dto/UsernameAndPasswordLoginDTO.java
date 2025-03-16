package com.michael_delivery.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class UsernameAndPasswordLoginDTO {
    @NotBlank(message = "username is required")
    @Schema(example = "admin1")
    private String username;
    @NotBlank(message = "password is required")
    @Schema(example = "Pass123!")
    private String password;

    public @NotBlank(message = "username is required") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "username is required") String username) {
        this.username = username;
    }

    public @NotBlank(message = "password is required") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "password is required") String password) {
        this.password = password;
    }
}
