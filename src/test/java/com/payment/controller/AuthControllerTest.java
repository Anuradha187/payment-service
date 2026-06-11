package com.payment.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.payment.dto.request.LoginRequest;
import com.payment.dto.request.RegisterRequest;
import com.payment.dto.response.LoginResponse;
import com.payment.service.AuthService;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Test
    void shouldRegisterUserSuccessfully() {

        RegisterRequest request = new RegisterRequest();
        request.setUsername("testuser");
        request.setPassword("password123");

        doNothing().when(authService).register(request);

        ResponseEntity<String> response =
                authController.register(request);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully",
                response.getBody());

        verify(authService, times(1))
                .register(request);
    }

    @Test
    void shouldLoginSuccessfully() {

        LoginRequest request = new LoginRequest();
        request.setUsername("testuser");
        request.setPassword("password123");

        String jwtToken = "dummy.jwt.token";

        when(authService.login(
                request.getUsername(),
                request.getPassword()))
                .thenReturn(jwtToken);

        ResponseEntity<LoginResponse> response =
                authController.login(request);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response.getBody());
        assertEquals(jwtToken,
                response.getBody().getToken());

        verify(authService, times(1))
                .login(
                        request.getUsername(),
                        request.getPassword());
    }
}