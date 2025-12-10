package com.minegocio.backend.service.impl;

import com.minegocio.backend.dto.auth.*;
import com.minegocio.backend.model.Usuario;
import com.minegocio.backend.model.enums.RolUsuario;
import com.minegocio.backend.model.enums.UsuarioEstado;
import com.minegocio.backend.repository.UsuarioRepository;
import com.minegocio.backend.security.jwt.JwtUtil;
import com.minegocio.backend.security.model.CustomUserDetails;
import com.minegocio.backend.service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public LoginResponse login(LoginRequest request) {

        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        String token = jwtUtil.generarToken(usuario.getCorreo(), usuario.getRol().toString());

        return LoginResponse.builder()
                .token(token)
                .correo(usuario.getCorreo())
                .nombre(usuario.getNombre())
                .build();
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {

        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        Usuario nuevo = Usuario.builder()
                .nombre(request.getNombre())
                .correo(request.getCorreo())
                .password(passwordEncoder.encode(request.getPassword()))
                .estado(UsuarioEstado.ACTIVO)
                .emailVerificado(false)
                .Rol(RolUsuario.USUARIO)
                .fechaRegistro(new Timestamp(System.currentTimeMillis()))
                .build();

        usuarioRepository.save(nuevo);

        return RegisterResponse.builder()
                .correo(nuevo.getCorreo())
                .mensaje("Usuario registrado correctamente")
                .build();
    }

    @Override
    public MeResponse me() {

        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails userDetails)) {
            throw new RuntimeException("No autenticado");
        }

        Usuario usuario = userDetails.getUsuario();

        return MeResponse.builder()
                .correo(usuario.getCorreo())
                .nombre(usuario.getNombre())
                .emailVerificado(usuario.getEmailVerificado())
                .build();
    }

    @Override
    public LogoutResponse logout() {

        SecurityContextHolder.clearContext();

        return LogoutResponse.builder()
                .mensaje("Sesión cerrada correctamente")
                .build();
    }

    @Override
    public mecompelto mecompleto() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails userDetails)) {
            throw new RuntimeException("No autenticado");
        }

        Usuario usuario = userDetails.getUsuario();

        return mecompleto().builder().avatarUrl(usuario.getAvatarUrl()).correo(usuario.getCorreo())
                .emailVerificado(usuario.getEmailVerificado())
                .estado(usuario.getEstado())
                .fechaRegistro(usuario.getFechaRegistro())
                .lastLogin(usuario.getLastLogin())

                .build();
    }

    @Override
    public mecompelto mecompeltoporconrreo(String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        return mecompleto().builder().avatarUrl(usuario.getAvatarUrl()).correo(usuario.getCorreo())
                .emailVerificado(usuario.getEmailVerificado())
                .estado(usuario.getEstado())
                .fechaRegistro(usuario.getFechaRegistro())
                .lastLogin(usuario.getLastLogin())

                .build();

    }

}
