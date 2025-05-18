package com.aeribmm.filmcritic.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeepSeekServiceTest {

    @Mock
    private CloseableHttpClient httpClient;

    @Mock
    private HttpPost httpPost;

    @Mock
    private CloseableHttpResponse httpResponse;

    @Mock
    private HttpEntity httpEntity;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private JsonNode rootNode;

    @Mock
    private JsonNode choicesNode;

    @Mock
    private JsonNode firstChoiceNode;

    @Mock
    private JsonNode messageNode;

    @Mock
    private JsonNode contentNode;

    @InjectMocks
    private DeepSeekService deepSeekService;

    @BeforeEach
    void setUp() throws IOException {
        ReflectionTestUtils.setField(deepSeekService, "apiKey", "test-api-key");
        
        when(httpClient.execute(any(HttpPost.class))).thenReturn(httpResponse);
        when(httpResponse.getEntity()).thenReturn(httpEntity);
        when(EntityUtils.toString(httpEntity)).thenReturn("{\"json\":\"response\"}");
        
        when(objectMapper.readTree(anyString())).thenReturn(rootNode);
        when(rootNode.path("choices")).thenReturn(choicesNode);
        when(choicesNode.isArray()).thenReturn(true);
        when(choicesNode.size()).thenReturn(1);
        when(choicesNode.get(0)).thenReturn(firstChoiceNode);
        when(firstChoiceNode.path("message")).thenReturn(messageNode);
        when(messageNode.isMissingNode()).thenReturn(false);
        when(messageNode.path("content")).thenReturn(contentNode);
        when(contentNode.isMissingNode()).thenReturn(false);
        when(contentNode.asText()).thenReturn("AI generated response");
    }

    @Test
    void generateText_ShouldReturnResponse() throws IOException {
        // Arrange
        String prompt = "Tell me about a movie";

        // Act
        String result = deepSeekService.generateText(prompt);

        // Assert
        assertEquals("AI generated response", result);
    }
}