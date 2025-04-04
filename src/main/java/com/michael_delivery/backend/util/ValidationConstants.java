package com.michael_delivery.backend.util;

public final class ValidationConstants {
    private ValidationConstants() {
    }

    public static final class URLs {
        private URLs() {
        }

        public static final String URL_PATTERN = "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$";

        public static final String SECURE_URL_PATTERN = "^https://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$";

        public static final String RESTRICTED_DOMAINS_PATTERN = "^(https?://)?([\\w-]+\\.)+(com|org|net)(/[\\w-./?%&=]*)?$";
    }

    public static final class TIME{
        private TIME() {}
        public static final String TIME_PATTERN = "^([0-1]\\d|2[0-3]):[0-5]\\d$";

    }
    public static final class StrongPasswordValidator{

        public static final String STRONG_PASSWORD_REGEX =
                "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";



    }
}