package com.tutorial.crud.security.infraestructure.repository;

import com.tutorial.crud.content.actividad_usuario.domain.entity.ActividadUsuario;
import com.tutorial.crud.security.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
  //Revisar si existe el nombre de usuario
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    boolean existsByNombreUsuario(String nombreUsuario);
    boolean existsByEmail(String email);

    //Usuarios que tengan admitido a false
 /* @Query(value="SELECT * FROM Usuario WHERE activo is false",nativeQuery = true)
  List<Usuario> listarUsuarioNoAdmitidos();*/

  List<Usuario> findAllByActivoIsFalse();

}
