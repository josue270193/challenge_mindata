package com.josue.challenge_avoris.infrastructure.outbound.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {

  public static final String HOTEL_SEARCH_TOPIC = "hotel_availability_searches";
  private final int PARTITIONS = 5;
  private final int REPLICAS = 3;

  @Bean
  public NewTopic hotelAvailabilitySearchesTopic() {
    return TopicBuilder.name(HOTEL_SEARCH_TOPIC)
        .partitions(PARTITIONS)
        .replicas(REPLICAS)
        .build();
  }

}
