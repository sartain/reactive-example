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

## Setup
- Start Zookeeper and Kafka
- Create Kafka topic `scores` if not already created
- Create and launch Kafka Producer
- Make API call to the reactive endpoint
- Add score updates to the producer
- View results in reactive endpoint

## Things to try
- Shut down Kafka server and view the call to the endpoint
  - Attempt another endpoint call
  - Restart Kafka server
- Shut down endpoint call
  - Make score updates using Kafka Producer
  - Restart application endpoint
  - View missed calls appearing