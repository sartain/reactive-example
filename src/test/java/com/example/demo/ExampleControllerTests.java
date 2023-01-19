package com.example.demo;

import com.example.demo.persistence.ICAOData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class ExampleControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void test_getPost() throws Exception {
        ICAOData icaoData = new ICAOData("EDLW", "something", "something", "something");
        this.webTestClient.get().uri("/airport").accept(MediaType.APPLICATION_JSON).exchange()
                .expectStatus().isOk()
                .expectBody(ICAOData.class).isEqualTo(icaoData);
    }

}
