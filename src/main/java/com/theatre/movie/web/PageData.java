package com.theatre.movie.web;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class PageData {
    @NonNull
    private String url;
    private boolean redirect;

    public String getUrl() {
        return url;
    }

    public boolean isRedirect() {
        return redirect;
    }
}
