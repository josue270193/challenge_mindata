package com.josue.challenge_avoris.domain.repository;

import com.josue.challenge_avoris.domain.model.HotelSearch;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HotelSearchRepository {

  Mono<HotelSearch> save(HotelSearch entity);

  Flux<HotelSearch> obtainSimilarBySearchId(String searchId);

}
