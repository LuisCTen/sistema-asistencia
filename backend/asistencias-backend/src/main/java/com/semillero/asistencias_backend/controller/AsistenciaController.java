package com.semillero.asistencias_backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.semillero.asistencias_backend.service.IAsistenciaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/asistencias")
@RequiredArgsConstructor
public class AsistenciaController {

    private final IAsistenciaService iAsistenciaService;

    

}
