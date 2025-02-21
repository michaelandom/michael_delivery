package com.michael_delivery.backend.util;

public final class ValidationConstants {
    private ValidationConstants() {
        // Private constructor to prevent instantiation
    }

    public static final class URLs {
        private URLs() {
            // Private constructor to prevent instantiation
        }

        /**
         * Matches URLs with following pattern:
         * - Optional http:// or https:// prefix
         * - Domain with optional subdomains
         * - Optional path, query parameters and fragments
         */
        public static final String URL_PATTERN = "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$";

        /**
         * More strict pattern requiring https:// prefix
         */
        public static final String SECURE_URL_PATTERN = "^https://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$";

        /**
         * Pattern matching URLs with specific allowed domains
         * Example: .com, .org, .net
         */
        public static final String RESTRICTED_DOMAINS_PATTERN = "^(https?://)?([\\w-]+\\.)+(com|org|net)(/[\\w-./?%&=]*)?$";
    }
}