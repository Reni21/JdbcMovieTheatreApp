package com.theatre.movie.web.command;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class PageResponse implements CommandResponse {
    @NonNull
    private String url;
    private boolean redirect;

    public String getUrl() {
        return url;
    }

    public boolean isRedirect() {
        return redirect;
    }

    @Override
    public CommandResponseType getResponseType() {
        return CommandResponseType.PAGE;
    }
}
