package com.aeribmm.filmcritic.Model.WatchListModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WatchListRequestTest {

    private WatchListRequest request;
    private final Integer USER_ID = 1;
    private final String MOVIE_ID = "tt1234567";
    private final WatchListStatus STATUS = WatchListStatus.watching;

    @BeforeEach
    void setUp() {
        request = new WatchListRequest();
    }

    @Test
    void constructorAndGettersSetters_ShouldWorkCorrectly() {
        // Act - using setter methods
        request.setUserId(USER_ID);
        request.setMovieId(MOVIE_ID);
        request.setStatus(STATUS);

        // Assert - using getter methods
        assertEquals(USER_ID, request.getUserId());
        assertEquals(MOVIE_ID, request.getMovieId());
        assertEquals(STATUS, request.getStatus());

        // Act - using parameterized constructor
        WatchListRequest paramRequest = new WatchListRequest(USER_ID, MOVIE_ID, STATUS);

        // Assert - for constructor initialized object
        assertNotNull(paramRequest);
        assertEquals(USER_ID, paramRequest.getUserId());
        assertEquals(MOVIE_ID, paramRequest.getMovieId());
        assertEquals(STATUS, paramRequest.getStatus());
    }
}