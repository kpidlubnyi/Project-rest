package com.aeribmm.filmcritic.Service;

import com.aeribmm.filmcritic.DAO.UserRepository;
import com.aeribmm.filmcritic.DAO.WatchListRepository;
import com.aeribmm.filmcritic.Exception.userException.UserNotFoundException;
import com.aeribmm.filmcritic.Model.UserModel.User;
import com.aeribmm.filmcritic.Model.WatchListModel.WatchList;
import com.aeribmm.filmcritic.Model.WatchListModel.WatchListRequest;
import com.aeribmm.filmcritic.Model.WatchListModel.WatchListStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WatchListServiceTest {

    @Mock
    private WatchListRepository watchListRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private WatchListService watchListService;

    private User testUser;
    private WatchList testWatchList;
    private WatchListRequest testRequest;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1);
        testUser.setUsername("testUser");

        testWatchList = new WatchList();
        testWatchList.setId(1);
        testWatchList.setUserId(1);
        testWatchList.setMovieId("tt1234567");
        testWatchList.setStatus(WatchListStatus.planned);
        testWatchList.setCreateAt(LocalDate.now());

        testRequest = new WatchListRequest();
        testRequest.setUserId(1);
        testRequest.setMovieId("tt1234567");
        testRequest.setStatus(WatchListStatus.watching);
    }

    @Test
    void getWatchlistByUserId_ShouldReturnWatchlist() {
        // Arrange
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(testUser));
        when(watchListRepository.getWatchlistByUserId(anyInt())).thenReturn(Arrays.asList(testWatchList));

        // Act
        List<WatchList> result = watchListService.getWatchlistByUserId(1);

        // Assert
        assertEquals(1, result.size());
        assertEquals(testWatchList, result.get(0));
        verify(userRepository).findById(1);
        verify(watchListRepository).getWatchlistByUserId(1);
    }

    @Test
    void getWatchlistByUserId_ShouldThrowException_WhenUserDoesNotExist() {
        // Arrange
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> watchListService.getWatchlistByUserId(1));
        verify(userRepository).findById(1);
    }

    @Test
    void getAll_ShouldReturnAllWatchlists() {
        // Arrange
        when(watchListRepository.findAll()).thenReturn(Arrays.asList(testWatchList));

        // Act
        List<WatchList> result = watchListService.getAll();

        // Assert
        assertEquals(1, result.size());
        assertEquals(testWatchList, result.get(0));
        verify(watchListRepository).findAll();
    }

    @Test
    void addMovieToWatchList_ShouldUpdateExistingEntry_WhenAlreadyExists() {
        // Arrange
        when(watchListRepository.findByUserIdAndMovieId(anyInt(), any())).thenReturn(testWatchList);

        // Act
        watchListService.addMovieToWatchList(testRequest);

        // Assert
        assertEquals(WatchListStatus.watching, testWatchList.getStatus());
        verify(watchListRepository).findByUserIdAndMovieId(1, "tt1234567");
        verify(watchListRepository).save(testWatchList);
    }

    @Test
    void addMovieToWatchList_ShouldCreateNewEntry_WhenDoesNotExist() {
        // Arrange
        when(watchListRepository.findByUserIdAndMovieId(anyInt(), any())).thenReturn(null);

        // Act
        watchListService.addMovieToWatchList(testRequest);

        // Assert
        verify(watchListRepository).findByUserIdAndMovieId(1, "tt1234567");
        verify(watchListRepository).save(any(WatchList.class));
    }
}