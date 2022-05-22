package com.tutorial.crud.content.actividad_fotos.application.mapper.impl;

import com.tutorial.crud.content.actividad_fotos.application.mapper.ActividadFotoToOutputMapper;
import com.tutorial.crud.content.actividad_fotos.domain.entity.ActividadFoto;
import com.tutorial.crud.content.actividad_fotos.infrastructure.controller.dto.output.ActividadFotoDtoOutput;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ActividadFotoToOutputMapperImpl implements ActividadFotoToOutputMapper {

    @Override
    public List<ActividadFotoDtoOutput> toOutput(List<ActividadFoto> actividadFotoList) {
     List<ActividadFotoDtoOutput>actividadFotoDtoOutputList=new ArrayList<>();
        for (ActividadFoto af:actividadFotoList) {
            ActividadFotoDtoOutput afo=new ActividadFotoDtoOutput();
            if (af.getId()!=null){
                afo.setActividadFotoId(af.getId());
            }
            if (af.getFoto()!=null){
                afo.setFotos(af.getFoto());
            }
            if (af.getActividad()!=null){
                afo.setAcitividadId(af.getActividad().getId());
            }
            actividadFotoDtoOutputList.add(afo);
        }
        return actividadFotoDtoOutputList;
    }
}
