package com.tutorial.crud.content.actividad_fotos.application.mapper;

import com.tutorial.crud.content.actividad_fotos.domain.entity.ActividadFoto;
import com.tutorial.crud.content.actividad_fotos.infrastructure.controller.dto.output.ActividadFotoDtoOutput;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActividadFotoToOutputMapper {

    List<ActividadFotoDtoOutput> toOutput(List<ActividadFoto> actividadFoto);
}
