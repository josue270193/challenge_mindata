package com.josue.challenge_avoris.application.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;

public record HotelSearchRequest(
    String hotelId,
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate checkIn,
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate checkOut,
    List<Integer> ages
) {

}
