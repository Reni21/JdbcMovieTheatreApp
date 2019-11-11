package com.reni.hi.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class PageDto {
    @NonNull
    private String url;
    private boolean redirect;
    private String queryParams;

    public PageDto(@NonNull String url, boolean redirect) {
        this.url = url;
        this.redirect = redirect;
    }

    public PageDto(@NonNull String url, String queryParams) {
        this.url = url;
        this.queryParams = queryParams;
    }

    public boolean isRedirect() {
        return redirect;
    }

}
