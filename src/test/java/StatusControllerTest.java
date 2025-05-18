package com.aeribmm.filmcritic.Controller;

import com.aeribmm.filmcritic.Model.WatchListModel.WatchListRequest;
import com.aeribmm.filmcritic.Model.WatchListModel.WatchListStatus;
import com.aeribmm.filmcritic.Service.WatchListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class StatusControllerTest {

    @Mock
    private WatchListService watchListService;

    @InjectMocks
    private StatusController statusController;

    private WatchListRequest testRequest;

    @BeforeEach
    void setUp() {
        testRequest = new WatchListRequest();
        testRequest.setUserId(1);
        testRequest.setMovieId("tt1234567");
        testRequest.setStatus(WatchListStatus.watching);
    }

    @Test
    void addToWatchList_ShouldAddMovieToWatchList() {
        // Act
        ResponseEntity<String> response = statusController.addToWatchList(testRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Success.", response.getBody());
        verify(watchListService).addMovieToWatchList(testRequest);
    }
}