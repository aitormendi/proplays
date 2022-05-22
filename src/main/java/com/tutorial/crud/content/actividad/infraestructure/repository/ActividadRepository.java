package com.tutorial.crud.content.actividad.infraestructure.repository;

import com.tutorial.crud.content.actividad.domain.entity.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad,Integer> {

    List<Actividad> findAllByTituloContaining(String nombre);
}
