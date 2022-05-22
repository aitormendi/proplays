package com.tutorial.crud.security.infraestructure.controller.dto.output;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioNoAdmitidoDtoOutput {

    public String nombreUsuario;
    public String nombre;
    public String email;
    public List<String> roles=new ArrayList();
    public Boolean activo;

}
