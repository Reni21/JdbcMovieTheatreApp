package com.theatre.movie.entity;

import lombok.*;

@NoArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class User {
    private Integer id;
    @NonNull
    private String login;
    @NonNull
    private String password;
    @NonNull
    private String email;
    @NonNull
    private Role role;
}
