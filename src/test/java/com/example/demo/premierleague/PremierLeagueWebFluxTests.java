package com.example.demo.premierleague;

import com.example.demo.premierleague.controllers.WebFluxScoreController;
import com.example.demo.premierleague.services.ScoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WebFluxTest
@ContextConfiguration(classes = TestConfiguration.class)
class PremierLeagueWebFluxTests {

    private WebTestClient webClient;

    @MockBean
    private ScoreService service;

    private String[] scoreArray = {"Everton 1-0 Fulham", "Tottenham 2-2 Leeds", "Manchester United 0-3 Crystal Palace"};

    @BeforeEach
    void initClient() {
        webClient = WebTestClient.bindToController(new WebFluxScoreController(service)).build();
    }

    @Test
    void scoreStreams() {
        when(service.getScoresViaCall())
                .thenReturn(Flux.just(scoreArray));

        List<String> scoreList = webClient.get().uri("/kafka/webflux")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(String.class)
                .returnResult()
                .getResponseBody();
        assertEquals(3, scoreList.size());
        scoreList.forEach(System.out::println);
        assertEquals(scoreList, Arrays.asList(scoreArray));
    }
}
