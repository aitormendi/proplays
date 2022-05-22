package com.tutorial.crud.jwt;

import com.tutorial.crud.security.application.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;

// Una vez se ejecuta para cada petición
public class JwtTokenFilter extends OncePerRequestFilter {
  private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

  @Autowired JwtProvider jwtProvider;

  @Autowired UserDetailsServiceImpl userDetailsService;

  // Va a comporbar si el token es valido o no extraemos el nombre del token y creamos una
  // autenticacion
  @Override
  protected void doFilterInternal(
      HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
      throws ServletException, IOException {

    try {
      String token = getToken(req);
      if (token != null && jwtProvider.validateToken(token)) {
        String nombreUsuario = jwtProvider.getNombreUsuarioFromToken(token);
        // Esto para acceder a los datos de usuario a partir del token y finalmente creamos la
        // autenticacion
        UserDetails userDetails = userDetailsService.loadUserByUsername(nombreUsuario);
        // Para la creación de la autenticacion necesitamos la autorizacion para saber a que
        // recursos puede acceder
        UsernamePasswordAuthenticationToken auth =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        // Al contexto de autorización se lo asignamos al usuario
        SecurityContextHolder.getContext().setAuthentication(auth);
      }

    } catch (Exception e) {
      logger.error("Fallo en el método doFilter"+ e.getMessage());
    }
    // Finalmente llamamos al filterChain para que haga el filtro
    filterChain.doFilter(req, res);
  }

  // Crear método para obtener el token sin el espacio
  private String getToken(HttpServletRequest request) {
    String header = request.getHeader("Authorization");
    if (header != null){
    //header.startsWith("Bearer")) {
      return header;
              //header.replace("Bearer ", "");
    }
    return null;
  }
}
