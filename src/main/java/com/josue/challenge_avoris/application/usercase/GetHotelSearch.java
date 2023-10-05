package com.josue.challenge_avoris.application.usercase;

import com.josue.challenge_avoris.domain.model.HotelSearch;
import reactor.core.publisher.Flux;

public interface GetHotelSearch {

  Flux<HotelSearch> obtainSimilarBySearchId(String searchId);

}
