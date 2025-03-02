package com.michael_delivery.backend.model;

import jakarta.validation.constraints.NotBlank;

public class UsernameAndPasswordLoginDTO {
    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "password is required")
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
