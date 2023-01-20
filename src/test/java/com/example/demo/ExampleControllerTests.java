package com.example.demo;

import com.example.demo.persistence.ICAOData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.lang.reflect.Array;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class ExampleControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testGetSingleAirpotr() throws Exception {
        ICAOData icaoData = new ICAOData("EDLW", "something", "something", "something");
        this.webTestClient.get().uri("/airport").accept(MediaType.APPLICATION_JSON).exchange()
                .expectStatus().isOk()
                .expectBody(ICAOData.class).isEqualTo(icaoData);
    }

    @Test
    public void testRouteExample() throws Exception {
        ICAOData icaoData = new ICAOData("EDLW", "something", "something", "something");
        this.webTestClient.get().uri("/plane").accept(MediaType.APPLICATION_JSON).exchange()
                .expectStatus().isOk()
                .expectBody(ICAOData.class).isEqualTo(icaoData);
    }

    @Test
    public void testGetAirports() throws Exception {
        this.webTestClient.get().uri("/airports").accept(MediaType.APPLICATION_JSON).exchange()
                .expectStatus().isOk()
                .expectBodyList(ICAOData.class);
    }



}
