package com.aeribmm.filmcritic.Config.AuthenticationConfig;

import com.aeribmm.filmcritic.DAO.UserRepository;
import com.aeribmm.filmcritic.Exception.userException.UserNotFoundException;
import com.aeribmm.filmcritic.Model.UserModel.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplicationConfigTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationConfiguration authConfig;

    @InjectMocks
    private ApplicationConfig applicationConfig;

    @Test
    void userDetailsService_ShouldReturnUserDetailsService() {
        // Arrange
        User testUser = new User();
        testUser.setEmail("test@example.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));

        // Act
        UserDetailsService userDetailsService = applicationConfig.userDetailsService();

        // Assert
        assertNotNull(userDetailsService);
        assertEquals(testUser, userDetailsService.loadUserByUsername("test@example.com"));
        verify(userRepository).findByEmail("test@example.com");
    }

    @Test
    void userDetailsService_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Act
        UserDetailsService userDetailsService = applicationConfig.userDetailsService();

        // Assert
        assertThrows(UserNotFoundException.class, () -> userDetailsService.loadUserByUsername("nonexistent@example.com"));
        verify(userRepository).findByEmail("nonexistent@example.com");
    }

    @Test
    void authenticationProvider_ShouldReturnDaoAuthenticationProvider() {
        // Act
        AuthenticationProvider authProvider = applicationConfig.authenticationProvider();

        // Assert
        assertNotNull(authProvider);
    }

    @Test
    void passwordEncoder_ShouldReturnBCryptPasswordEncoder() {
        // Act
        BCryptPasswordEncoder passwordEncoder = (BCryptPasswordEncoder) applicationConfig.passwordEncoder();

        // Assert
        assertNotNull(passwordEncoder);
        assertTrue(passwordEncoder instanceof BCryptPasswordEncoder);
    }
}