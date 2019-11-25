package com.theatre.movie.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateUserRequestDto {
    private String login;
    private String password;
    private String email;
}
