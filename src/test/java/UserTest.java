package com.aeribmm.filmcritic.Model.UserModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;
    private static final Integer ID = 1;
    private static final String USERNAME = "testuser";
    private static final String EMAIL = "test@example.com";
    private static final String PASSWORD = "password";

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void constructorAndGettersSetters_ShouldWorkCorrectly() {
        // Act - using setter methods
        user.setId(ID);
        user.setUsername(USERNAME);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);

        // Assert - using getter methods
        assertEquals(ID, user.getId());
        assertEquals(USERNAME, user.getName());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(PASSWORD, user.getPassword());
        assertEquals(EMAIL, user.getUsername()); // username returns email for Spring Security

        // Act - using parameterized constructor
        User paramUser = new User(ID, USERNAME, EMAIL, PASSWORD);

        // Assert - for constructor initialized object
        assertNotNull(paramUser);
        assertEquals(ID, paramUser.getId());
        assertEquals(USERNAME, paramUser.getName());
        assertEquals(EMAIL, paramUser.getEmail());
        assertEquals(PASSWORD, paramUser.getPassword());
    }

    @Test
    void builder_ShouldCreateUserCorrectly() {
        // Act
        User builtUser = User.builder()
                .id(ID)
                .username(USERNAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        // Assert
        assertNotNull(builtUser);
        assertEquals(ID, builtUser.getId());
        assertEquals(USERNAME, builtUser.getName());
        assertEquals(EMAIL, builtUser.getEmail());
        assertEquals(PASSWORD, builtUser.getPassword());
    }

    @Test
    void userDetails_ShouldReturnCorrectValues() {
        // Arrange
        user.setId(ID);
        user.setUsername(USERNAME);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);

        // Act & Assert
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        assertTrue(authorities.isEmpty());
        assertEquals(EMAIL, user.getUsername()); // getUsername returns email
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isEnabled());
    }
}