package com.tutorial.crud.content.actividad_usuario.application;

import com.tutorial.crud.content.actividad.domain.entity.Actividad;
import com.tutorial.crud.content.actividad_usuario.domain.entity.ActividadUsuario;
import com.tutorial.crud.content.actividad_usuario.infrastructure.controller.output.ActividadUsuarioInscritoDtoOutput;
import com.tutorial.crud.security.domain.entity.Usuario;
import com.tutorial.crud.security.infraestructure.controller.dto.output.UsuarioDtoOutput;

import java.util.List;

public interface ActividadUsuarioUseCase {

    void inscribirActividad (Integer actividadId, String nombreUsuario);

    List<Usuario> listarUsuariosPorActividad (Integer actividadId);

    List<Actividad> listarActividadesPorFavoritos (String nombreUsuario);

    //Cuando la relación no está en favoritos
    void inscribirActividadFavoritos (Integer actividadId, String nombreUsuario);
    //Cuando la relación está en favoritos
    void inscribirActividadRelacionCreada(ActividadUsuario au);


    ActividadUsuario buscarActividadUsuario(Integer actividadId,String nombreUsuario);

    List<ActividadUsuario>buscarActividadesInscritasUsuario(String nombreUsuario);

    void insertarFavoritosActividadUsuarioCreada(ActividadUsuario au);

    ActividadUsuarioInscritoDtoOutput listarUsuariosInscritosActividad(Integer actividadId);

}
