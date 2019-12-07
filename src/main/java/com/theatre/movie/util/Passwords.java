package com.theatre.movie.util;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Passwords {

    public static String hash(String password) {
        return new String(BCrypt.withDefaults().hash(12, password.getBytes(StandardCharsets.UTF_8)));
    }

    public static boolean verifyHash(String password, String hash) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.getBytes(StandardCharsets.UTF_8), hash.getBytes(StandardCharsets.UTF_8));
        return result.verified;
    }

    @AllArgsConstructor
    @RequiredArgsConstructor
    public static class ValidationResult {
        @NonNull
        public boolean valid;
        private List<String> errors = new ArrayList<>();

        void addError(String error) {
            valid = false;
            errors.add(error);
        }

        public String errorReasons() {
            return String.join(";\n", errors);
        }
    }

    public static ValidationResult validatePassword(String password) {

        ValidationResult validationRes = new ValidationResult(true);

        if (password.length() < 8) {
            validationRes.addError("Password length must be greater than 8 characters");
        }

        String upperCaseChars = "(.*[A-Z].*)";
        if (!password.matches(upperCaseChars)) {
            validationRes.addError("Password should contain at least one upper case alphabet");
        }

        String numbers = "(.*[0-9].*)";
        if (!password.matches(numbers)) {
            validationRes.addError("Password should contain at least one number");
        }
        return validationRes;
    }
}
