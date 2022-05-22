package com.tutorial.crud.content.actividad_usuario.infrastructure.controller.input;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class InsertarActividadInputDto {

    public Integer actividadId;
    public String nombreUsuario;

}
