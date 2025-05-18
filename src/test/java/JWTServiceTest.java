package com.aeribmm.filmcritic.Service.JWTTokens;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JWTServiceTest {

    @InjectMocks
    private JWTService jwtService;

    private UserDetails userDetails;
    private String secretKey;

    @BeforeEach
    void setUp() {
        secretKey = "testSecretKeyWithLength512BitsTestSecretKeyWithLength512BitsTestSecretKeyWithLength512Bits";
        ReflectionTestUtils.setField(jwtService, "SECRET_KEY", secretKey);
        
        userDetails = User.builder()
                .username("john@example.com")
                .password("password")
                .authorities(new ArrayList<>())
                .build();
    }

    @Test
    void extractUsername_ShouldReturnUsername() {
        // Arrange
        String token = createToken(userDetails.getUsername());

        // Act
        String username = jwtService.extractUsername(token);

        // Assert
        assertEquals(userDetails.getUsername(), username);
    }

    @Test
    void generateToken_ShouldGenerateValidToken() {
        // Act
        String token = jwtService.generateToken(userDetails);

        // Assert
        assertNotNull(token);
        assertEquals(userDetails.getUsername(), jwtService.extractUsername(token));
    }

    @Test
    void isTokenValid_ShouldReturnTrue_ForValidToken() {
        // Arrange
        String token = jwtService.generateToken(userDetails);

        // Act
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void isTokenValid_ShouldReturnFalse_ForExpiredToken() {
        // Arrange
        String expiredToken = createExpiredToken(userDetails.getUsername());

        // Act
        boolean isValid = jwtService.isTokenValid(expiredToken, userDetails);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void isTokenValid_ShouldReturnFalse_ForTokenWithDifferentUsername() {
        // Arrange
        String token = createToken("differentemail@example.com");

        // Act
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        // Assert
        assertFalse(isValid);
    }

    private String createToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    private String createExpiredToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis() - 1000 * 60 * 60))
                .setExpiration(new Date(System.currentTimeMillis() - 1000 * 60 * 30))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }
}