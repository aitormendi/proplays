package com.tutorial.crud.security.application.impl;

import com.tutorial.crud.security.application.UsuarioUseCase;
import com.tutorial.crud.security.domain.entity.Rol;
import com.tutorial.crud.security.domain.entity.Usuario;
import com.tutorial.crud.security.infraestructure.controller.dto.input.UsuarioNoAdmitidoDtoInput;
import com.tutorial.crud.security.infraestructure.controller.dto.output.UsuarioNoAdmitidoDtoOutput;
import com.tutorial.crud.security.infraestructure.repository.RolRepository;
import com.tutorial.crud.security.infraestructure.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UsuarioUseCaseImpl implements UsuarioUseCase {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    RolRepository rolRepository;

    @Override
    public List<Usuario> listarUsuariosNoAdmitidos() {

        List<Usuario>usuarioList=usuarioRepository.findAllByActivoIsFalse();

        return usuarioList;
    }

    @Override
    public void admitirUsuario(UsuarioNoAdmitidoDtoInput usuarioNoAdmitidoDtoInput) {
        Usuario usuario=usuarioRepository.findByNombreUsuario(usuarioNoAdmitidoDtoInput.getNombreUsuario()).orElse(null);
        Set<Rol> listadoRoles=usuario.getRoles();
        //Eliminamos el rol de alumno por defecto
        if (!listadoRoles.isEmpty()){
            listadoRoles.clear();
            usuario.setRoles(listadoRoles);
         //   usuarioRepository.save(usuario);
        }
        //Insertamos el rol que elija el admin
        Set<Rol>rolNuevoList=new HashSet<>();
        Rol rol=rolRepository.findByRolNombre(usuarioNoAdmitidoDtoInput.getRol()).orElse(null);
        rolNuevoList.add(rol);
        if (!rolNuevoList.isEmpty()) {
            usuario.setRoles(rolNuevoList);
          //  usuarioRepository.save(usuario);
        }
        //Tambien actualizamos el activo a true que nos llegará de front
        usuario.setActivo(usuarioNoAdmitidoDtoInput.getActivo());
        usuarioRepository.save(usuario);

    }
}
