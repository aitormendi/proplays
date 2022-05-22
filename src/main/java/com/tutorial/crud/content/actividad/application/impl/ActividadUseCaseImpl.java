package com.tutorial.crud.content.actividad.application.impl;

import com.tutorial.crud.content.actividad.application.ActividadUseCase;
import com.tutorial.crud.content.actividad.application.mapper.ActividadInputDtoToActividadMapper;
import com.tutorial.crud.content.actividad.domain.entity.Actividad;
import com.tutorial.crud.content.actividad.infraestructure.controller.dto.input.ActividadDtoInput;
import com.tutorial.crud.content.actividad.infraestructure.repository.ActividadRepository;
import com.tutorial.crud.content.actividad_fotos.domain.entity.ActividadFoto;
import com.tutorial.crud.content.actividad_fotos.infrastructure.repository.ActividadFotoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ActividadUseCaseImpl implements ActividadUseCase {

    @Autowired
    private ActividadInputDtoToActividadMapper actividadInputDtoToActividadMapper;

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private ActividadFotoRepository actividadFotoRepository;

    @Override
    public Actividad crearActividad(ActividadDtoInput actividadDtoInput) {
        Actividad actividad = actividadInputDtoToActividadMapper.toEntity(actividadDtoInput);
        if(!actividadDtoInput.getFotos().isEmpty()){
            for (String foto:actividadDtoInput.getFotos()) {
                ActividadFoto af= new ActividadFoto();
                af.setActividad(actividad);
                af.setFoto(foto);
                actividadFotoRepository.saveAndFlush(af);
            }
        }
        actividadRepository.save(actividad);
        return actividad;
    }

    @Override
    public List<Actividad> listarActividades(String nombre) {

        List<Actividad> actividadList;

        if (nombre != null) {
            actividadList = actividadRepository.findAllByTituloContaining(nombre);
        } else {
            actividadList = actividadRepository.findAll();
        }
        //Esto poner en una línea prefiero dejarlo así para hacer pruebas
        //  List<ActividadDtoOutput> actividadDtoOutputList = actividadToOutputMapper.toOutput(actividadList);

        return actividadList;

    }

    @Override
    public Actividad getActividad(Integer actividadId) {

        Actividad actividad=actividadRepository.findById(actividadId).orElse(null);

        return actividad;
    }


    @Override
    public Actividad editarActividad(Integer actividadId, ActividadDtoInput actividadDtoInput)  {

        //Encontramos la actividad
        Actividad actividad= actividadRepository.findById(actividadId).orElse(null);
        List<ActividadFoto>actividadFotoList=actividadFotoRepository.findAllByActividadId(actividadId);

        for (ActividadFoto af:actividadFotoList) {
            actividadFotoRepository.deleteById(af.getId());
        }

        //Desde front pasamos los campos de actividad para settearlos
      if (actividadDtoInput.getTitulo()!=null) {
          actividad.setTitulo(actividadDtoInput.getTitulo());
      }
      if (actividadDtoInput.getFecha()!=null) {
          actividad.setFecha(actividadDtoInput.getFecha());
      }
      if (actividadDtoInput.getEtapaEducativa()!=null) {
          actividad.setEtapaEducativa(actividadDtoInput.getEtapaEducativa());
      }
      if (actividadDtoInput.getDescripcion()!=null) {
          actividad.setDescripcion(actividadDtoInput.getDescripcion());
      }
      //Eliminamos todas las relaciones de fotos existentes
      //  actividadFotoRepository.deleteAll(actividad.getActividadFotoList());

        //actividadFotoRepository.deleteAll(actividadFotoList);

      //  actividadRepository.save(actividad);
        return actividad;
    }

    @Override
    public void eliminarActividad(Integer actividadId) {
        Actividad a= actividadRepository.findById(actividadId).orElse(null);
        actividadRepository.delete(a);
    }
}
