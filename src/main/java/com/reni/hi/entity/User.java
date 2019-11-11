package com.reni.hi.entity;

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
    private Role role;
    private String email;

}
