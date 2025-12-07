package com.semillero.asistencias_backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.semillero.asistencias_backend.dto.LoginRequestDto;
import com.semillero.asistencias_backend.dto.LoginResponseDto;
import com.semillero.asistencias_backend.jwt.JwtUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        //Debemos validar user/password, para ello spring security
        
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername() , 
                                                    loginRequestDto.getPassword())
        );
        //Si ya se valido credenciales entonces extraemos el username y el role para pasarlo al entity
        String username= authentication.getName();
        //Debemos obtener el rol, recuerda relacion 1:1
        List<String> roles=authentication.getAuthorities().stream()
                                       .map(GrantedAuthority::getAuthority)
                                       .toList();
        // JwtUtil solo necesita el primer rol, debemos extraerlo (1:1):
       String principalRole = roles.stream().findFirst().orElse("ROLE_USER");
        //No olvidar generar el token
        //El jwt espera una lista, asi que pasamos los roles como una lista(asi sea solo uno)
        String token = jwtUtil.generateToken(username,roles);
        //Devolvemos el entity
        return ResponseEntity.ok(LoginResponseDto.builder()
                    .token(token)
                    .username(username)
                    .role(principalRole)
                    .build());
    }
    
}
