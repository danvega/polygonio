package dev.danvega.polygonio;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroupedDailyTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

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
                },
                {
                  "T": "TANH",
                  "c": 23.4,
                  "h": 24.763,
                  "l": 22.65,
                  "n": 1096,
                  "o": 24.5,
                  "t": 1602705600000,
                  "v": 25933,
                  "vw": 23.493
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
        assertNull(groupedDaily.request_id()); // Not present in JSON

        List<GroupedDaily.Result> results = groupedDaily.results();
        assertEquals(2, results.size());

        // Verify first result
        GroupedDaily.Result firstResult = results.get(0);
        assertEquals("KIMpL", firstResult.ticker());
        assertEquals(25.9102, firstResult.close());
        assertEquals(26.25, firstResult.high());
        assertEquals(25.91, firstResult.low());
        assertEquals(74, firstResult.numberOfTrades());
        assertEquals(26.07, firstResult.open());
        assertEquals(1602705600000L, firstResult.timestamp());
        assertEquals(4369, firstResult.volume());
        assertEquals(26.0407, firstResult.vwap());

        // Verify second result
        GroupedDaily.Result secondResult = results.get(1);
        assertEquals("TANH", secondResult.ticker());
        assertEquals(23.4, secondResult.close());
        assertEquals(24.763, secondResult.high());
        assertEquals(22.65, secondResult.low());
        assertEquals(1096, secondResult.numberOfTrades());
        assertEquals(24.5, secondResult.open());
        assertEquals(1602705600000L, secondResult.timestamp());
        assertEquals(25933, secondResult.volume());
        assertEquals(23.493, secondResult.vwap());
    }
}