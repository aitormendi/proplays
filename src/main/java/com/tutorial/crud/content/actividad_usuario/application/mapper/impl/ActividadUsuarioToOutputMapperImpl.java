package com.tutorial.crud.content.actividad_usuario.application.mapper.impl;

import com.tutorial.crud.content.actividad_usuario.application.mapper.ActividadUsuarioToOutputMapper;
import com.tutorial.crud.content.actividad_usuario.domain.entity.ActividadUsuario;
import com.tutorial.crud.security.domain.entity.Usuario;
import com.tutorial.crud.security.infraestructure.controller.dto.output.UsuarioDtoOutput;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ActividadUsuarioToOutputMapperImpl implements ActividadUsuarioToOutputMapper {


    @Override
    public List<UsuarioDtoOutput> toOutput(List<Usuario> usuarioList) {
        List<UsuarioDtoOutput>usuarioDtoOutputList=new ArrayList<>();
        // Se crea un constructor de usuarioDtoOutput por cada Usuario de UsuarioList

        for (Usuario u:usuarioList) {
            UsuarioDtoOutput uo = new UsuarioDtoOutput();
            if(u.getId()!=null) {
                uo.setUsuarioId(u.getId());
            }
            if(u.getNombre()!=null) {
                uo.setNombre(u.getNombre());
            }
            usuarioDtoOutputList.add(uo);
        }
      return usuarioDtoOutputList;
    }
}
