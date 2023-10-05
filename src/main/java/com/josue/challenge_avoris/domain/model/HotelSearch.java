package com.josue.challenge_avoris.domain.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public record HotelSearch(
    String searchId,
    String hotelId,
    LocalDate checkIn,
    LocalDate checkOut,
    List<Integer> ages,
    Date createdDate
) {

}
