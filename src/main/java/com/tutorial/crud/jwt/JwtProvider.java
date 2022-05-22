package com.tutorial.crud.jwt;

import com.tutorial.crud.security.domain.entity.UsuarioPrincipal;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

  // Para ver el error donde nos puede dar la aplicación (esto se usa en desarrollo)
  private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private int expiration;

  // Necesitamos a un usuario principal tras obtenerlo creamos el token (setexpiration fecha de
  // expiracion)y firmamos con un algoritmo
  public String generateToken(Authentication authentication) {
    UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();
    return Jwts.builder()
        .setSubject(usuarioPrincipal.getUsername())
            .setIssuedAt(new Date())
        .setExpiration(new Date(new Date().getTime() + expiration * 1000))
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }

  // Obtener nombre usuario del token (el subject es el nombre de usuario) y lo obtenemos junto al
  // body
  public String getNombreUsuarioFromToken(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validateToken(String token) {

    try {
Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
return true;
    } catch (MalformedJwtException e) {
      logger.error("Token mal formado");

    } catch (UnsupportedJwtException e) {
      logger.error("Token no soportado");

    } catch (ExpiredJwtException e) {
      logger.error("Token expirado");

    } catch (IllegalArgumentException e) {
      logger.error("Token vacio");

    } catch (SignatureException e) {
      logger.error("Fail en la firma");
    }
    return false;
  }
}
