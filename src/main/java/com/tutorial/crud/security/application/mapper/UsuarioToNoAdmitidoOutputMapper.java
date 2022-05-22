package com.tutorial.crud.security.application.mapper;

import com.tutorial.crud.security.domain.entity.Usuario;
import com.tutorial.crud.security.infraestructure.controller.dto.output.UsuarioNoAdmitidoDtoOutput;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioToNoAdmitidoOutputMapper {

    List<UsuarioNoAdmitidoDtoOutput> toOutput(List<Usuario>usuarioList);

}
