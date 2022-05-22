package com.tutorial.crud.content.actividad.application.mapper;

import com.tutorial.crud.content.actividad.domain.entity.Actividad;
import com.tutorial.crud.content.actividad.infraestructure.controller.dto.input.ActividadDtoInput;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActividadInputDtoToActividadMapper {

    //Front va a pasar un ActividadInputDTO este va a ser convertido en entidad de base de datos actividad
    //Y esta actividad va a ser guardada en base de datos

    Actividad toEntity(ActividadDtoInput actividadDtoInput);

}
