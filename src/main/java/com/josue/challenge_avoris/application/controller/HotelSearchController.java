package com.josue.challenge_avoris.application.controller;

import com.josue.challenge_avoris.application.mapper.HotelSearchMapper;
import com.josue.challenge_avoris.application.request.HotelSearchRequest;
import com.josue.challenge_avoris.application.response.HotelCountResponse;
import com.josue.challenge_avoris.application.response.HotelSearchResponse;
import com.josue.challenge_avoris.application.service.HotelSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HotelSearchController {

  @Autowired
  private HotelSearchService hotelSearchService;

  @Autowired
  private HotelSearchMapper hotelSearchMapper;

  @PostMapping("/search")
  public Mono<HotelSearchResponse> saveSearch(@RequestBody HotelSearchRequest request) {
    return Mono.just(request)
        .map(hotelSearchMapper::fromSearchRequestToEntity)
        .flatMap(hotelSearchService::save)
        .map(hotelSearchMapper::fromEntityToSearchResponse);
  }

  @GetMapping("/count")
  public Mono<HotelCountResponse> countSearch(@RequestParam String searchId) {
    return hotelSearchService.obtainSimilarBySearchId(searchId)
        .collectList()
        .map(list -> hotelSearchMapper.fromEntityToCountResponse(searchId, list));
  }

}
