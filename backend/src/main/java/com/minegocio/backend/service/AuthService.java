package com.minegocio.backend.service;

import com.minegocio.backend.dto.auth.*;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    RegisterResponse register(RegisterRequest request);

    MeResponse me();

    LogoutResponse logout();

    mecompelto mecompleto();

    mecompelto mecompeltoporconrreo(String correo);

}
