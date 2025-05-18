package com.aeribmm.filmcritic.Config.AuthenticationConfig;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.aeribmm.filmcritic.Service.JWTTokens.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
public class JwtAuthentificationFilterTest {

    @Mock
    private JWTService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetails userDetails;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private JwtAuthentificationFilter jwtAuthentificationFilter;

    @Test
    void doFilterInternal_ShouldSkipAuthentication_WhenPathStartsWithAuth() throws ServletException, IOException {
        // Arrange
        when(request.getServletPath()).thenReturn("/auth/login");

        // Act
        jwtAuthentificationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(jwtService);
        verifyNoInteractions(userDetailsService);
    }

    @Test
    void doFilterInternal_ShouldSkipAuthentication_WhenNoAuthHeader() throws ServletException, IOException {
        // Arrange
        when(request.getServletPath()).thenReturn("/api/movies");
        when(request.getHeader("Authorization")).thenReturn(null);

        // Act
        jwtAuthentificationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(jwtService);
        verifyNoInteractions(userDetailsService);
    }

    @Test
    void doFilterInternal_ShouldSkipAuthentication_WhenAuthHeaderDoesNotStartWithBearer() throws ServletException, IOException {
        // Arrange
        when(request.getServletPath()).thenReturn("/api/movies");
        when(request.getHeader("Authorization")).thenReturn("Basic dXNlcjpwYXNzd29yZA==");

        // Act
        jwtAuthentificationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(userDetailsService);
    }

    @Test
    void doFilterInternal_ShouldAuthenticateUser_WhenValidToken() throws ServletException, IOException {
        // Arrange
        when(request.getServletPath()).thenReturn("/api/movies");
        when(request.getHeader("Authorization")).thenReturn("Bearer valid.jwt.token");
        when(jwtService.extractUsername(anyString())).thenReturn("user@example.com");
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);
        when(jwtService.isTokenValid(anyString(), any(UserDetails.class))).thenReturn(true);
        when(securityContext.getAuthentication()).thenReturn(null);

        // Act
        jwtAuthentificationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(jwtService).extractUsername("valid.jwt.token");
        verify(userDetailsService).loadUserByUsername("user@example.com");
        verify(jwtService).isTokenValid("valid.jwt.token", userDetails);
        verify(securityContext).setAuthentication(any());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_ShouldNotAuthenticateUser_WhenInvalidToken() throws ServletException, IOException {
        // Arrange
        when(request.getServletPath()).thenReturn("/api/movies");
        when(request.getHeader("Authorization")).thenReturn("Bearer invalid.jwt.token");
        when(jwtService.extractUsername(anyString())).thenReturn("user@example.com");
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);
        when(jwtService.isTokenValid(anyString(), any(UserDetails.class))).thenReturn(false);
        when(securityContext.getAuthentication()).thenReturn(null);

        // Act
        jwtAuthentificationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(jwtService).extractUsername("invalid.jwt.token");
        verify(userDetailsService).loadUserByUsername("user@example.com");
        verify(jwtService).isTokenValid("invalid.jwt.token", userDetails);
        verify(securityContext, never()).setAuthentication(any());
        verify(filterChain).doFilter(request, response);
    }
}