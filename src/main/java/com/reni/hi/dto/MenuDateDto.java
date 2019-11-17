package com.reni.hi.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class MenuDateDto {
    @NonNull
    private LocalDate date;
    @Setter
    private boolean isActive;

    public String getIsoDate(){
        return "?date=" + date.format(DateTimeFormatter.ISO_DATE);
    }

    public String getDayOfWeek(){
        return date.getDayOfWeek().name().substring(0,3).toLowerCase();
    }

    public String getFormatedDate(){
        return " "+ date.format(DateTimeFormatter.ofPattern("dd:MM"));
    }
}
