package com.aeribmm.filmcritic.Config.AuthenticationConfig;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.aeribmm.filmcritic.DAO.UserRepository;
import com.aeribmm.filmcritic.Model.UserModel.User;

@ExtendWith(MockitoExtension.class)
public class ApplicationConfigTest {

    @Mock
    private UserRepository userRepository;

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