package com.aeribmm.filmcritic.Service.JWTTokens;

import com.aeribmm.filmcritic.Aunth.AuthenticationRequest;
import com.aeribmm.filmcritic.Aunth.AuthenticationResponse;
import com.aeribmm.filmcritic.Aunth.RegisterRequest;
import com.aeribmm.filmcritic.DAO.UserRepository;
import com.aeribmm.filmcritic.Exception.userException.UserAlreadyExistsException;
import com.aeribmm.filmcritic.Model.UserModel.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTService jwtService;

    @InjectMocks
    private AuthenticationService authenticationService;

    private RegisterRequest registerRequest;
    private AuthenticationRequest authRequest;
    private User testUser;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("John");
        registerRequest.setLastName("Doe");
        registerRequest.setEmail("john@example.com");
        registerRequest.setPassword("password");

        authRequest = new AuthenticationRequest();
        authRequest.setEmail("john@example.com");
        authRequest.setPassword("password");

        testUser = new User();
        testUser.setId(1);
        testUser.setUsername("John Doe");
        testUser.setEmail("john@example.com");
        testUser.setPassword("encodedPassword");
    }

    @Test
    void register_ShouldCreateNewUser_WhenEmailDoesNotExist() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");

        // Act
        AuthenticationResponse response = authenticationService.register(registerRequest);

        // Assert
        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        verify(userRepository).findByEmail("john@example.com");
        verify(passwordEncoder).encode("password");
        verify(userRepository).createUser(eq("John Doe"), eq("john@example.com"), eq("encodedPassword"));
        verify(jwtService).generateToken(any(User.class));
    }

    @Test
    void register_ShouldThrowException_WhenEmailAlreadyExists() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));

        // Act & Assert
        assertThrows(UserAlreadyExistsException.class, () -> authenticationService.register(registerRequest));
        verify(userRepository).findByEmail("john@example.com");
    }

    @Test
    void authenticate_ShouldAuthenticateUser() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");

        // Act
        AuthenticationResponse response = authenticationService.authenticate(authRequest);

        // Assert
        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByEmail("john@example.com");
        verify(jwtService).generateToken(testUser);
    }
}