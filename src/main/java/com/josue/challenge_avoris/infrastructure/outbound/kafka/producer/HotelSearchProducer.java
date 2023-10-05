package com.josue.challenge_avoris.infrastructure.outbound.kafka.producer;

import com.josue.challenge_avoris.infrastructure.outbound.kafka.config.KafkaConfiguration;
import com.josue.challenge_avoris.infrastructure.outbound.kafka.topic.HotelSearchTopic;
import com.josue.challenge_avoris.infrastructure.outbound.mongo.entity.HotelSeachEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class HotelSearchProducer {

  @Autowired
  private KafkaTemplate<String, HotelSearchTopic> kafkaTemplate;

  public void sendMessage(HotelSeachEntity dataToSend) {
    HotelSearchTopic topic = null;
    if (dataToSend != null) {
      topic = new HotelSearchTopic(
          dataToSend.searchId(),
          dataToSend.hotelId(),
          dataToSend.checkIn(),
          dataToSend.checkOut(),
          dataToSend.ages()
      );
    }

    if (topic != null) {
      Message<HotelSearchTopic> message = MessageBuilder
          .withPayload(topic)
          .setHeader(KafkaHeaders.TOPIC, KafkaConfiguration.HOTEL_SEARCH_TOPIC)
          .build();

      kafkaTemplate.send(message);
    }
  }

}
