package com.josue.challenge_avoris.infrastructure.outbound;

import com.josue.challenge_avoris.domain.model.HotelSearch;
import com.josue.challenge_avoris.domain.repository.HotelSearchRepository;
import com.josue.challenge_avoris.infrastructure.outbound.kafka.producer.HotelSearchProducer;
import com.josue.challenge_avoris.infrastructure.outbound.mongo.MongoRepository;
import com.josue.challenge_avoris.infrastructure.outbound.mongo.mapper.HotelSearchMongoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class HotelSearchRepositoryImpl implements HotelSearchRepository {

  @Autowired
  private MongoRepository mongoRepository;

  @Autowired
  private HotelSearchProducer hotelSearchProducer;

  @Autowired
  private HotelSearchMongoMapper hotelSearchMongoMapper;

  @Override
  public Mono<HotelSearch> save(HotelSearch dto) {
    return Mono.just(dto)
        .map(hotelSearchMongoMapper::fromDtoToEntity)
        .doOnNext(hotelSearchProducer::sendMessage)
        .map(hotelSearchMongoMapper::fromEntityToDto);
  }

  @Override
  public Flux<HotelSearch> obtainSimilarBySearchId(String searchId) {
    return mongoRepository.findById(searchId)
            .flatMapMany(entity -> mongoRepository.findSimilary(
                entity.hotelId(),
                entity.checkIn(),
                entity.checkOut(),
                entity.ages())
            )
            .map(hotelSearchMongoMapper::fromEntityToDto);
  }

}
