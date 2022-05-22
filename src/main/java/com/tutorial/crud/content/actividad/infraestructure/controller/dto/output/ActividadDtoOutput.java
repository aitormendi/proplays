package com.tutorial.crud.content.actividad.infraestructure.controller.dto.output;


import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ActividadDtoOutput {

    public Integer id;
    public String titulo;
    public String fecha;
    public String descripcion;
    public String etapaEducativa;
    public boolean favorita;
    public List<String> fotos;
}
