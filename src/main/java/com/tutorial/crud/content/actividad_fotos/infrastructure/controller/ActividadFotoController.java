package com.tutorial.crud.content.actividad_fotos.infrastructure.controller;

import com.tutorial.crud.content.actividad_fotos.application.ActividadFotoUseCase;
import com.tutorial.crud.content.actividad_fotos.application.mapper.ActividadFotoToOutputMapper;
import com.tutorial.crud.content.actividad_fotos.domain.entity.ActividadFoto;
import com.tutorial.crud.content.actividad_fotos.infrastructure.controller.dto.output.ActividadFotoDtoOutput;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/actividadFoto")
public class ActividadFotoController {

    @Autowired
    ActividadFotoUseCase actividadFotoUseCase;

    @Autowired
    ActividadFotoToOutputMapper actividadFotoToOutputMapper;


    @GetMapping(value = "/listarFotosPorActividad/{actividadId}")
    public List<ActividadFotoDtoOutput> listarFotosPorActividad(@PathVariable Integer actividadId) {

        List<ActividadFoto> actividadFotoList = actividadFotoUseCase.listarFotosActividad(actividadId);
        return actividadFotoToOutputMapper.toOutput(actividadFotoList);
    }

    //Revisar luego en front como insertar varias
    @PostMapping(value="/insertarFotosActividad")
    public ResponseEntity<String> insertarFotosActividad(@RequestParam Integer actividadId, @RequestParam List<String> fotos){
        actividadFotoUseCase.insertarFotosActividad(actividadId,fotos);
        return ResponseEntity.status(HttpStatus.CREATED).body("Fotos insertadas a la actividad");
    }


    @DeleteMapping(value="/eliminarFotosActividad/{actividadFotoId}")
    public ResponseEntity<String>  deleteFotosActividad(@PathVariable Integer actividadFotoId) {
        actividadFotoUseCase.eliminarFotosActividad(actividadFotoId);
        return ResponseEntity.status(HttpStatus.OK).body("Se ha eliminado la foto de la actividad correctamente");
    }
}



