# Type-Safe API Integration with Spring Boot

A guide for creating type-safe API integrations using Spring Boot, demonstrating clean data modeling and robust deserialization.

## Technical Overview

- Type-safe API response modeling using Records
- JSON deserialization with Jackson
- REST client configuration
- Unit testing API integrations

## Requirements

- Java 17+
- Spring Boot 3.x
- Jackson for JSON processing

## Data Modeling

Model your API responses using records for immutable, type-safe data classes:

```java
public record GroupedDaily(
    Boolean adjusted,
    Integer queryCount,
    List<Result> results,
    Integer resultsCount,
    String status,
    String request_id
) {
    public record Result(
        @JsonProperty("T") String ticker,
        @JsonProperty("c") double close,
        @JsonProperty("h") double high,
        @JsonProperty("l") double low,
        @JsonProperty("n") int numberOfTrades,
        @JsonProperty("o") double open,
        @JsonProperty("t") long timestamp,
        @JsonProperty("v") double volume,
        @JsonProperty("vw") double vwap
    ) {}
}
```

## REST Client Setup

Configure a type-safe REST client using Spring's RestClient builder:

```java
@Bean
CommandLineRunner commandLineRunner(RestClient.Builder builder) {
    return args -> {
        var client = builder
                .baseUrl("http://localhost:8080")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();

        ResponseEntity<GroupedDaily> entity = client.get()
                .uri("/api/polygon")
                .retrieve()
                .toEntity(GroupedDaily.class);
    };
}
```

## API Controller

Create a REST controller to expose your API:

```java
@RestController
@RequestMapping("/api/polygon")
public class PolygonController {

    @RequestMapping("")
    public String getJSON() {
        return """
            {
              "adjusted": true,
              "queryCount": 3,
              "results": [
                {
                  "T": "KIMpL",
                  "c": 25.9102,
                  "h": 26.25,
                  "l": 25.91,
                  "n": 74,
                  "o": 26.07,
                  "t": 1602705600000,
                  "v": 4369,
                  "vw": 26.0407
                }
              ],
              "resultsCount": 3,
              "status": "OK"
            }
            """;
    }
}
```

## Testing

Test your API integration:

```java
@Test
void shouldParseJsonIntoGroupedDaily() throws Exception {
    // given
    String json = """
        {
          "adjusted": true,
          "queryCount": 3,
          "results": [
            {
              "T": "KIMpL",
              "c": 25.9102,
              "h": 26.25,
              "l": 25.91,
              "n": 74,
              "o": 26.07,
              "t": 1602705600000,
              "v": 4369,
              "vw": 26.0407
            }
          ],
          "resultsCount": 3,
          "status": "OK"
        }
        """;

    // when
    GroupedDaily groupedDaily = objectMapper.readValue(json, GroupedDaily.class);

    // then
    assertNotNull(groupedDaily);
    assertTrue(groupedDaily.adjusted());
    assertEquals(3, groupedDaily.queryCount());
    assertEquals(3, groupedDaily.resultsCount());
    assertEquals("OK", groupedDaily.status());
}
```

## Best Practices

1. Use records for immutable data models
2. Properly annotate JSON field mappings
3. Write comprehensive tests
4. Configure appropriate HTTP headers
5. Handle API errors gracefully

## Contributing

Contributions welcome! Please ensure:
1. Tests pass
2. New features include tests
3. Code follows existing style

## License

[Add your license information here]