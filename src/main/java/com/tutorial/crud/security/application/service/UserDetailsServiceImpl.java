package com.tutorial.crud.security.application.service;

import com.tutorial.crud.security.domain.entity.Usuario;
import com.tutorial.crud.security.domain.entity.UsuarioPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired UsuarioService usuarioService;

  //Creamos el userDetails a partir del nombre de usuario ya que va a
  // ser único meter restricción en el registro para que no pueda haber 2 usuarios iguales
  @Override
  public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
    Usuario usuario = usuarioService.getByNombreUsuario(nombreUsuario).get();
    return UsuarioPrincipal.build(usuario);
  }


}
