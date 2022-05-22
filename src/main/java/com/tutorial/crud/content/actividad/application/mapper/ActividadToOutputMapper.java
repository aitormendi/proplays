package com.tutorial.crud.content.actividad.application.mapper;

import com.tutorial.crud.content.actividad.domain.entity.Actividad;
import com.tutorial.crud.content.actividad.infraestructure.controller.dto.output.ActividadDtoOutput;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActividadToOutputMapper {

    //Se puede añadir una dependencia que haga el mappeo sin tener que hacerlo
    // tu es decir lo hace automático spring

    //ActividadToOutputMapper INSTANCE= Mappers.getMapper(ActividadToOutputMapper.class);


    List<ActividadDtoOutput> toOutput(List<Actividad>actividadList);

    ActividadDtoOutput toOutput(Actividad actividad,List<String>fotos);

    ActividadDtoOutput toOutput(Actividad actividad);
}
