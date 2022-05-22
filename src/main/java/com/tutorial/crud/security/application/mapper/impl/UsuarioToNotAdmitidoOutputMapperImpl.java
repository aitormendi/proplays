package com.tutorial.crud.security.application.mapper.impl;

import com.tutorial.crud.security.application.mapper.UsuarioToNoAdmitidoOutputMapper;
import com.tutorial.crud.security.domain.entity.Rol;
import com.tutorial.crud.security.domain.entity.Usuario;
import com.tutorial.crud.security.infraestructure.controller.dto.output.UsuarioNoAdmitidoDtoOutput;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioToNotAdmitidoOutputMapperImpl implements UsuarioToNoAdmitidoOutputMapper {
    @Override
    public List<UsuarioNoAdmitidoDtoOutput> toOutput(List<Usuario> usuarioList) {
        List<UsuarioNoAdmitidoDtoOutput>usuarioNoAdmitidoDtoOutputList=new ArrayList<>();
        for (Usuario u:usuarioList) {
            UsuarioNoAdmitidoDtoOutput uo=new UsuarioNoAdmitidoDtoOutput();
            if (u.getNombreUsuario()!=null){
                uo.setNombreUsuario(u.getNombreUsuario());
            }
            if(u.getNombre()!=null){
                uo.setNombre(u.getNombre());
            }
            if (u.getEmail()!=null){
                uo.setEmail(u.getEmail());
            }
            if (u.getRoles()!=null){

               /** List<String> roles
                        = new ArrayList(u.getRoles());*/
               List<String>nombreRolesList=new ArrayList<>();
               List<Rol>rolesList=new ArrayList<>(u.getRoles());
                for (Rol r:rolesList) {
                    nombreRolesList.add(r.getRolNombre().toString());
                }
                if (!nombreRolesList.isEmpty()){
                    uo.setRoles(nombreRolesList);

                }
            }
            if (u.getActivo()!=null){
                uo.setActivo(u.getActivo());
            }
            usuarioNoAdmitidoDtoOutputList.add(uo);
        }
        return usuarioNoAdmitidoDtoOutputList;
    }
}
