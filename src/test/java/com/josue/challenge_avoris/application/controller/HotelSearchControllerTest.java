package com.josue.challenge_avoris.application.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.josue.challenge_avoris.application.mapper.HotelSearchMapper;
import com.josue.challenge_avoris.application.request.HotelSearchRequest;
import com.josue.challenge_avoris.application.response.HotelCountResponse;
import com.josue.challenge_avoris.application.response.HotelSearchResponse;
import com.josue.challenge_avoris.application.service.HotelSearchService;
import com.josue.challenge_avoris.domain.model.HotelSearch;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = HotelSearchController.class)
class HotelSearchControllerTest {

  @Autowired
  private WebTestClient webClient;

  @MockBean
  private HotelSearchService hotelSearchService;

  @MockBean
  private HotelSearchMapper hotelSearchMapper;

  private final String PATH_SEARCH = "/search";
  private final String PATH_COUNT = "/count";

  @BeforeEach
  void setUp() {

  }

  @Test
  void whenSearchSaveOkThenOk() {
    var request = new HotelSearchRequest(
        UUID.randomUUID().toString(),
        LocalDate.of(2023, 1, 1),
        LocalDate.of(2023, 1, 1),
        List.of(1, 2)
    );

    setUpMockSearchSave(request);

    webClient.post()
        .uri(PATH_SEARCH)
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(request))
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$.searchId").exists();
    verify(hotelSearchService, times(1)).save(any());
    verify(hotelSearchMapper, times(1)).fromSearchRequestToEntity(any());
    verify(hotelSearchMapper, times(1)).fromEntityToSearchResponse(any());
  }

  @Test
  void whenSearchSaveMissingThenError() {
    var request = new HotelSearchRequest(
        null,
        null,
        null,
        null
    );

    webClient.post()
        .uri(PATH_SEARCH)
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(request))
        .exchange()
        .expectStatus()
        .is4xxClientError();
    verify(hotelSearchService, times(0)).save(any());
    verify(hotelSearchMapper, times(0)).fromSearchRequestToEntity(any());
    verify(hotelSearchMapper, times(0)).fromEntityToSearchResponse(any());
  }

  @Test
  void whenSearchSaveMissingCheckInThenError() {
    var request = new HotelSearchRequest(
        UUID.randomUUID().toString(),
        null,
        LocalDate.of(2023, 1, 1),
        List.of(1)
    );

    webClient.post()
        .uri(PATH_SEARCH)
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(request))
        .exchange()
        .expectStatus()
        .is4xxClientError();
    verify(hotelSearchService, times(0)).save(any());
    verify(hotelSearchMapper, times(0)).fromSearchRequestToEntity(any());
    verify(hotelSearchMapper, times(0)).fromEntityToSearchResponse(any());
  }

  @Test
  void whenSearchSaveMissingCheckOutThenError() {
    var request = new HotelSearchRequest(
        UUID.randomUUID().toString(),
        LocalDate.of(2023, 1, 1),
        null,
        List.of(1)
    );

    webClient.post()
        .uri(PATH_SEARCH)
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(request))
        .exchange()
        .expectStatus()
        .is4xxClientError();
    verify(hotelSearchService, times(0)).save(any());
    verify(hotelSearchMapper, times(0)).fromSearchRequestToEntity(any());
    verify(hotelSearchMapper, times(0)).fromEntityToSearchResponse(any());
  }

  @Test
  void whenSearchSaveMissingAgeThenError() {
    var request = new HotelSearchRequest(
        UUID.randomUUID().toString(),
        LocalDate.of(2023, 1, 1),
        LocalDate.of(2023, 1, 1),
        null
    );

    webClient.post()
        .uri(PATH_SEARCH)
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(request))
        .exchange()
        .expectStatus()
        .is4xxClientError();
    verify(hotelSearchService, times(0)).save(any());
    verify(hotelSearchMapper, times(0)).fromSearchRequestToEntity(any());
    verify(hotelSearchMapper, times(0)).fromEntityToSearchResponse(any());
  }

  @Test
  void whenSearchSaveMissingHotelIdThenError() {
    var request = new HotelSearchRequest(
        null,
        LocalDate.of(2023, 1, 1),
        LocalDate.of(2023, 1, 1),
        List.of(1)
    );

    webClient.post()
        .uri(PATH_SEARCH)
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(request))
        .exchange()
        .expectStatus()
        .is4xxClientError();
    verify(hotelSearchService, times(0)).save(any());
    verify(hotelSearchMapper, times(0)).fromSearchRequestToEntity(any());
    verify(hotelSearchMapper, times(0)).fromEntityToSearchResponse(any());
  }

  @Test
  void whenCountSearchOkThenOk() {
    var searchId = UUID.randomUUID().toString();

    setUpMockCountSearch(searchId);

    webClient.get()
        .uri(uriBuilder -> uriBuilder.path(PATH_COUNT)
            .queryParam("searchId", searchId)
            .build())
        .exchange()
        .expectStatus()
        .isOk();
    verify(hotelSearchService, times(0)).save(any());
    verify(hotelSearchMapper, times(0)).fromSearchRequestToEntity(any());
    verify(hotelSearchMapper, times(0)).fromEntityToSearchResponse(any());
    verify(hotelSearchService, times(1)).obtainSimilarBySearchId(any());
    verify(hotelSearchMapper, times(1)).fromEntityToCountResponse(any(), any());
  }

  @Test
  void whenCountSearchMissingSearchIdThenOk() {
    webClient.get()
        .uri(uriBuilder -> uriBuilder.path(PATH_COUNT)
            .build())
        .exchange()
        .expectStatus()
        .is4xxClientError();
    verify(hotelSearchService, times(0)).save(any());
    verify(hotelSearchMapper, times(0)).fromSearchRequestToEntity(any());
    verify(hotelSearchMapper, times(0)).fromEntityToSearchResponse(any());
    verify(hotelSearchService, times(0)).obtainSimilarBySearchId(any());
    verify(hotelSearchMapper, times(0)).fromEntityToCountResponse(any(), any());
  }


  private void setUpMockSearchSave(HotelSearchRequest request) {
    var searchId = UUID.randomUUID().toString();
    var dto = new HotelSearch(searchId, request.hotelId(), request.checkIn(), request.checkOut(),
        request.ages(), new Date());
    var response = new HotelSearchResponse(searchId);

    when(hotelSearchMapper.fromSearchRequestToEntity(any(HotelSearchRequest.class)))
        .thenReturn(dto);
    when(hotelSearchService.save(any(HotelSearch.class)))
        .thenReturn(Mono.just(dto));
    when(hotelSearchMapper.fromEntityToSearchResponse(any(HotelSearch.class)))
        .thenReturn(response);
  }

  private void setUpMockCountSearch(String searchId) {
    var list = List.<HotelSearch>of();
    var response = new HotelCountResponse(searchId, null, list.size());

    when(hotelSearchService.obtainSimilarBySearchId(eq(searchId)))
        .thenReturn(Flux.fromIterable(list));
    when(hotelSearchMapper.fromEntityToCountResponse(eq(searchId), anyList()))
        .thenReturn(response);
  }

}