package com.tutorial.crud.content.actividad_usuario.application.impl;

import com.tutorial.crud.content.actividad.domain.entity.Actividad;
import com.tutorial.crud.content.actividad.infraestructure.repository.ActividadRepository;
import com.tutorial.crud.content.actividad_usuario.application.ActividadUsuarioUseCase;
import com.tutorial.crud.content.actividad_usuario.domain.entity.ActividadUsuario;
import com.tutorial.crud.content.actividad_usuario.infrastructure.controller.output.ActividadUsuarioInscritoDtoOutput;
import com.tutorial.crud.content.actividad_usuario.infrastructure.repository.ActividadUsuarioRepository;
import com.tutorial.crud.security.domain.entity.Usuario;
import com.tutorial.crud.security.infraestructure.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ActividadUsuarioUseCaseImpl implements ActividadUsuarioUseCase {

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ActividadUsuarioRepository actividadUsuarioRepository;

    @Override
    public void inscribirActividad(Integer actividadId, String nombreUsuario) {
        Actividad a= actividadRepository.findById(actividadId).orElse(null);
        Usuario u = usuarioRepository.findByNombreUsuario(nombreUsuario).orElse(null);
        ActividadUsuario au = new ActividadUsuario();
        au.setActividad(a);
        au.setUsuario(u);
        au.setInscrito(true);
        actividadUsuarioRepository.save(au);
    }

    @Override
    public List<Usuario> listarUsuariosPorActividad(Integer actividadId) {
       Actividad a = actividadRepository.findById(actividadId).orElse(null);
       List <ActividadUsuario> actividadUsuarioList =  a.getActividadUsuarioList();
       List <Usuario> usuarioList = new ArrayList<>();
        for (ActividadUsuario au:actividadUsuarioList) {
            Usuario u = au.getUsuario();
            usuarioList.add(u);
        }
        return usuarioList;
    }

    @Override
    public List<Actividad> listarActividadesPorFavoritos(String nombreUsuario) {
        Usuario usuario=usuarioRepository.findByNombreUsuario(nombreUsuario).orElse(null);
        List <ActividadUsuario> actividadUsuarioList = actividadUsuarioRepository.findAllFavoritosByUsuario(usuario.getId());
        List <Actividad> actividadFavoritoList = new ArrayList<>();
        for (ActividadUsuario au:actividadUsuarioList) {
            Actividad a = au.getActividad();
            actividadFavoritoList.add(a);
        }
        return actividadFavoritoList;
    }

    @Override
    public void inscribirActividadFavoritos(Integer actividadId, String nombreUsuario) {
        Actividad a= actividadRepository.findById(actividadId).orElse(null);
        Usuario u = usuarioRepository.findByNombreUsuario(nombreUsuario).orElse(null);
        ActividadUsuario au = new ActividadUsuario();
        au.setActividad(a);
        au.setUsuario(u);
        au.setInscrito(false);
        au.setFavorito(true);
        actividadUsuarioRepository.save(au);
    }

    @Override
    public void inscribirActividadRelacionCreada(ActividadUsuario au) {
        au.setInscrito(true);
        actividadUsuarioRepository.save(au);
    }

    @Override
    public ActividadUsuario buscarActividadUsuario(Integer actividadId, String nombreUsuario) {
        Usuario u=usuarioRepository.findByNombreUsuario(nombreUsuario).orElse(null);
        ActividadUsuario au=actividadUsuarioRepository.findByUsuarioIdAndActividadId(u.getId(),actividadId);
        return au;
    }

    @Override
    public List<ActividadUsuario> buscarActividadesInscritasUsuario(String nombreUsuario) {
        Usuario u=usuarioRepository.findByNombreUsuario(nombreUsuario).orElse(null);
        List<ActividadUsuario>actividadUsuarioList=actividadUsuarioRepository.findAllactividadesInscritasByUsuario(u.getId());
        return actividadUsuarioList;
    }



    @Override
    public void insertarFavoritosActividadUsuarioCreada(ActividadUsuario au) {
        au.setFavorito(true);
        actividadUsuarioRepository.save(au);
    }

    @Override
    public ActividadUsuarioInscritoDtoOutput listarUsuariosInscritosActividad(Integer actividadId) {
        List<ActividadUsuario>actividadUsuarioList=actividadUsuarioRepository.findAllusuariosInscritasEnActividad(actividadId);
        Actividad actividad=actividadRepository.findById(actividadId).orElse(null);
        List<String>nombreUsuarioList=new ArrayList<>();
        for (ActividadUsuario au:actividadUsuarioList) {
          String nombreUsuario=  au.getUsuario().getNombre();
          nombreUsuarioList.add(nombreUsuario);
        }


        ActividadUsuarioInscritoDtoOutput ai=new ActividadUsuarioInscritoDtoOutput();
        if (actividad.getTitulo()!=null) {
            ai.setTituloActividad(actividad.getTitulo());
        }
        ai.setNombreUsuarioList(nombreUsuarioList);
        return ai;
    }


}
