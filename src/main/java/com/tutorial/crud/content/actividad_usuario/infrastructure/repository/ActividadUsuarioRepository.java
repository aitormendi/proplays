package com.tutorial.crud.content.actividad_usuario.infrastructure.repository;

import com.tutorial.crud.content.actividad_usuario.domain.entity.ActividadUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActividadUsuarioRepository extends JpaRepository<ActividadUsuario,Integer> {

    @Query(value="SELECT * FROM RELA_ACTIVIDAD_USUARIO WHERE usuario_id=?1 and favorito is true",nativeQuery = true)
    List<ActividadUsuario> findAllFavoritosByUsuario(Integer usuarioID);

    //Buscar por usuario y actividad para que no me cree una relación ya creada
    ActividadUsuario findByUsuarioIdAndActividadId(Integer usuarioID,Integer actividadId);

    @Query(value="SELECT * FROM RELA_ACTIVIDAD_USUARIO WHERE usuario_id=?1 and inscrito is true",nativeQuery = true)
    List<ActividadUsuario> findAllactividadesInscritasByUsuario(Integer usuarioID);

    @Query(value="SELECT * FROM RELA_ACTIVIDAD_USUARIO WHERE actividad_id=?1 and inscrito is true",nativeQuery = true)
    List<ActividadUsuario> findAllusuariosInscritasEnActividad(Integer actividadId);

   // List<ActividadUsuario> findAllByUsuarioAndFavoritoTrue(Integer usuarioID);
}
