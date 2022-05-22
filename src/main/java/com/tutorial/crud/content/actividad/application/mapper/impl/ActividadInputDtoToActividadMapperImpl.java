package com.tutorial.crud.content.actividad.application.mapper.impl;

import com.tutorial.crud.content.actividad.application.mapper.ActividadInputDtoToActividadMapper;
import com.tutorial.crud.content.actividad.domain.entity.Actividad;
import com.tutorial.crud.content.actividad.infraestructure.controller.dto.input.ActividadDtoInput;
import org.springframework.stereotype.Component;

@Component
public class ActividadInputDtoToActividadMapperImpl implements ActividadInputDtoToActividadMapper {
    @Override
    public Actividad toEntity(ActividadDtoInput actividadDtoInput) {

        Actividad a=new Actividad();
        if (actividadDtoInput.getTitulo()!=null){
            a.setTitulo(actividadDtoInput.getTitulo());
        }
        if (actividadDtoInput.getFecha()!=null){
            a.setFecha(actividadDtoInput.getFecha());
        }
        if (actividadDtoInput.getDescripcion()!=null){
            a.setDescripcion(actividadDtoInput.getDescripcion());
        }
        if (actividadDtoInput.getEtapaEducativa()!=null){
            a.setEtapaEducativa(actividadDtoInput.getEtapaEducativa());
        }

        return a;
    }
}

//InputDto (objeto de entrada) te lo envia front -> Lo conviertes a entidad de base de datos -> Normalmente se devuelve el outputDto (objeto de salida)