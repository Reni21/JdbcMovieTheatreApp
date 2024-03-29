package com.theatre.movie.web.command.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SuccessResponse implements CommandResponse {

    private String payload;

    @Override
    public CommandResponseType getResponseType() {
        return CommandResponseType.PAYLOAD;
    }
}
