package com.aeribmm.filmcritic.Model.Movie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MovieDTOTest {

    private MovieDTO movieDTO;
    private final String TITLE = "Test Movie";
    private final String POSTER_URL = "http://example.com/poster.jpg";
    private final String PLOT = "Test plot description";
    private final String GENRE = "Action, Drama";
    private final Integer YEAR = 2023;

    @BeforeEach
    void setUp() {
        movieDTO = new MovieDTO();
    }

    @Test
    void constructorAndGettersSetters_ShouldWorkCorrectly() {
        // Act - using setter methods
        movieDTO.setTitle(TITLE);
        movieDTO.setPosterURL(POSTER_URL);
        movieDTO.setPlot(PLOT);
        movieDTO.setGenre(GENRE);
        movieDTO.setYear(YEAR);

        // Assert - using getter methods
        assertEquals(TITLE, movieDTO.getTitle());
        assertEquals(POSTER_URL, movieDTO.getPosterURL());
        assertEquals(PLOT, movieDTO.getPlot());
        assertEquals(GENRE, movieDTO.getGenre());
        assertEquals(YEAR, movieDTO.getYear());

        // Act - using parameterized constructor
        MovieDTO paramMovieDTO = new MovieDTO(TITLE, POSTER_URL, PLOT, GENRE, YEAR);

        // Assert - for constructor initialized object
        assertNotNull(paramMovieDTO);
        assertEquals(TITLE, paramMovieDTO.getTitle());
        assertEquals(POSTER_URL, paramMovieDTO.getPosterURL());
        assertEquals(PLOT, paramMovieDTO.getPlot());
        assertEquals(GENRE, paramMovieDTO.getGenre());
        assertEquals(YEAR, paramMovieDTO.getYear());
    }

    @Test
    void toString_ShouldReturnStringRepresentation() {
        // Arrange
        movieDTO.setTitle(TITLE);
        movieDTO.setPosterURL(POSTER_URL);
        movieDTO.setPlot(PLOT);
        movieDTO.setGenre(GENRE);
        movieDTO.setYear(YEAR);

        // Act
        String toString = movieDTO.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains(TITLE));
        assertTrue(toString.contains(POSTER_URL));
        assertTrue(toString.contains(PLOT));
        assertTrue(toString.contains(GENRE));
        assertTrue(toString.contains(YEAR.toString()));
    }
}