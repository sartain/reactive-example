package com.example.demo;

import com.example.demo.persistence.IcaoData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class ExampleControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testGetSingleAirport() throws Exception {
        IcaoData icaoData = new IcaoData("EDLW", "something", "something", "something");
        this.webTestClient.get().uri("/airport").accept(MediaType.APPLICATION_JSON).exchange()
                .expectStatus().isOk()
                .expectBody(IcaoData.class).isEqualTo(icaoData);
    }

    @Test
    public void testGetSingleAirportPathVariable() throws Exception {
        IcaoData icaoData = new IcaoData("EDDE", "something", "something", "something");
        this.webTestClient.get().uri("/airport/EDDE").accept(MediaType.APPLICATION_JSON).exchange()
                .expectStatus().isOk()
                .expectBody(IcaoData.class).isEqualTo(icaoData);
    }

    @Test
    public void testRouteExample() throws Exception {
        IcaoData icaoData = new IcaoData("EDLW", "something", "something", "something");
        this.webTestClient.get().uri("/plane").accept(MediaType.APPLICATION_JSON).exchange()
                .expectStatus().isOk()
                .expectBody(IcaoData.class).isEqualTo(icaoData);
    }

    @Test
    public void testGetAirports() throws Exception {
        this.webTestClient.get().uri("/airports").accept(MediaType.APPLICATION_JSON).exchange()
                .expectStatus().isOk()
                .expectBodyList(IcaoData.class);
    }

    @Test
    public void testPostAirport() throws Exception {
        IcaoData dataToAdd = new IcaoData("EDLW", "something", "something", "something");
        this.webTestClient.post().uri("/airport").accept(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(dataToAdd))
                .exchange()
                .expectStatus().isOk()
                .expectBody(IcaoData.class).isEqualTo(dataToAdd);
    }



}
