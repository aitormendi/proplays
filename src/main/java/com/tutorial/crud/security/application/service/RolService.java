package com.tutorial.crud.security.application.service;

import com.tutorial.crud.security.domain.entity.Rol;
import com.tutorial.crud.security.domain.enums.RolNombre;
import com.tutorial.crud.security.infraestructure.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class RolService {

    @Autowired
    RolRepository rolRepository;

    public Optional<Rol> getByRolNombre(RolNombre rolNombre){
        return rolRepository.findByRolNombre(rolNombre);
    }


    public void save(Rol rol){
        rolRepository.save(rol);
    }
}
