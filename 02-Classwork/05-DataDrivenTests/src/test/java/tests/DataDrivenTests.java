package tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.Priority;
import data.RequestRow;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class DataDrivenTests {

    @Test
    void sampleTest() {
        List<RequestRow> rows = List.of(
                new RequestRow("Laptop not booting", "High", "Open"),
                new RequestRow("Need new mouse", "Low", "Open"),
                new RequestRow("Server is down", "Critical", "In Progress"),
                new RequestRow("VPN access", "Medium", "Resolved"),
                new RequestRow("Printer jam", "High", "Closed"));

    List<RequestRow> result = rows.stream()
            .filter(x -> Objects.equals(x.priority(), "High"))
            .sorted(Comparator.comparing(RequestRow::title))
            .toList();
        System.out.println(result);

    Boolean result2 = rows.stream()
            .filter(x -> Objects.equals(x.priority(), "High"))
            .findFirst()
            .isPresent();

    RequestRow firstRow = rows.stream()
            .filter(x -> Objects.equals(x.priority(), "High"))
            .findFirst()
            .get();

        System.out.println(firstRow);
    List<String> titles = rows.stream()
            .map(RequestRow::title)
            .toList();

        System.out.println(titles);
    }

    public record NewRequestData(String title, String category, Priority priority) { }

    @ParameterizedTest(name = "{index} Request: {0}")
    @MethodSource("requests")
    void request_isSubmitted(NewRequestData data) {
        Assertions.assertNotNull(data);
    }

    static Stream<NewRequestData> requests() {
        return Stream.of(
                new NewRequestData("Pothole", "Roads & Infrastructure", Priority.HIGH),
                new NewRequestData("Broken bench", "Parks & Recreation", Priority.LOW));
    }

    // src/test/resources/requests.json: [ { "title": "...", "priority": "HIGH", ... } ]
    static Stream<NewRequestData> requestsFromJson() throws IOException {
        try (InputStream json = DataDrivenTests.class.getResourceAsStream("/requests.json")) {
            List<NewRequestData> data = new ObjectMapper()
                    .readValue(json, new TypeReference<List<NewRequestData>>() { });
            return data.stream();
        }
    }
}
