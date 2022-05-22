package com.tutorial.crud.content.actividad_usuario.infrastructure.controller;

import com.tutorial.crud.content.actividad.application.mapper.ActividadToOutputMapper;
import com.tutorial.crud.content.actividad.domain.entity.Actividad;
import com.tutorial.crud.content.actividad.infraestructure.controller.dto.output.ActividadDtoOutput;
import com.tutorial.crud.content.actividad_fotos.application.ActividadFotoUseCase;
import com.tutorial.crud.content.actividad_usuario.application.ActividadUsuarioUseCase;
import com.tutorial.crud.content.actividad_usuario.application.mapper.ActividadUsuarioToOutputMapper;
import com.tutorial.crud.content.actividad_usuario.domain.entity.ActividadUsuario;
import com.tutorial.crud.content.actividad_usuario.infrastructure.controller.input.InsertarActividadInputDto;
import com.tutorial.crud.content.actividad_usuario.infrastructure.controller.output.ActividadUsuarioInscritoDtoOutput;
import com.tutorial.crud.security.domain.entity.Usuario;
import com.tutorial.crud.security.infraestructure.controller.dto.output.UsuarioDtoOutput;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/actividadUsuario")
public class ActividadUsuarioController {


    @Autowired
    private ActividadUsuarioUseCase actividadUsuarioUseCase;

    @Autowired
    private ActividadFotoUseCase actividadFotoUseCase;

    @Autowired
    private ActividadUsuarioToOutputMapper actividadUsuarioToOutputMapper;

    @Autowired
    private ActividadToOutputMapper actividadToOutputMapper;



    @PostMapping(value = "/inscribirActividad")
    public ResponseEntity<String> inscribirActividad(@RequestBody InsertarActividadInputDto insertarActividadInputDto) {

        //Revisamos que no se haya inscrito ya a una actividad
        List<ActividadUsuario>actividadUsuarioList=actividadUsuarioUseCase.buscarActividadesInscritasUsuario(insertarActividadInputDto.getNombreUsuario());
        if (!actividadUsuarioList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("No puedes inscribirte en más de una actividad");
        }
        //Buscamos si la relacion actividadUsuario ya existe en favoritos y la setteamos a inscrito
        ActividadUsuario au=actividadUsuarioUseCase.buscarActividadUsuario(insertarActividadInputDto.getActividadId(), insertarActividadInputDto.getNombreUsuario());
        if (au!=null){
            actividadUsuarioUseCase.inscribirActividadRelacionCreada(au);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario inscrito en la actividad");
        }
        //Si no habrá que crear la relación con inscrito a true
        actividadUsuarioUseCase.inscribirActividad(insertarActividadInputDto.getActividadId(), insertarActividadInputDto.getNombreUsuario());
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario inscrito en la actividad");
    }

    @GetMapping(value = "/usuariosPorActividad")
    public List<UsuarioDtoOutput> listarUsuariosPorActividad(@RequestParam Integer actividadId) {
        List<Usuario> usuarioList = actividadUsuarioUseCase.listarUsuariosPorActividad(actividadId);
        List<UsuarioDtoOutput> usuarioDtoOutputList = actividadUsuarioToOutputMapper.toOutput(usuarioList);
        return usuarioDtoOutputList;
    }

    @GetMapping(value = "/listarActividadesFavoritas")
    public List<ActividadDtoOutput> listarActividadFavorito(@RequestParam("nombreUsuario") String nombreUsuario) {
        List<Actividad> actividadList = actividadUsuarioUseCase.listarActividadesPorFavoritos(nombreUsuario);
        List<ActividadDtoOutput>actividadDtoOutputList=actividadFotoUseCase.getActividadConFotos(actividadList);
        return actividadDtoOutputList;
    }


    @GetMapping(value = "/listarActividadesInscritas")
    public List<ActividadDtoOutput> listarActividadInscrita(@RequestParam("nombreUsuario") String nombreUsuario) {
        List<ActividadUsuario> actividadUsuarioList = actividadUsuarioUseCase.buscarActividadesInscritasUsuario(nombreUsuario);
        List<Actividad>actividadList=new ArrayList<>();
        for (ActividadUsuario au:actividadUsuarioList) {
            actividadList.add(au.getActividad());
        }
        List<ActividadDtoOutput>actividadDtoOutputList=actividadFotoUseCase.getActividadConFotos(actividadList);
        return actividadDtoOutputList;
    }



    @PostMapping(value="/insertarFavoritos")
    public ResponseEntity<String> inscribirFavoritos(@RequestBody InsertarActividadInputDto insertarActividadInputDto) {

        //Revisamos que no esté inscrito a favoritos la actividad por el mismo usuario
        ActividadUsuario au=actividadUsuarioUseCase.buscarActividadUsuario(insertarActividadInputDto.getActividadId(), insertarActividadInputDto.getNombreUsuario());
        if (au!=null && (au.getFavorito()!=null && au.getFavorito())){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Ya tienes inscrito en favoritos esta actividad");
        }
        //Buscamos si la relacion actividadUsuario ya existe y la setteamos a favoritos
        if (au!=null){
            actividadUsuarioUseCase.insertarFavoritosActividadUsuarioCreada(au);
            return ResponseEntity.status(HttpStatus.CREATED).body("Actividad añadida a favoritos");
        }
        //Si no se encuentra en favoritos se crea la relación y se guarda
        actividadUsuarioUseCase.inscribirActividadFavoritos(insertarActividadInputDto.getActividadId(), insertarActividadInputDto.getNombreUsuario());

        return ResponseEntity.status(HttpStatus.CREATED).body("Actividad añadida a favoritos");
    }

    @GetMapping(value="/listarUsuariosInscritosActividad/{actividadId}")
        public ActividadUsuarioInscritoDtoOutput listarActividadInscrita(@PathVariable Integer actividadId) {
        ActividadUsuarioInscritoDtoOutput actividadUsuarioInscritoDtoOutput = actividadUsuarioUseCase.listarUsuariosInscritosActividad(actividadId);
        return actividadUsuarioInscritoDtoOutput;
    }

}
