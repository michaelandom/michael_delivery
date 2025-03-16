package com.michael_delivery.backend.dto;

import jakarta.validation.constraints.NotBlank;


public class SsoProviderDTO {

    private Long ssoProviderId;

    @NotBlank(message = "ssoProvider is required")
    private String ssoProvider;

    public Long getSsoProviderId() {
        return ssoProviderId;
    }

    public void setSsoProviderId(final Long ssoProviderId) {
        this.ssoProviderId = ssoProviderId;
    }

    public String getSsoProvider() {
        return ssoProvider;
    }

    public void setSsoProvider(final String ssoProvider) {
        this.ssoProvider = ssoProvider;
    }


}
