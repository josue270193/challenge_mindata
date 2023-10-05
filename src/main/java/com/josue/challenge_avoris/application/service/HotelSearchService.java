package com.josue.challenge_avoris.application.service;

import com.josue.challenge_avoris.application.usercase.GetHotelSearch;
import com.josue.challenge_avoris.application.usercase.SaveHotelSearch;
import com.josue.challenge_avoris.domain.model.HotelSearch;
import com.josue.challenge_avoris.domain.repository.HotelSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class HotelSearchService implements GetHotelSearch, SaveHotelSearch {

  @Autowired
  private HotelSearchRepository hotelSearchRepository;

  @Override
  public Flux<HotelSearch> obtainSimilarBySearchId(String searchId) {
    return hotelSearchRepository.obtainSimilarBySearchId(searchId)
        .onErrorResume(error -> Flux.empty());
  }

  @Override
  public Mono<HotelSearch> save(HotelSearch entity) {
    return hotelSearchRepository.save(entity)
        .onErrorResume(error -> Mono.empty());
  }

}
