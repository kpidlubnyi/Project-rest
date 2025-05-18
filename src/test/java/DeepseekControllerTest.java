package com.aeribmm.filmcritic.Controller;

import com.aeribmm.filmcritic.Service.DeepSeekService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeepseekControllerTest {

    @Mock
    private DeepSeekService deepSeekService;

    @InjectMocks
    private DeepseekController deepseekController;

    @Test
    void createAiMessage_ShouldReturnAiResponse() throws IOException {
        // Arrange
        String prompt = "Tell me about The Godfather movie";
        String expectedResponse = "The Godfather is a 1972 American crime film...";
        when(deepSeekService.generateText(anyString())).thenReturn(expectedResponse);

        // Act
        ResponseEntity<String> response = deepseekController.createAiMessage(prompt);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(deepSeekService).generateText(prompt);
    }

    @Test
    void createAiMessage_ShouldReturnErrorResponse_WhenServiceThrowsException() throws IOException {
        // Arrange
        String prompt = "Tell me about The Godfather movie";
        IOException exception = new IOException("API Error");
        when(deepSeekService.generateText(anyString())).thenThrow(exception);

        // Act
        ResponseEntity<String> response = deepseekController.createAiMessage(prompt);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error processing request: API Error", response.getBody());
        verify(deepSeekService).generateText(prompt);
    }
}