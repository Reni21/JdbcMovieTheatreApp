package com.theatre.movie.web;

import com.theatre.movie.entity.Role;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecurityConfig {

    private static Map<Role, List<String>> securityPages = new HashMap<>();

    static {
        securityPages.put(Role.ROLE_ADMIN, Arrays.asList("/admin", "/account", "/movies"));
        securityPages.put(Role.ROLE_USER, Arrays.asList("/user", "/account", "/booking"));
    }

    public static boolean isSecurePage(String page) {
        return securityPages.values().stream()
                .anyMatch(list -> list.stream()
                        .anyMatch(pageValue -> pageValue.equals(page)));
    }

    public static boolean hasPermission(String page, Role role) {
        return securityPages.containsKey(role) && securityPages.get(role).contains(page);
    }
}
