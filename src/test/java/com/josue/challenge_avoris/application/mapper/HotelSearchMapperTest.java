package com.josue.challenge_avoris.application.mapper;

import com.josue.challenge_avoris.application.request.HotelSearchRequest;
import com.josue.challenge_avoris.application.response.HotelCountResponse;
import com.josue.challenge_avoris.application.response.HotelSearchResponse;
import com.josue.challenge_avoris.domain.model.HotelSearch;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class HotelSearchMapperTest {

  @InjectMocks
  private HotelSearchMapper mapper;

  private HotelSearchRequest hotelSearchRequest;
  private HotelSearch hotelSearch;
  private HotelSearchResponse hotelSearchResponse;
  private HotelCountResponse hotelCountResponse;

  @BeforeEach
  void setUp() {
    hotelSearchRequest = null;
    hotelSearch = null;
    hotelSearchResponse = null;
    hotelCountResponse = null;
  }

  @Test
  void whenFromSearchRequestToEntityOkThenOk() {
    hotelSearchRequest = createMockRequest();
    hotelSearch = mapper.fromSearchRequestToEntity(hotelSearchRequest);
    Assertions.assertAll(
        () -> Assertions.assertNotNull(hotelSearch),
        () -> Assertions.assertNull(hotelSearch.searchId()),
        () -> Assertions.assertNotNull(hotelSearch.hotelId()),
        () -> Assertions.assertNotNull(hotelSearch.checkIn()),
        () -> Assertions.assertNotNull(hotelSearch.checkOut()),
        () -> Assertions.assertNotNull(hotelSearch.ages()),
        () -> Assertions.assertNull(hotelSearch.createdDate())
    );
  }

  @Test
  void whenFromSearchRequestToEntityNullThenNull() {
    hotelSearch = mapper.fromSearchRequestToEntity(hotelSearchRequest);
    Assertions.assertAll(
        () -> Assertions.assertNull(hotelSearch)
    );
  }

  @Test
  void whenFromEntityToSearchResponseOkThenOk() {
    hotelSearch = createMockDto();
    hotelSearchResponse = mapper.fromEntityToSearchResponse(hotelSearch);
    Assertions.assertAll(
        () -> Assertions.assertNotNull(hotelSearchResponse),
        () -> Assertions.assertNotNull(hotelSearchResponse.searchId()),
        () -> Assertions.assertFalse(hotelSearchResponse.searchId().isEmpty())
    );
  }

  @Test
  void whenFromEntityToSearchResponseNullThenNull() {
    hotelSearchResponse = mapper.fromEntityToSearchResponse(hotelSearch);
    Assertions.assertAll(
        () -> Assertions.assertNull(hotelSearchResponse)
    );
  }

  @Test
  void whenFromEntityToCountResponseOkThenOk() {
    int size = 2;
    var searchId = UUID.randomUUID().toString();
    hotelCountResponse = mapper.fromEntityToCountResponse(searchId, createMockListDto(size));
    Assertions.assertAll(
        () -> Assertions.assertNotNull(hotelCountResponse),
        () -> Assertions.assertNotNull(hotelCountResponse.searchId()),
        () -> Assertions.assertEquals(searchId, hotelCountResponse.searchId()),
        () -> Assertions.assertEquals(size, hotelCountResponse.count()),
        () -> Assertions.assertNotNull(hotelCountResponse.search()),
        () -> Assertions.assertNotNull(hotelCountResponse.search().hotelId()),
        () -> Assertions.assertNotNull(hotelCountResponse.search().checkIn()),
        () -> Assertions.assertNotNull(hotelCountResponse.search().checkOut()),
        () -> Assertions.assertNotNull(hotelCountResponse.search().ages())
    );
  }

  @Test
  void whenFromEntityToCountResponseNullThenNull() {
    int size = 0;
    var searchId = UUID.randomUUID().toString();
    hotelCountResponse = mapper.fromEntityToCountResponse(searchId, createMockListDto(size));
    Assertions.assertAll(
        () -> Assertions.assertNotNull(hotelCountResponse),
        () -> Assertions.assertNotNull(hotelCountResponse.searchId()),
        () -> Assertions.assertEquals(searchId, hotelCountResponse.searchId()),
        () -> Assertions.assertEquals(size, hotelCountResponse.count()),
        () -> Assertions.assertNull(hotelCountResponse.search())
    );
  }

  private List<HotelSearch> createMockListDto(int size) {
    var list = new ArrayList<HotelSearch>(size);
    while (size > 0) {
      list.add(createMockDto());
      size--;
    }
    return list;
  }

  private HotelSearch createMockDto() {
    return new HotelSearch(
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        LocalDate.of(2023, 1, 1),
        LocalDate.of(2023, 1, 1),
        List.of(1, 2),
        new Date()
    );
  }

  private HotelSearchRequest createMockRequest() {
    return new HotelSearchRequest(
        UUID.randomUUID().toString(),
        LocalDate.of(2023, 1, 1),
        LocalDate.of(2023, 1, 1),
        List.of(1, 2)
    );
  }

}