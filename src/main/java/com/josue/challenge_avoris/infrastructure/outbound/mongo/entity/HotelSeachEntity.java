package com.josue.challenge_avoris.infrastructure.outbound.mongo.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("hotel_search")
public record HotelSeachEntity(
    @Id
    String searchId,
    String hotelId,
    LocalDate checkIn,
    LocalDate checkOut,
    List<Integer> ages,
    @CreatedDate
    Date createdDate
) {

}
