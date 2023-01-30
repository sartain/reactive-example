package com.example.demo.premierleague;

import com.example.demo.premierleague.controllers.WebFluxScoreController;
import com.example.demo.premierleague.services.ScoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import reactor.core.publisher.Flux;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity;

@WebFluxTest
@ContextConfiguration(classes = TestConfiguration.class)
class PremierLeagueWebFluxTests {

    private WebTestClient webClient;
    private WebTestClient secureWebClient;

    @Autowired
    ApplicationContext context;

    @MockBean
    private ScoreService service;

    @BeforeEach
    void initClient() {
        webClient = WebTestClient.bindToController(new WebFluxScoreController(service)).build();
        secureWebClient = WebTestClient.bindToApplicationContext(this.context)
                .apply(springSecurity())
                .configureClient()
                .filter(ExchangeFilterFunctions.basicAuthentication("user", "password"))
                .build();
    }

    /**
     * Test the stream receives scores
     * Mock the service linked to kafka to return some mock data
     * Call the endpoint using mock service and retrieve mock data back
     * Assert actual mock data equals the expected mock data
     */

    @Test
    void scoreStreams() {
        //Return Mock data in service response
        String[] scoreArray = {
                "Everton 1-0 Fulham",
                "Tottenham 2-2 Leeds",
                "Manchester United 0-3 Crystal Palace"
        };
        when(service.getScoresViaCall())
                .thenReturn(Flux.just(scoreArray));
        //Call the client using the mock service
        List<String> scoreList = webClient.get().uri("/kafka/webflux")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(String.class)
                .returnResult()
                .getResponseBody();

        //Test the response from the client using mock service
        assertEquals(3, scoreList.size());
        scoreList.forEach(System.out::println);
        assertEquals(scoreList, Arrays.asList(scoreArray));
    }

    @Test
    void scoreStreamsWithAuth() {
        //Return Mock data in service response
        String[] scoreArray = {
                "Everton 1-0 Fulham",
                "Tottenham 2-2 Leeds",
                "Manchester United 0-3 Crystal Palace"
        };
        when(service.getScoresViaAuthCall())
                .thenReturn(Flux.just(scoreArray));

        //Call the client using the mock service
        List<String> scoreList = secureWebClient.get().uri("/auth/kafka/webflux")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .headers(headers -> headers.setBasicAuth("user", "password"))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(String.class)
                .returnResult()
                .getResponseBody();

        //Test the response from the client using mock service
        assertEquals(3, scoreList.size());
        scoreList.forEach(System.out::println);
        assertEquals(scoreList, Arrays.asList(scoreArray));
    }
}
