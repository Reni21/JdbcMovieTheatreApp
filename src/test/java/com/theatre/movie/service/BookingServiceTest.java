package com.theatre.movie.service;

import com.theatre.movie.dao.BookingDao;
import com.theatre.movie.dto.BookingViewDto;
import com.theatre.movie.persistence.transaction.TransactionHandler;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookingServiceTest {

    private BookingService instance;
    private BookingDao bookingDao = mock(BookingDao.class);
    private TransactionHandler transactionHandler = mock(TransactionHandler.class);

    @Before
    public void setUp() {
        instance = new BookingService(bookingDao, transactionHandler);
    }

    @Test
    public void shouldGetActualBookingForUser() {

        BookingViewDto bookingViewDto = new BookingViewDto(1, "Titanic", 120, LocalDateTime.now(), "red", 8, 9);
        List<BookingViewDto> bookings = Collections.singletonList(bookingViewDto);
        when(bookingDao.getAllActualBookingByUserId(1)).thenReturn(bookings);

        List<BookingViewDto> actual = instance.getActualUsersBookingById(1);
        assertThat(actual).isEqualTo(bookings);
    }
}