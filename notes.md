# Reactive Spring (WebFlux) notes
## Main differences
- WebFlux is asynchronous = non-blocking
- WebFlux is not always required, useful for scalability as a thread pool is used vs single thread for each call
- If application is mostly communicating with a SQL database, WebFlux not as useful
- Any blocking on WebFlux blocks the entire server
- Mono (0..1 values)
- Flux (0..N values)
- Mono and Flux both publishers
- Can compose logic on flux and mono without the values having to be there, kind of like a promise
- Use streams to do this
- Can begin to use Reactive on servlet e.g. same GETMapping but return a Flux/Mono
- Flux can be O..N, how to know N?
  - Media type
    - JSON array = finite & accumulate all data before returning
    - Text Event Stream / Stream JSON = Request some items from the source, get a stream of information back
    - The connection exists between client/server works for examples like a chatroom
- Small number of users and low concurrency = not reactive