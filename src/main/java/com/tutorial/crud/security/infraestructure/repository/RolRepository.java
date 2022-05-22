package com.tutorial.crud.security.infraestructure.repository;

import com.tutorial.crud.security.domain.entity.Rol;
import com.tutorial.crud.security.domain.enums.RolNombre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol,Integer> {
    Optional<Rol> findByRolNombre(RolNombre rolNombre);




}
