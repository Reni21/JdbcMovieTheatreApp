package com.theatre.movie.web.command;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PayloadResponse implements CommandResponse {

    private String payload;

    public String getPayload() {
        return payload;
    }

    @Override
    public CommandResponseType getResponseType() {
        return CommandResponseType.PAYLOAD;
    }
}
