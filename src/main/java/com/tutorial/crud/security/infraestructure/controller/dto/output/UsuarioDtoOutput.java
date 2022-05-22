package com.tutorial.crud.security.infraestructure.controller.dto.output;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDtoOutput {
    private Integer usuarioId;
    private String nombre;
}
