package com.josue.challenge_avoris.infrastructure.outbound.kafka.topic;

import java.time.LocalDate;
import java.util.List;

public record HotelSearchTopic(
    String searchId,
    String hotelId,
    LocalDate checkIn,
    LocalDate checkOut,
    List<Integer> ages
) {

}
