package com.theatre.movie.web.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponse implements CommandResponse {

    private int httpStatus;
    private String errorMsg;

    @Override
    public CommandResponseType getResponseType() {
        return CommandResponseType.ERROR;
    }
}
