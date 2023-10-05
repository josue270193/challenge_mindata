package com.josue.challenge_avoris.application.usercase;

import com.josue.challenge_avoris.domain.model.HotelSearch;
import reactor.core.publisher.Mono;

public interface SaveHotelSearch {

  Mono<HotelSearch> save(HotelSearch entity);

}
