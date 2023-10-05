package com.josue.challenge_avoris.infrastructure.outbound.mongo;

import com.josue.challenge_avoris.infrastructure.outbound.mongo.entity.HotelSeachEntity;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MongoRepository extends ReactiveCrudRepository<HotelSeachEntity, String> {

  @Query("{ hotelId: ?0, checkIn: ?1, checkOut: ?2, ages: {$all: ?3} }")
  Flux<HotelSeachEntity> findSimilary(String hotelId, LocalDate checkIn, LocalDate checkOut,
      List<Integer> ages);

}
