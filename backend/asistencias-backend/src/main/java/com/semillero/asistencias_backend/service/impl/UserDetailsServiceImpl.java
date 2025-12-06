package com.semillero.asistencias_backend.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.semillero.asistencias_backend.models.UserEntity;
import com.semillero.asistencias_backend.repository.IUserRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
//Este servicio es el que llama el Login para ver si el usuario existe

public class UserDetailsServiceImpl implements UserDetailsService {
    private final IUserRepository iUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity= iUserRepository.findByUsername(username)
                                              .orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado: "+username));
        return new UserDetailsImpl(userEntity) ;
    }
}
