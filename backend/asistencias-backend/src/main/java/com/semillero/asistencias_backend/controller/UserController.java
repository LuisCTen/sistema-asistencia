package com.semillero.asistencias_backend.controller;
import com.semillero.asistencias_backend.dto.UserRequestDto;
import com.semillero.asistencias_backend.dto.UserResponseDto;
import com.semillero.asistencias_backend.service.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UserController {

    private final IUsuarioService usuarioService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody UserRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id, @RequestBody UserRequestDto dto) {
        return ResponseEntity.ok(usuarioService.update(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponseDto> delete(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.delete(id));
    }
}
