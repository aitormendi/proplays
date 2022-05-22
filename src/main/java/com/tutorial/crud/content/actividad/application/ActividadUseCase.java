package com.tutorial.crud.content.actividad.application;

import com.tutorial.crud.content.actividad.domain.entity.Actividad;
import com.tutorial.crud.content.actividad.infraestructure.controller.dto.input.ActividadDtoInput;

import java.util.List;

public interface ActividadUseCase {

    Actividad crearActividad(ActividadDtoInput actividadDtoInput);

    List<Actividad> listarActividades(String nombre);

    Actividad getActividad(Integer actividadId);

    Actividad editarActividad(Integer actividadId,ActividadDtoInput actividadDtoInput);

    void eliminarActividad(Integer actividadId);

}
