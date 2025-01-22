package dev.danvega.polygonio;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    }

}
