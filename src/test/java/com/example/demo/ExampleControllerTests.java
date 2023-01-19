package com.example.demo;

import com.example.demo.controllers.ExampleController;
import com.example.demo.data.ICAOData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ExampleControllerTests {

    @Mock
    private WebClient webClientMock;
    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpecMock;
    @Mock
    private WebClient.RequestBodySpec requestBodySpecMock;
    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpecMock;
    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;
    @Mock
    private WebClient.ResponseSpec responseSpecMock;
    @InjectMocks
    private ExampleController controllerMock;

    @Test
    public void test_getPost() throws Exception {
        ICAOData icaoData = new ICAOData("EDLW", "something", "something", "something");
        when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(anyString())).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(
                ArgumentMatchers.<Class<ICAOData>>notNull())).thenReturn(Mono.just(icaoData));
        Mono<ICAOData> response = controllerMock.singleAirport();
        Assertions.assertEquals("EDLW", response.block().getIcao());
    }

}
