package dev.danvega.polygonio;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GroupedDaily(
        Boolean adjusted,
        Integer queryCount,
        List<Result> results,
        Integer resultsCount,
        String status,
        String request_id
) {

    public record Result(
            @JsonProperty("T") String ticker,        // Ticker symbol
            @JsonProperty("c") double close,         // Close price
            @JsonProperty("h") double high,          // High price
            @JsonProperty("l") double low,           // Low price
            @JsonProperty("n") int numberOfTrades,   // Number of trades
            @JsonProperty("o") double open,          // Open price
            @JsonProperty("t") long timestamp,       // Timestamp in milliseconds
            @JsonProperty("v") double volume,        // Volume
            @JsonProperty("vw") double vwap          // Volume-weighted average price
    ) {}

}
