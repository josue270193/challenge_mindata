package com.josue.challenge_avoris.application.mapper;

import com.josue.challenge_avoris.application.request.HotelSearchRequest;
import com.josue.challenge_avoris.application.response.HotelCountResponse;
import com.josue.challenge_avoris.application.response.HotelCountResponse.HotelCountSearchResponse;
import com.josue.challenge_avoris.application.response.HotelSearchResponse;
import com.josue.challenge_avoris.domain.model.HotelSearch;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class HotelSearchMapper {

  public HotelSearch fromSearchRequestToEntity(HotelSearchRequest request) {
    HotelSearch entity = null;

    if (request != null) {
      entity = new HotelSearch(null,
          request.hotelId(),
          request.checkIn(),
          request.checkOut(),
          request.ages(),
          null
      );
    }

    return entity;
  }

  public HotelSearchResponse fromEntityToSearchResponse(HotelSearch entity) {
    HotelSearchResponse response = null;
    if (entity != null) {
      response = new HotelSearchResponse(entity.searchId());
    }
    return response;
  }

  public HotelCountResponse fromEntityToCountResponse(String searchId, List<HotelSearch> list) {
    var searchItem = list.stream()
        .findFirst()
        .map(hotelSearch -> new HotelCountSearchResponse(
            hotelSearch.hotelId(),
            hotelSearch.checkIn(),
            hotelSearch.checkOut(),
            hotelSearch.ages()
        ))
        .orElse(null);

    return new HotelCountResponse(
        searchId,
        searchItem,
        list.size()
    );
  }
}
