package com.josue.challenge_avoris.application.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;

public record HotelCountResponse(
    String searchId,
    HotelCountSearchResponse search,
    Integer count
) {

  public record HotelCountSearchResponse(
      String hotelId,
      @JsonFormat(pattern = "dd/MM/yyyy")
      LocalDate checkIn,
      @JsonFormat(pattern = "dd/MM/yyyy")
      LocalDate checkOut,
      List<Integer> ages
  ) {
  }
}
