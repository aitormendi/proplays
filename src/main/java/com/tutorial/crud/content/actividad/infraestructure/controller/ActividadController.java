package com.tutorial.crud.content.actividad.infraestructure.controller;

import com.tutorial.crud.content.actividad.application.ActividadUseCase;
import com.tutorial.crud.content.actividad.application.mapper.ActividadToOutputMapper;
import com.tutorial.crud.content.actividad.domain.entity.Actividad;

import com.tutorial.crud.content.actividad.infraestructure.controller.dto.input.ActividadDtoInput;
import com.tutorial.crud.content.actividad.infraestructure.controller.dto.output.ActividadDtoOutput;
import com.tutorial.crud.content.actividad_fotos.application.ActividadFotoUseCase;
import com.tutorial.crud.content.actividad_fotos.domain.entity.ActividadFoto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/actividad")
public class ActividadController {


    //Quizás mejor para que se haga automático con dependencias los mappers
    @Autowired
    private ActividadToOutputMapper actividadToOutputMapper;

    private final ActividadUseCase actividadUseCase;

    private final ActividadFotoUseCase actividadFotoUseCase;

    //La búsqueda por nombre es por título
    // @PreAuthorize("hasRole('ROLE_ALUMNO')")
    @GetMapping("/listaActividades")
    public List<ActividadDtoOutput> listarActividades(@RequestParam(value = "tituloActividad", required = false) String tituloActividad) {

        List<Actividad> actividadList = actividadUseCase.listarActividades(tituloActividad);
        List<ActividadDtoOutput> actividadFinalList=actividadFotoUseCase.getActividadConFotos(actividadList);
        return actividadFinalList;
    }


    @GetMapping("/getActividad/{actividadId}")
    public ActividadDtoOutput listarActividad(@PathVariable(value = "actividadId", required = false) Integer actividadId) {

        Actividad actividad = actividadUseCase.getActividad(actividadId);
        List<ActividadFoto>actividadFotoList=actividadFotoUseCase.listarFotosActividad(actividadId);
        List<String>fotos=new ArrayList<>();
        for (ActividadFoto af:actividadFotoList) {
            fotos.add(af.getFoto());
        }

        return actividadToOutputMapper.toOutput(actividad,fotos);
    }



    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/crearActividad")
    public ResponseEntity<ActividadDtoOutput> crearActividad(@RequestBody ActividadDtoInput nuevaActividad) {

        //Llamo al useCase en el cual devuelvo una actividad y
        // no realizo un método void para que luego en la respuesta pueda mappear y devolver el objeto de salida
        Actividad a = actividadUseCase.crearActividad(nuevaActividad);
     //   return ResponseEntity.status(HttpStatus.CREATED).body("Se ha creado la actividad");

        return ResponseEntity.status(HttpStatus.CREATED).body(actividadToOutputMapper.toOutput(a));
    }

    /**
     * Mismo dto para crear como para editar
     */
    @PutMapping(value = "/editarActividad/{actividadId}")
    public ResponseEntity<String> editarActividad(@PathVariable Integer actividadId, @RequestBody ActividadDtoInput nuevaActividad) {

        //1ºBuscamos la actividad que queremos modificar y la modificamos con la información que pasamos en el primer pasoeliminamos las relaciones con fotos
        Actividad a = actividadUseCase.editarActividad(actividadId, nuevaActividad);
        //2ºEn el segundo paso insertamos las fotos que pasamos por parámetro
        actividadFotoUseCase.insertarFotosActividad(actividadId, nuevaActividad.getFotos());
        return ResponseEntity.status(HttpStatus.OK).body("Se ha editado la actividad");
    }

    @DeleteMapping(value="/eliminarActividad/{actividadId}")
    public ResponseEntity<String>  deleteVideojuego(@PathVariable Integer actividadId) {
        actividadUseCase.eliminarActividad(actividadId);
        return ResponseEntity.status(HttpStatus.OK).body("Se ha eliminado la actividad correctamente");

    }



}
