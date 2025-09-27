package com.example.auth_service;


import com.example.auth_service.dto.request.LoginRequest;
import com.example.auth_service.dto.request.RegisterRequest;
import com.example.auth_service.dto.response.LoginResponse;
import com.example.auth_service.entity.User;
import com.example.auth_service.enums.Roles;
import com.example.auth_service.security.Jwt.JwtService;
import com.example.auth_service.service.AuthService;
import com.example.auth_service.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import com.example.auth_service.enums.Roles.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_shouldReturnLoginResponse() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("aziz");
        request.setPassword("123456");
        request.setEmail("aziz@email.com");
        request.setPhoneNumber("0555123456");
        request.setFirstName("Aziz");
        request.setLastName("Jaser");
        request.setAge(25);

        when(userService.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(jwtService.generateToken(any(User.class))).thenReturn("mocked.jwt.token");
        when(jwtService.extractExpiration(anyString())).thenReturn(LocalDateTime.now().plusHours(1));

        LoginResponse response = authService.register(request);

        verify(userService).createUser(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals("aziz", savedUser.getUsername());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertTrue(savedUser.getRoles().contains(Roles.ROLE_USER));

        assertEquals("mocked.jwt.token", response.getToken());
        assertNotNull(response.getExpireAt());
        assertNotNull(response.getIssueAt());
    }

    @Test
    void login_shouldReturnLoginResponse() {
        LoginRequest request = new LoginRequest();
        request.setEmail("aziz@email.com");
        request.setPassword("123456");

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword("encodedPassword");
        user.setRoles(Set.of(Roles.ROLE_USER));

        when(userService.getUserByEmail(request.getEmail())).thenReturn(user);
        when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("mocked.jwt.token");
        when(jwtService.extractExpiration("mocked.jwt.token")).thenReturn(LocalDateTime.now().plusHours(1));

        LoginResponse response = authService.login(request);

        assertEquals("mocked.jwt.token", response.getToken());
        assertNotNull(response.getExpireAt());
        assertNotNull(response.getIssueAt());
    }

    @Test
    void login_shouldThrowException_ifUserNotFound() {
        LoginRequest request = new LoginRequest();
        request.setEmail("notfound@email.com");

        when(userService.getUserByEmail(request.getEmail()))
                .thenThrow(new IllegalArgumentException("User not found"));

        assertThrows(IllegalArgumentException.class, () -> authService.login(request));
    }

    @Test
    void login_shouldThrowException_ifPasswordMismatch() {
        LoginRequest request = new LoginRequest();
        request.setEmail("aziz@email.com");
        request.setPassword("wrongpass");

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword("encodedPassword");

        when(userService.getUserByEmail(request.getEmail())).thenReturn(user);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> authService.login(request));
    }

    @Test
    void login_shouldThrowException_ifPasswordDoesNotMatch() {
        LoginRequest request = new LoginRequest();
        request.setEmail("aziz@email.com");
        request.setPassword("wrongPassword");

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword("encodedCorrectPassword");
        user.setRoles(Set.of(Roles.ROLE_USER));

        when(userService.getUserByEmail(request.getEmail())).thenReturn(user);
        when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> authService.login(request));
    }

    @Test
    void register_shouldThrowException_ifEmailExists() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("existing@email.com");

        when(userService.existsByEmail(request.getEmail())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> authService.register(request));
    }

    @Test
    void register_shouldReturnLoginResponse_whenSuccess() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("new@email.com");
        request.setPassword("123456");
        request.setUsername("aziz");
        request.setFirstName("Aziz");
        request.setLastName("Jaser");
        request.setPhoneNumber("+966501234567");
        request.setAge(25);

        String encodedPassword = "encodedPassword";

        when(userService.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn(encodedPassword);
        when(jwtService.generateToken(any(User.class))).thenReturn("mocked.jwt.token");
        when(jwtService.extractExpiration(anyString())).thenReturn(LocalDateTime.now().plusHours(1));

        LoginResponse response = authService.register(request);

        assertEquals("mocked.jwt.token", response.getToken());
        assertNotNull(response.getExpireAt());
        assertNotNull(response.getIssueAt());
    }
}