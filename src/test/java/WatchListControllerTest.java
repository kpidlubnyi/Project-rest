package com.aeribmm.filmcritic.Controller;

import com.aeribmm.filmcritic.Model.WatchListModel.WatchList;
import com.aeribmm.filmcritic.Service.WatchListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WatchListControllerTest {

    @Mock
    private WatchListService watchListService;

    @InjectMocks
    private WatchListController watchListController;

    private WatchList testWatchList;

    @BeforeEach
    void setUp() {
        testWatchList = new WatchList();
        testWatchList.setId(1);
        testWatchList.setUserId(1);
        testWatchList.setMovieId("tt1234567");
        testWatchList.setCreateAt(LocalDate.now());
    }

    @Test
    void getWatchListByUser_ShouldReturnWatchList() {
        // Arrange
        List<WatchList> watchLists = Arrays.asList(testWatchList);
        when(watchListService.getWatchlistByUserId(anyInt())).thenReturn(watchLists);

        // Act
        ResponseEntity<List<WatchList>> response = watchListController.getWatchListByUser(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(testWatchList, response.getBody().get(0));
        verify(watchListService).getWatchlistByUserId(1);
    }

    @Test
    void getAll_ShouldReturnAllWatchLists() {
        // Arrange
        List<WatchList> watchLists = Arrays.asList(testWatchList);
        when(watchListService.getAll()).thenReturn(watchLists);

        // Act
        ResponseEntity<List<WatchList>> response = watchListController.getAll();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(testWatchList, response.getBody().get(0));
        verify(watchListService).getAll();
    }
}