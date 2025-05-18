package com.aeribmm.filmcritic.Service;

import com.aeribmm.filmcritic.DAO.FilmRepository;
import com.aeribmm.filmcritic.Model.Movie.Movie;
import com.aeribmm.filmcritic.Model.Movie.MovieDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @Mock
    private FilmRepository filmRepository;

    @InjectMocks
    private MovieService movieService;

    private Movie testMovie;

    @BeforeEach
    void setUp() {
        testMovie = new Movie();
        testMovie.setImdbId("tt1234567");
        testMovie.setTitle("Test Movie");
        testMovie.setYear(2023);
        testMovie.setGenre("Action");
        testMovie.setPlot("Test plot");
        testMovie.setPoster("http://example.com/poster.jpg");
    }

    @Test
    void getMovie_ShouldReturnMovie() {
        // Arrange
        when(filmRepository.getMovieByImdbId(anyString())).thenReturn(testMovie);

        // Act
        Movie result = movieService.getMovie("tt1234567");

        // Assert
        assertNotNull(result);
        assertEquals(testMovie.getImdbId(), result.getImdbId());
        assertEquals(testMovie.getTitle(), result.getTitle());
        verify(filmRepository).getMovieByImdbId("tt1234567");
    }

    @Test
    void random_ShouldReturnRandomMovie() {
        // Arrange
        when(filmRepository.getRandomMovie()).thenReturn(testMovie);

        // Act
        Movie result = movieService.random();

        // Assert
        assertNotNull(result);
        assertEquals(testMovie, result);
        verify(filmRepository).getRandomMovie();
    }

    @Test
    void getAll_ShouldReturnMovieDTOs() {
        // Arrange
        List<Movie> movies = Arrays.asList(testMovie, testMovie);
        Page<Movie> moviesPage = new PageImpl<>(movies);
        when(filmRepository.findAll(any(Pageable.class))).thenReturn(moviesPage);

        // Act
        List<MovieDTO> result = movieService.getAll(10);

        // Assert
        assertEquals(2, result.size());
        assertEquals(testMovie.getTitle(), result.get(0).getTitle());
        assertEquals(testMovie.getPoster(), result.get(0).getPosterURL());
        verify(filmRepository).findAll(any(Pageable.class));
    }

    @Test
    void search_ShouldReturnMatchingMovies() {
        // Arrange
        List<Movie> movies = Arrays.asList(testMovie);
        when(filmRepository.findByTitleContainingIgnoreCase(anyString())).thenReturn(movies);

        // Act
        List<Movie> result = movieService.search("Test");

        // Assert
        assertEquals(1, result.size());
        assertEquals(testMovie, result.get(0));
        verify(filmRepository).findByTitleContainingIgnoreCase("Test");
    }

    @Test
    void get5_ShouldReturnFiveMovieDTOs() {
        // Arrange
        List<Movie> movies = Arrays.asList(testMovie, testMovie, testMovie, testMovie, testMovie);
        when(filmRepository.getFiveLastMovies()).thenReturn(movies);

        // Act
        List<MovieDTO> result = movieService.get5();

        // Assert
        assertEquals(5, result.size());
        for (MovieDTO dto : result) {
            assertEquals(testMovie.getTitle(), dto.getTitle());
            assertEquals(testMovie.getPoster(), dto.getPosterURL());
        }
        verify(filmRepository).getFiveLastMovies();
    }
}