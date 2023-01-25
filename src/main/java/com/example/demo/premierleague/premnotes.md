# Notes
## Kafka
- Enables live game events to be added to a topic
- Enables consumers to retrieve live game events
  - If consumer fails, the events will be retrieved again to get the goal updates
  - If producer fails, consumer waits until producer alive again

## Spring Reactive API
- Uses Kafka consumer to retrieve the live game events
- Asynchronous and non-blocking, waits until game event occurs

## Benefits
- Ability to format the messages and setup consumers for different groups
- Example = Consumer which retrieves teams to notify of score update
- Example = Consumer which retrieves score update for display purposes
- If kafka backend goes down, the non-blocking asynchronous API will remain
- If API goes down, the kafka consumer with the related group name will retrieve all messages missed