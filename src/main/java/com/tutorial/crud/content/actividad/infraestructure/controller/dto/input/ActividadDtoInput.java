package com.tutorial.crud.content.actividad.infraestructure.controller.dto.input;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ActividadDtoInput {

    public String titulo;
    public String fecha;
    public String descripcion;
    public String etapaEducativa;
    public List<String> fotos=new ArrayList<>();
}
