package com.minegocio.backend.controller;

import com.minegocio.backend.dto.auth.*;
import com.minegocio.backend.service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/publicos/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);

    }

    @PostMapping("/publicos/register")
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @GetMapping("/usuario/me")
    public MeResponse me() {
        return authService.me();
    }

    @PostMapping("/usuario/logout")
    public LogoutResponse logout() {
        return authService.logout();
    }

}
