package com.tutorial.crud.content.actividad_usuario.infrastructure.controller.output;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ActividadUsuarioInscritoDtoOutput {

    public String tituloActividad;
    public List<String> nombreUsuarioList;
}
