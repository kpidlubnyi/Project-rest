package com.aeribmm.filmcritic.Service;

import com.aeribmm.filmcritic.Model.omdbApi.MovieResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OmdbServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private OmdbService omdbService;

    private String apiKey = "testApiKey";
    private MovieResponse testMovieResponse;
    private String jsonResponse;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(omdbService, "apiKey", apiKey);

        testMovieResponse = new MovieResponse();
        testMovieResponse.setImdbId("tt1234567");
        testMovieResponse.setTitle("Test Movie");
        testMovieResponse.setYear(2023);

        jsonResponse = "{\"imdbID\":\"tt1234567\",\"Title\":\"Test Movie\",\"Year\":2023,\"Response\":\"True\"}";
    }

    @Test
    void getMovieByTitle_ShouldReturnMovieResponse() {
        // Arrange
        String title = "Test Movie";
        String url = String.format("https://www.omdbapi.com/?apikey=%s&t=%s", apiKey, title.replace(" ", "+"));
        
        when(restTemplate.getForObject(url, MovieResponse.class)).thenReturn(testMovieResponse);

        // Act
        MovieResponse result = omdbService.getMovieByTitle(title);

        // Assert
        assertNotNull(result);
        assertEquals(testMovieResponse, result);
    }
}