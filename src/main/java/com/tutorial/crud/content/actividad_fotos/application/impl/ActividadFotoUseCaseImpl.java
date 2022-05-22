package com.tutorial.crud.content.actividad_fotos.application.impl;

import com.tutorial.crud.content.actividad.application.mapper.ActividadToOutputMapper;
import com.tutorial.crud.content.actividad.domain.entity.Actividad;
import com.tutorial.crud.content.actividad.infraestructure.controller.dto.output.ActividadDtoOutput;
import com.tutorial.crud.content.actividad.infraestructure.repository.ActividadRepository;
import com.tutorial.crud.content.actividad_fotos.application.ActividadFotoUseCase;
import com.tutorial.crud.content.actividad_fotos.domain.entity.ActividadFoto;
import com.tutorial.crud.content.actividad_fotos.infrastructure.repository.ActividadFotoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ActividadFotoUseCaseImpl implements ActividadFotoUseCase {

    @Autowired
    ActividadFotoRepository actividadFotoRepository;

    @Autowired
    ActividadRepository actividadRepository;

    @Autowired
    private ActividadToOutputMapper actividadToOutputMapper;


    @Override
    public List<ActividadFoto> listarFotosActividad(Integer actividadId) {

        List<ActividadFoto> actividadFotoList = actividadFotoRepository.findAllByActividadId(actividadId);

        return actividadFotoList;
    }

    @Override
    public List<ActividadDtoOutput> getActividadConFotos(List<Actividad> actividadList) {
        List<ActividadDtoOutput>actividadFotosList=new ArrayList<>();
        for (Actividad actividad:actividadList) {
            List<ActividadFoto> actividadFotoList= listarFotosActividad(actividad.getId());
            List<String>fotosActividad=new ArrayList<>();
            for (ActividadFoto af:actividadFotoList) {
                String foto=af.getFoto();
                if (foto!=null) {
                    fotosActividad.add(foto);
                }
            }
            ActividadDtoOutput af=actividadToOutputMapper.toOutput(actividad,fotosActividad);
            actividadFotosList.add(af);
        }
        return actividadFotosList;
    }


    @Override
    public void insertarFotosActividad(Integer actividadId, List<String> fotos) {
        Actividad actividad = actividadRepository.findById(actividadId).orElse(null);
        //ActividadFoto actividadFoto=actividadFotoRepository.delete(actividadFotoList);
        if (fotos!=null && !fotos.isEmpty()) {
          for (String foto : fotos) {
              ActividadFoto af = new ActividadFoto();
              af.setActividad(actividad);
              af.setFoto(foto);
              actividadFotoRepository.save(af);
          }
      }
    }

    @Override
    public void eliminarFotosActividad(Integer actividadFotoId) {
        ActividadFoto af=actividadFotoRepository.findById(actividadFotoId).orElse(null);
        actividadFotoRepository.delete(af);
    }
}
