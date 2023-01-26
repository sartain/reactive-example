# Reactive Spring example
## Airport
This example uses a 3rd party open API found [here](https://airport-web.appspot.com/api/docs/).
The 3rd party API is called reactively, with links on reactive spring below
- [Spring WebFlux](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html#webflux)
- [Spring WebClient](https://www.baeldung.com/spring-5-webclient)
- [Spring WebClient Testing](https://www.learninjava.com/4-ways-to-test-webclient-mocking/)
- [Spring Reactive course](https://learning.oreilly.com/videos/reactive-spring-boot/9780137831463/)
- [Spring Reactive POST request](https://stackoverflow.com/questions/62073018/spring-webclient-post-method-body)

## Sunday League
This is an MVC example in Spring Boot where scores can be:
- Added with a POST request
- Retrieved with a GET request
It is not reactive.

## Premier League
This is a reactive example in Spring Boot with a '3rd party' Kafka backend.
Scores can be:
- Added to a kafka topic using a producer
- Retrieved asynchronously using a Spring Reactive endpoint 'GET' request

Related links:
- [Reactive Kafka](https://projectreactor.io/docs/kafka/release/reference/#_reactive_api_for_kafka)
- [Testing a WebFlux stream endpoint](https://blog.rpuch.com/2019/08/29/unit-testing-spring-webflux-streaming-controller.html)
- [Example vidiprinter / scoring system](https://www.skysports.com/live-scores)