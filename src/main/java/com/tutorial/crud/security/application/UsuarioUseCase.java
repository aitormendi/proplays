package com.tutorial.crud.security.application;

import com.tutorial.crud.security.domain.entity.Usuario;
import com.tutorial.crud.security.infraestructure.controller.dto.input.UsuarioNoAdmitidoDtoInput;
import com.tutorial.crud.security.infraestructure.controller.dto.output.UsuarioNoAdmitidoDtoOutput;

import java.util.List;

public interface UsuarioUseCase {


    List<Usuario>listarUsuariosNoAdmitidos();

    void admitirUsuario(UsuarioNoAdmitidoDtoInput usuarioNoAdmitidoDtoInput);


}
