package com.tutorial.crud.security.infraestructure.controller;


import com.tutorial.crud.content.actividad.application.ActividadUseCase;
import com.tutorial.crud.content.actividad.domain.entity.Actividad;
import com.tutorial.crud.content.actividad.infraestructure.controller.dto.input.ActividadDtoInput;
import com.tutorial.crud.content.actividad.infraestructure.controller.dto.output.ActividadDtoOutput;
import com.tutorial.crud.content.actividad_usuario.domain.entity.ActividadUsuario;
import com.tutorial.crud.security.application.UsuarioUseCase;
import com.tutorial.crud.security.application.mapper.UsuarioToNoAdmitidoOutputMapper;
import com.tutorial.crud.security.domain.entity.Usuario;
import com.tutorial.crud.security.infraestructure.controller.dto.input.UsuarioNoAdmitidoDtoInput;
import com.tutorial.crud.security.infraestructure.controller.dto.output.UsuarioNoAdmitidoDtoOutput;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioToNoAdmitidoOutputMapper usuarioToNoAdmitidoOutputMapper;

    private final UsuarioUseCase usuarioUseCase;


    @GetMapping(value = "/listarUsuariosNoAdmitidos")
    public List<UsuarioNoAdmitidoDtoOutput> listarUsuariosNoAdmitidos() {
        List<Usuario> usuariosNoAdmitidosList = usuarioUseCase.listarUsuariosNoAdmitidos();
        List<UsuarioNoAdmitidoDtoOutput> usuarioNoAdmitidoDtoOutputList = usuarioToNoAdmitidoOutputMapper.toOutput(usuariosNoAdmitidosList);
        return usuarioNoAdmitidoDtoOutputList;
    }

    @PutMapping(value = "/admitirUsuario")
    public ResponseEntity<String> admitirUsuario(@RequestBody UsuarioNoAdmitidoDtoInput usuarioNoAdmitidoDtoInput) {

        usuarioUseCase.admitirUsuario(usuarioNoAdmitidoDtoInput);
        return ResponseEntity.status(HttpStatus.OK).body("Se ha admitido al usuario");
    }

    @GetMapping("/login2")
    public ResponseEntity<List<Usuario>> login() {
        try {
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }
}
