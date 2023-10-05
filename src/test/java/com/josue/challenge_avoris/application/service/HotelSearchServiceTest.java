package com.josue.challenge_avoris.application.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.josue.challenge_avoris.domain.model.HotelSearch;
import com.josue.challenge_avoris.domain.repository.HotelSearchRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
class HotelSearchServiceTest {

  @Mock
  private HotelSearchRepository hotelSearchRepository;

  @InjectMocks
  private HotelSearchService service;

  @BeforeEach
  void setUp() {
  }

  @Test
  void whenObtainSimilarBySearchIdEmptyThenOk() {
    var searchId = UUID.randomUUID().toString();
    setUpMockCountSearch(searchId, 0);

    var result = service.obtainSimilarBySearchId(searchId);
    StepVerifier.create(result)
        .expectNextCount(0)
        .verifyComplete();
    verify(hotelSearchRepository, times(1)).obtainSimilarBySearchId(any());
  }

  @Test
  void whenObtainSimilarBySearchIdOkThenOk() {
    var searchId = UUID.randomUUID().toString();
    var size = 2;
    setUpMockCountSearch(searchId, size);

    var result = service.obtainSimilarBySearchId(searchId);
    StepVerifier.create(result)
        .expectNextCount(2)
        .verifyComplete();
    verify(hotelSearchRepository, times(1)).obtainSimilarBySearchId(any());
  }

  @Test
  void whenObtainSimilarBySearchErrorThenEmpty() {
    var searchId = UUID.randomUUID().toString();
    when(hotelSearchRepository.obtainSimilarBySearchId(eq(searchId)))
        .thenReturn(Flux.error(new NullPointerException()));

    var result = service.obtainSimilarBySearchId(searchId);
    StepVerifier.create(result)
        .expectNextCount(0)
        .verifyComplete();
    verify(hotelSearchRepository, times(1)).obtainSimilarBySearchId(any());
  }

  @Test
  void whenSaveOkThenOk() {
    var dto = createMockDto();
    setUpMockSaveSearch(dto);

    var result = service.save(dto);
    StepVerifier.create(result)
        .expectNextCount(1)
        .verifyComplete();
    verify(hotelSearchRepository, times(1)).save(any());
  }

  private void setUpMockCountSearch(String searchId, int size) {
    var list = new ArrayList<HotelSearch>(size);
    while (size > 0) {
      list.add(createMockDto());
      size--;
    }

    when(hotelSearchRepository.obtainSimilarBySearchId(eq(searchId)))
        .thenReturn(Flux.fromIterable(list));
  }

  private void setUpMockSaveSearch(HotelSearch dto) {
    var modified = createMockDto();
    when(hotelSearchRepository.save(eq(dto)))
        .thenReturn(Mono.just(modified));
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

}