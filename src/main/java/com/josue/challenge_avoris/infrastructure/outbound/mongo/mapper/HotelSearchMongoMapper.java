package com.josue.challenge_avoris.infrastructure.outbound.mongo.mapper;

import com.josue.challenge_avoris.domain.model.HotelSearch;
import com.josue.challenge_avoris.infrastructure.outbound.kafka.topic.HotelSearchTopic;
import com.josue.challenge_avoris.infrastructure.outbound.mongo.entity.HotelSeachEntity;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class HotelSearchMongoMapper {

  public HotelSeachEntity fromDtoToEntity(HotelSearch dto) {
    return new HotelSeachEntity(
        UUID.randomUUID().toString(),
        dto.hotelId(),
        dto.checkIn(),
        dto.checkOut(),
        dto.ages(),
        null);
  }

  public HotelSearch fromEntityToDto(HotelSeachEntity entity) {
    return new HotelSearch(
        entity.searchId(),
        entity.hotelId(),
        entity.checkIn(),
        entity.checkOut(),
        entity.ages(),
        entity.createdDate());
  }

  public HotelSeachEntity fromTopicToEntity(HotelSearchTopic topic) {
    return new HotelSeachEntity(
        topic.searchId(),
        topic.hotelId(),
        topic.checkIn(),
        topic.checkOut(),
        topic.ages(),
        null);
  }

}
