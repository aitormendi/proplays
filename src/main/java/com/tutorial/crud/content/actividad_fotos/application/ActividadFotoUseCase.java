package com.tutorial.crud.content.actividad_fotos.application;

import com.tutorial.crud.content.actividad.domain.entity.Actividad;
import com.tutorial.crud.content.actividad.infraestructure.controller.dto.output.ActividadDtoOutput;
import com.tutorial.crud.content.actividad_fotos.domain.entity.ActividadFoto;

import java.util.List;

public interface ActividadFotoUseCase {

    List<ActividadFoto> listarFotosActividad(Integer actividadId);

    List<ActividadDtoOutput>getActividadConFotos(List<Actividad>actividadIdList);

    void insertarFotosActividad(Integer actividad, List<String> fotos);

    void eliminarFotosActividad(Integer actividadFotoId);

}
