package com.semillero.asistencias_backend.service.impl;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.semillero.asistencias_backend.models.UserEntity;

//Este es mi adaptador que encapsula al UserEntity
public class UserDetailsImpl implements UserDetails {

    private final UserEntity user;

    public UserDetailsImpl(UserEntity user) {
        this.user = user;
    }

    @Override
    // Convierte el Rol a una colección de autoridades (GrantedAuthority) para Spring Security
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //el usuario tiene UN solo rol directo
        // Spring Security espera una lista, entonces debo devolver una lista de 1 elemento.
        if (user.getRole() == null) {
            return Collections.emptyList();
        }
        return Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().getNombre())
        );
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        //(1=Activo, 0=Inactivo)
        return user.getEstado() != null && user.getEstado() == 1;
    }

}
