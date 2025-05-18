package com.aeribmm.filmcritic.Exception;

import com.aeribmm.filmcritic.Exception.MovieException.MovieNotFoundException;
import com.aeribmm.filmcritic.Exception.userException.UserAlreadyExistsException;
import com.aeribmm.filmcritic.Exception.userException.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler exceptionHandler;

    @Test
    void handleUserAlreadyExistsException_ShouldReturnConflictStatus() {
        // Arrange
        UserAlreadyExistsException exception = new UserAlreadyExistsException("User already exists");

        // Act
        ResponseEntity<String> response = exceptionHandler.handleUserAlreadyExistsException(exception);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("User already exists", response.getBody());
    }

    @Test
    void handleUserNotFoundException_ShouldReturnNotFoundStatus() {
        // Arrange
        UserNotFoundException exception = new UserNotFoundException("User not found");

        // Act
        ResponseEntity<String> response = exceptionHandler.handleUserNotFoundException(exception);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", response.getBody());
    }

    @Test
    void handleMovieNotFoundException_ShouldReturnNotFoundStatus() {
        // Arrange
        MovieNotFoundException exception = new MovieNotFoundException();

        // Act
        ResponseEntity<String> response = exceptionHandler.handleMovieNotFoundException(exception);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }
}