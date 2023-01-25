---
marp: true
---

# Spring WebFlux

## Alex Sartain

---

# WebFlux comparison with Spring MVC

- WebFlux is fully synchronous and non-blocking whilst Spring MVC is not
- WebFlux is useful for scalability, but in most instances isn't necessary
  - A system which looks up data in a database and returns it is more suited for Spring MVC
  - A system which makes use of microservices and has many external calls is more suited for WebFlux
- WebFlux introduces 'Mono' and 'Flux' which are 'Publisher' types for asynchronous stream processing

  - A stream is some data e.g. score updates for football matches
  - A system which needs to respond to a stream is more suited to WebFlux

    (https://www.squeed.com/julkalender-2022/spring-mvc-or-spring-webflux/)

---

# Example

## Score Update Application

- Local Sunday League

  - Games are scored by the referee and handed in to league official after the game
  - After all games are played, the league official adds the results to the system
  - Hundreds of people are interested in the results

- Professional League

  - Game events are scored throughout the game
  - During the game, score updates and events are added to the system
  - Millions of people are interested in the game events as soon as they happen

---

# Spring MVC Service Example

```java
    public List<Score> getSundayLeagueScores() {
        return scoreDao.getScores("sunday-league");
    }

    public List<Score> getPremierLeagueScores() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Score>> response = restTemplate.exchange("http://premierleague.com/scores",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Score>>(){});

        List<Score> result = response.getBody();
        result.forEach(result -> log.info(result.toString()));
        return result;
    }
```

---

# WebFlux example

```java
    public Flux<Score> getSundayLeagueScores() {
        return scoreDao.getScores("sunday-league");
    }

    private final WebClient client = WebClient.builder().baseUrl("http://premierleague.com").build();

    public Flux<String> getPremierLeagueScores() {
        return client.get()
                .uri("/scores")
                .retrieve()
                .bodyToFlux(String.class);
    }
```
