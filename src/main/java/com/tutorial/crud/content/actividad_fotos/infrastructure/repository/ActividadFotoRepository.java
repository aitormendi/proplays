package com.tutorial.crud.content.actividad_fotos.infrastructure.repository;

import com.tutorial.crud.content.actividad_fotos.domain.entity.ActividadFoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActividadFotoRepository extends JpaRepository<ActividadFoto,Integer> {

    List<ActividadFoto> findAllByActividadId(Integer actividadId);

    List<ActividadFoto> findAllByActividadIdIn(List<Integer> actividadIdList);

}
