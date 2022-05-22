package com.tutorial.crud.content.actividad_usuario.application.mapper;

import com.tutorial.crud.security.domain.entity.Usuario;
import com.tutorial.crud.security.infraestructure.controller.dto.output.UsuarioDtoOutput;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActividadUsuarioToOutputMapper {

    List<UsuarioDtoOutput> toOutput(List<Usuario>usuarioList);

}
