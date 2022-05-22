package com.tutorial.crud.security.infraestructure.controller.dto.input;

import com.tutorial.crud.security.domain.enums.RolNombre;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioNoAdmitidoDtoInput {

    public String nombreUsuario;
    public Boolean activo;
    public RolNombre rol;


}
