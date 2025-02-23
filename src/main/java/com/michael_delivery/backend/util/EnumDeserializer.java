package com.michael_delivery.backend.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.michael_delivery.backend.enums.AppNameType;

import java.io.IOException;

public class EnumDeserializer extends JsonDeserializer<AppNameType> {

    @Override
    public AppNameType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText().toUpperCase();  // Making it case-insensitive

        try {
            return AppNameType.valueOf(value);  // Map to enum value
        } catch (IllegalArgumentException e) {
            // If invalid, throw an exception to be handled later in the validation process
            throw new IOException("Invalid value for AppName. Valid values are: RIDER, CUSTOMER");
        }
    }
}
