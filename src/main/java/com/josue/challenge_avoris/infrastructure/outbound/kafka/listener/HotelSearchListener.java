package com.josue.challenge_avoris.infrastructure.outbound.kafka.listener;

import com.josue.challenge_avoris.infrastructure.outbound.kafka.config.KafkaConfiguration;
import com.josue.challenge_avoris.infrastructure.outbound.kafka.topic.HotelSearchTopic;
import com.josue.challenge_avoris.infrastructure.outbound.mongo.MongoRepository;
import com.josue.challenge_avoris.infrastructure.outbound.mongo.mapper.HotelSearchMongoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class HotelSearchListener {

  @Autowired
  private MongoRepository mongoRepository;

  @Autowired
  private HotelSearchMongoMapper hotelSearchMongoMapper;

  @KafkaListener(
      groupId = KafkaConfiguration.GROUP_ID,
      topics = KafkaConfiguration.HOTEL_SEARCH_TOPIC
  )
  public void listener(HotelSearchTopic data) {

    Mono.just(data)
        .map(hotelSearchMongoMapper::fromTopicToEntity)
        .flatMap(mongoRepository::save)
        .subscribe();
  }

}
