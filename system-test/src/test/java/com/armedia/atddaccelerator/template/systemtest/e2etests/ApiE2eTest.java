package com.armedia.atddaccelerator.template.systemtest.e2etests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class ApiE2eTest {

    static WireMockServer wireMockServer;

    @BeforeAll
    static void setup() {
        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();

        wireMockServer.stubFor(
                get(urlEqualTo("/api/taxes/info"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody("""
                                    {
                                      "data": {
                                        "taxAmount": 1500,
                                        "year": 2024,
                                        "status": "OK"
                                      }
                                    }
                                    """))
        );
    }

    @AfterAll
    static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void getTaxesFromExternalAPI_shouldReturnOK() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/api/taxes/info"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("data"));
        assertTrue(response.body().contains("taxAmount"));
    }

    @Test
    void getCitiesByName_shouldReturnWithExpectedFormat() throws Exception {
        // DISCLAIMER: This is an example of a badly written test
        // which unfortunately simulates real-life software test projects.
        // This is the starting point for our ATDD Accelerator exercises.

        // Arrange
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(new URI("http://localhost:8080/api/cities/by-name/Skopje")).GET().build();
        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Assert
        assertEquals(200, response.statusCode());

        String responseBody = response.body();

        // Verify JSON structure contains expected fields
        assertTrue(responseBody.contains("Skopje"));
        assertTrue(responseBody.contains("\"name\""), "Response should contain name of city field");
        assertTrue(responseBody.contains("\"airports\""), "Response should contain airports field");
        assertTrue(responseBody.contains("\"country\""), "Response should contain country field");

        assertTrue(responseBody.contains("\"name\":\"Skopje\"") || responseBody.contains("\"name\": \"Skopje\""), "Response should contain name field with value Skopje");
    }

    @Test
    void saveTestCity_shouldReturnOK() throws Exception {
        //Arrange
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/api/cities"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("""
                        {
                            "name": "Test",
                            "country": {"name":"Test"},
                            "airports": ["Test"]
                        }
                        """)).build();
        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Assert
        assertEquals(200, response.statusCode());

        String responseBody = response.body();

        // Verify JSON structure contains expected fields
        assertTrue(responseBody.contains("\"name\""), "Response should contain name of city field");
        assertTrue(responseBody.contains("\"airports\""), "Response should contain airports field");
        assertTrue(responseBody.contains("\"country\""), "Response should contain country field");

        assertTrue(responseBody.contains("\"name\":\"Test\"") || responseBody.contains("\"name\": \"Test\""),
                "Response should contain name field with value Test");
    }

    @Test
    void getFlightFromTo_shouldReturnWithExpectedPrice() throws Exception {
        // DISCLAIMER: This is an example of a badly written test
        // which unfortunately simulates real-life software test projects.
        // This is the starting point for our ATDD Accelerator exercises.

        // Arrange
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(new URI("http://localhost:8080/api/flights/1609/325")).GET().build();
        // Act
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Assert
        assertEquals(200, response.statusCode());

        String responseBody = response.body();

        // Verify JSON structure contains expected fields
        assertTrue(responseBody.contains("Skopje"));
        assertTrue(responseBody.contains("Berlin"));

        assertTrue(responseBody.contains("\"price\":209.71") || responseBody.contains("\"price\": 209.71"), "Response should contain price field");
    }
}
