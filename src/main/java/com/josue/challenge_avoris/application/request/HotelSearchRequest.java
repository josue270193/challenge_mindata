package com.josue.challenge_avoris.application.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public record HotelSearchRequest(
    @NotEmpty
    String hotelId,
    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate checkIn,
    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate checkOut,
    @NotNull
    List<Integer> ages
) {

}
