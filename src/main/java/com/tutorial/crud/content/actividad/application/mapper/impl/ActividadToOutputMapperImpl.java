package com.tutorial.crud.content.actividad.application.mapper.impl;

import com.tutorial.crud.content.actividad.application.mapper.ActividadToOutputMapper;
import com.tutorial.crud.content.actividad.domain.entity.Actividad;
import com.tutorial.crud.content.actividad.infraestructure.controller.dto.output.ActividadDtoOutput;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ActividadToOutputMapperImpl implements ActividadToOutputMapper {

    @Override
    public List<ActividadDtoOutput> toOutput(List<Actividad> actividadList) {
        List<ActividadDtoOutput> actividadDtoOutputList = new ArrayList<>();
        for (Actividad a : actividadList) {
            ActividadDtoOutput a2 = new ActividadDtoOutput();
            if (a.getId() != null) {
                a2.setId(a.getId());
            }
            if (a.getDescripcion() != null) {
                a2.setDescripcion(a.getDescripcion());
            }
            if (a.getTitulo() != null) {
                a2.setTitulo(a.getTitulo());
            }
            if (a.getFecha() != null) {
                a2.setFecha(a.getFecha());
            }
            if (a.getEtapaEducativa() != null) {
                a2.setEtapaEducativa(a.getEtapaEducativa());
            }
            actividadDtoOutputList.add(a2);
        }
        return actividadDtoOutputList;
    }

    @Override
    public ActividadDtoOutput toOutput(Actividad actividad, List<String> fotos) {
        ActividadDtoOutput a2 = new ActividadDtoOutput();
        if (actividad.getId() != null) {
            a2.setId(actividad.getId());
        }
        if (actividad.getDescripcion() != null) {
            a2.setDescripcion(actividad.getDescripcion());
        }
        if (actividad.getTitulo() != null) {
            a2.setTitulo(actividad.getTitulo());
        }
        if (actividad.getFecha() != null) {
            a2.setFecha(actividad.getFecha());
        }
        if (actividad.getEtapaEducativa() != null) {
            a2.setEtapaEducativa(actividad.getEtapaEducativa());
        }
        if (!fotos.isEmpty()) {
            a2.setFotos(fotos);
        }else{
            a2.setFotos(new ArrayList<>());
        }
        return a2;
    }

    @Override
    public ActividadDtoOutput toOutput(Actividad actividad) {
        ActividadDtoOutput a2 = new ActividadDtoOutput();
        if (actividad.getId() != null) {
            a2.setId(actividad.getId());
        }
        if (actividad.getDescripcion() != null) {
            a2.setDescripcion(actividad.getDescripcion());
        }
        if (actividad.getTitulo() != null) {
            a2.setTitulo(actividad.getTitulo());
        }
        if (actividad.getFecha() != null) {
            a2.setFecha(actividad.getFecha());
        }
        if (actividad.getEtapaEducativa() != null) {
            a2.setEtapaEducativa(actividad.getEtapaEducativa());
        }
        return a2;
    }

}
