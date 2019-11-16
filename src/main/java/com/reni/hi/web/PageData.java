package com.reni.hi.web;

import lombok.*;

import java.util.Optional;

@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class PageData {
    @NonNull
    private String url;
    private boolean redirect;
    private String queryParams;

    public PageData(@NonNull String url, boolean redirect) {
        this.url = url;
        this.redirect = redirect;
    }

    public PageData(@NonNull String url, String queryParams) {
        this.url = url;
        this.queryParams = queryParams;
    }

    public String getUrl() {
        //return url + "?date=" + queryParams;
        // return Optional.ofNullable(queryParams).map(q -> url + "?" + q).orElse(url);
        return url;
    }

    public boolean isRedirect() {
        return redirect;
    }

}
