package com.theatre.movie.util;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Passwords {

    public static String hash(String password) {
        return new String(BCrypt.withDefaults().hash(12, password.getBytes(StandardCharsets.UTF_8)));
    }

    public static boolean verifyHash(String password, String hash) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.getBytes(StandardCharsets.UTF_8), hash.getBytes(StandardCharsets.UTF_8));
        return result.verified;
    }
}
